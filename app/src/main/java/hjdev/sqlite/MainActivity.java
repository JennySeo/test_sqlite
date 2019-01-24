package hjdev.sqlite;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDB;
    EditText name, surname, marks;
    TextView id;
    Button save_btn, list_btn, update_btn, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//
//        });
        //
        name = (EditText)findViewById(R.id.name_txt);
        surname = (EditText)findViewById(R.id.surname_txt);
        marks = (EditText)findViewById(R.id.marks_txt);
        id = (TextView)findViewById(R.id.id_txt);
        save_btn = (Button)findViewById(R.id.send_btn);
        list_btn = (Button)findViewById(R.id.view_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);

        //Listener
        myDB = new DatabaseHelper(this);
        save_btn.setOnClickListener(this);
        id.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        myDB.list_data();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //SAVED
            case R.id.send_btn:
                boolean result = myDB.save_data(name.getText().toString(),
                                                   surname.getText().toString(),
                                                   marks.getText().toString());
                if (result)
                Toast.makeText(MainActivity.this, "Succed Add Data", Toast.LENGTH_SHORT).show();
                else
                Toast.makeText(MainActivity.this, "Failed Add Data", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_btn:
            case R.id.id_txt:
                Cursor db_info = myDB.list_data();
                if (db_info.getCount()==0){
                alert_message("Message", "NO DATA FOUND");
                return;
                }

            //APPEND data to buffer
                StringBuffer buffer = new StringBuffer();
                while (db_info.moveToNext()){
                buffer.append("Id : "+ db_info.getString(0) +"\n");
                buffer.append("Memo : "+ db_info.getString(1) + "\n");
                buffer.append("Memo2 : "+ db_info.getString(2) +"\n");
                buffer.append("Marks : "+ db_info.getString(3) +"\n\n\n");
                }

            //SHOW DATA
            alert_message("List DATA", buffer.toString());
                break;

            //UPDATE
            case R.id.update_btn:
                Log.e("HJHJ !!! ","id.getText().toString() :"+id.getText().toString());
                boolean update_result = myDB.update_data(id.getText().toString(),
                                                    name.getText().toString(),
                                                    surname.getText().toString(),
                                                    marks.getText().toString());
                if(update_result)
                    Toast.makeText(MainActivity.this, "Succeed Update", Toast.LENGTH_SHORT).show();
                break;

            //DELETE
            case R.id.delete_btn:
                Integer delete_result = myDB.delete_data(id.getText().toString());
                if(delete_result>0)
                    Toast.makeText(MainActivity.this, "Succeed Delete", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //SHOW ALERT
    private void alert_message(String title, String message) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(message);
        build.show();
    }
}
