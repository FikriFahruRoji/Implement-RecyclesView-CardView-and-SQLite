package id.fikri.testadvancesqlite.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fikri on 20/12/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int db_version = 1;
    public static final String db_name = "Student.db";
    public static final String table_students = "students";
    public static final String col_id = "ID";
    public static final String col_name = "NAME";
    public static final String col_surename = "SURNAME";

    public static final String table_create_student = "CREATE TABLE " + table_students + " ( " +
            col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            col_name + " TEXT, " +
            col_surename + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_create_student);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_students);
        onCreate(db);
    }

    public boolean save_table_student(String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(col_name, name);
        content_values.put(col_surename, surname);
        long result = db.insert(table_students, null, content_values);
        return result != -1;
    }

    public Cursor list_table_student() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor students = db.rawQuery("SELECT * FROM " + table_students, null);
        return students;
    }

    public boolean update_table_student(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(col_id, id);
        content_values.put(col_name, name);
        content_values.put(col_surename, surname);
        db.update(table_students, content_values, "ID = ? ", new String[]{id});
        return true;
    }

    public Integer delete_student(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_students, "ID = ?", new String[] {id});
    }
}