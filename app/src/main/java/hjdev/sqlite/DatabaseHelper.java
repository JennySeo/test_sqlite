package hjdev.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DroiD on 28/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "test.db";
    public static final String TABLE_NAME = "test";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "MEMO";
    public static final String COL_3 = "MEMO2";
    public static final String COL_4 = "MARKS";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_2 + " TEXT, "+
            COL_3 + " TEXT, "+
            COL_4 + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" +TABLE_NAME);
        onCreate(db);
    }

    public boolean save_data(String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, name);
        content_values.put(COL_3, surname);
        content_values.put(COL_4, marks);
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }

    public Cursor list_data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor datas = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return datas;
    }

    public boolean update_data(String id, String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, name);
        content_values.put(COL_3, surname);
        content_values.put(COL_4, marks);
        db.update(TABLE_NAME, content_values, "ID = ?", new String[]{id});
        return true;
    }

    public Integer delete_data(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }
}