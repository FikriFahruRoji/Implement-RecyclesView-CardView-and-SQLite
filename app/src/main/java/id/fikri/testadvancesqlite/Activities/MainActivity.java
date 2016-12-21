package id.fikri.testadvancesqlite.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.fikri.testadvancesqlite.Adapter.AdapterStudents;
import id.fikri.testadvancesqlite.Model.ModelStudents;
import id.fikri.testadvancesqlite.R;
import id.fikri.testadvancesqlite.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity {
    private List<ModelStudents> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterStudents mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_main);

        mAdapter = new AdapterStudents(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ModelStudents movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
    }

    private void prepareMovieData() {
        ModelStudents movie = new ModelStudents("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new ModelStudents("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new ModelStudents("Star Wars: Episode VII", "Action", "2015");
        movieList.add(movie);

        movie = new ModelStudents("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new ModelStudents("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }
}