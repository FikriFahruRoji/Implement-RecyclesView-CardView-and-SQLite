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
                alertDialogBuilder.setTitle("Add student");
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
                                boolean a = myDB.save_table_student(studId, name, surename);
                                if (a == true) {
                                    Toast.makeText(MainActivity.this, "Success add data Student", Toast.LENGTH_LONG).show();
                                    getDB();
                                } else {
                                    Toast.makeText(MainActivity.this, "Error add data Student", Toast.LENGTH_LONG).show();
                                }
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

        getDB();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ModelStudents students = studentsList.get(position);

                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.custom_input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Update student");
                alertDialogBuilder.setView(promptView);

                final EditText editName = (EditText) promptView.findViewById(R.id.edit_name);
                final EditText editId = (EditText) promptView.findViewById(R.id.edit_id);
                final EditText editSurename = (EditText) promptView.findViewById(R.id.edit_surename);

                // setup a dialog window
                editId.setText(students.getId());
                editName.setText(students.getName());
                editSurename.setText(students.getSurename());

                final boolean a = myDB.update_table_student(String.valueOf(position), editName.getText().toString(), editSurename.getText().toString());

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (a == true) {
                                    Toast.makeText(MainActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                                    getDB();
                                } else {
                                    Toast.makeText(MainActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
                                }
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

            @Override
            public void onLongClick(View view, int position) {
                final  ModelStudents students = studentsList.get(position);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Delete student");
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDB.delete_student(students.getId());
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
        }));
    }

    private void getDB(){
        studentsList.clear();
        Cursor students = myDB.list_table_student();
        if (students.getCount() == 0) {
            alert_message("Message", "No data student found");
            return;
        }
        //append data student to buffer
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