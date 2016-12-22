package id.fikri.testadvancesqlite.Activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import id.fikri.testadvancesqlite.Adapter.AdapterStudents;
import id.fikri.testadvancesqlite.DatabaseHelper;
import id.fikri.testadvancesqlite.Model.ModelStudents;
import id.fikri.testadvancesqlite.R;
import id.fikri.testadvancesqlite.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {
    private List<ModelStudents> studentsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterStudents mAdapter;

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.custom_input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText editId = (EditText) promptView.findViewById(R.id.edit_id);
                final EditText editName = (EditText) promptView.findViewById(R.id.edit_name);
                final EditText editSurename = (EditText) promptView.findViewById(R.id.edit_surename);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String studId =  editId.getText().toString();
                                String name =  editName.getText().toString();
                                String surename =  editSurename.getText().toString();
                                myDB.save_table_student(studId, name, surename);
                                getDB();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

        myDB = new DatabaseHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_main);

        mAdapter = new AdapterStudents(studentsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ModelStudents movie = studentsList.get(position);
                Toast.makeText(getApplicationContext(), movie.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getDB();
    }

    private void getDB(){
        Cursor students = myDB.list_table_student();
        if (students.getCount() == 0) {
            alert_message("Message", "No data student found");
            return;
        }
        //append data student to buffer
        studentsList.clear();
        while (students.moveToNext()) {
            ModelStudents movie = new ModelStudents(students.getString(0), students.getString(1), students.getString(2));
            studentsList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void alert_message(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}