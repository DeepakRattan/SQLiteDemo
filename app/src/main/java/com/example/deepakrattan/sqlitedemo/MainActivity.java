package com.example.deepakrattan.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSave, btnView, btnDelete, btnUpdate;
    private EditText edtName, edtPass;
    private String name, passwd;
    private MyDatabase myDatabase;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewByID

        edtName = (EditText) findViewById(R.id.edtName);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnView = (Button) findViewById(R.id.btnView);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        //Registering buttons for onclick event
        btnSave.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        myDatabase = new MyDatabase(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSave:
                Toast.makeText(MainActivity.this, "Save clicked", Toast.LENGTH_LONG).show();
                name = edtName.getText().toString();
                passwd = edtPass.getText().toString();
                Log.d("name", name);

                //Getting database in writable mode
                db = myDatabase.getWritableDatabase();

                //Use ContentValues to store data in columns
                ContentValues cv = new ContentValues();
                cv.put(MyDatabase.NAME, name);
                cv.put(MyDatabase.PASSWORD, passwd);

                //Inserting values in table
                long id = db.insert(MyDatabase.TABLE_NAME, null, cv);
                if (id < 0) {
                    Toast.makeText(MainActivity.this, "Insertion unsuccessful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insertion successful", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPass.setText("");
                }
                break;
            case R.id.btnView:
                Toast.makeText(MainActivity.this, "View clicked", Toast.LENGTH_LONG).show();
                db = myDatabase.getWritableDatabase();
                String[] column = {MyDatabase.UID, MyDatabase.NAME, MyDatabase.PASSWORD};

                Cursor cursor = db.query(MyDatabase.TABLE_NAME, column, null, null, null, null, null);
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    int index1 = cursor.getColumnIndex(MyDatabase.UID);
                    int index2 = cursor.getColumnIndex(MyDatabase.NAME);
                    int index3 = cursor.getColumnIndex(MyDatabase.PASSWORD);

                    int id1 = cursor.getInt(index1);
                    String name = cursor.getString(index2);
                    String pass = cursor.getString(index3);

                    buffer.append(" id = " + id1 + " name = " + name + " password = " + pass + "\n");

                }
                Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_LONG).show();
                break;
            case R.id.btnDelete:
                Toast.makeText(MainActivity.this, "Delete clicked", Toast.LENGTH_LONG).show();
                db = myDatabase.getWritableDatabase();
                String whereClause1 = MyDatabase.NAME + "=?";
                String[] whereArgs1 = {"Gogi"};
                //int count = db.delete(MyDatabase.TABLE_NAME, MyDatabase.NAME + "=?", new String[]{"Ravi"});
                int count = db.delete(MyDatabase.TABLE_NAME, whereClause1, whereArgs1);

                Toast.makeText(MainActivity.this, "No. of rows deleted = " + count, Toast.LENGTH_LONG).show();
                break;

            case R.id.btnUpdate:
                Toast.makeText(MainActivity.this, "Update clicked", Toast.LENGTH_LONG).show();
                db = myDatabase.getWritableDatabase();
                ContentValues cv1 = new ContentValues();
                cv1.put(MyDatabase.NAME, "Deepak Rattan MCA");
                String whereClause = MyDatabase.NAME + "=?";
                String[] whereArgs = {"Deepak"};
                int u = db.update(MyDatabase.TABLE_NAME, cv1, whereClause, whereArgs);
                Toast.makeText(MainActivity.this, "No. of rows updated = " + u, Toast.LENGTH_LONG).show();

        }

    }
}
