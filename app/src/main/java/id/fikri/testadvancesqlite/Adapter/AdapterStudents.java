package id.fikri.testadvancesqlite.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.fikri.testadvancesqlite.Model.ModelStudents;
import id.fikri.testadvancesqlite.R;

/**
 * Created by fikri on 21/12/16.
 */

public class AdapterStudents extends RecyclerView.Adapter<AdapterStudents.MyViewHolder> {

    private List<ModelStudents> studentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, surename;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.tx_id);
            name = (TextView) view.findViewById(R.id.tx_name);
            surename = (TextView) view.findViewById(R.id.tx_surename);
        }
    }


    public AdapterStudents(List<ModelStudents> moviesList) {
        this.studentsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_test, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelStudents student = studentsList.get(position);
        holder.id.setText(student.getId());
        holder.name.setText(student.getName());
        holder.surename.setText(student.getSurename());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }
}