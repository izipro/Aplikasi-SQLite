package com.latihan.aplikasisqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.latihan.aplikasisqlite.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    EditText txt_id, txt_name, txt_address;
    Button btn_submit, btn_cancel;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_address = (EditText) findViewById(R.id.txt_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        if (id==null || id == ""){
            setTitle("Add data");
        } else {
            setTitle("Edit data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }
        btn_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                try{
                    if(txt_id.getText().toString().equals("")){
                        save();
                    } else{
                        edit();
                    }
                }catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });

    }
    public void onBackPressed(){
        finish();
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void blank(){
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }
    public void save(){
        if(String.valueOf(txt_name.getText()).equals(null) ||
                String.valueOf(txt_name.getText()).equals("") || String.valueOf(txt_address.getText()).equals(null) ||
                String.valueOf(txt_address.getText()).equals("")){
            Toast.makeText(getApplicationContext(),
                    "Please Input Name or Address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
            Toast.makeText(getApplicationContext(),
                    "simpan berhasil;", Toast.LENGTH_SHORT).show();
            blank();
            finish();
        }
    }
    private void edit (){
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("") ||
                String.valueOf(txt_address.getText()).equals(null) || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address", Toast.LENGTH_SHORT).show();


        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_name.getText().toString().trim(),txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }
}
