package com.vsgaa.aplikasisqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vsgaa.aplikasisqlite.helper.DbHelper;

public class AddEditActivity extends AppCompatActivity {

    private DbHelper helper;
    private EditText txt_name, txt_address;
    private String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);

        //terima data jika ada
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            name = extras.getString("name");
            address = extras.getString("address");

            txt_name.setText(name);
            txt_address.setText(address);
        }

        Button btn_submit = findViewById(R.id.btn_submit);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        helper = new DbHelper(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null)
                    edit();
                else
                    save();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void save() {
        name = txt_name.getText().toString();
        address = txt_address.getText().toString();
        if (name.equals("") || address.equals("")) {
            Toast.makeText(this, "Name atau address tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            long rowId = helper.insert(name, address);
            if (rowId == -1) {
                Toast.makeText(this, "Insert gagal", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Insert berhasil", Toast.LENGTH_SHORT).show();
                blank();
            }
        }
    }

    private void blank() {
        txt_name.setText(null);
        txt_address.setText(null);
    }

    private void edit() {
        if (name.equals("") || address.equals("")) {
            Toast.makeText(this, "Name atau address tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            String newName = txt_name.getText().toString();
            String newAddress = txt_address.getText().toString();
            long rowAffected = helper.update(Integer.parseInt(id), newName, newAddress);
            if (rowAffected <= 0) {
                Toast.makeText(this, "Update gagal", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update berhasil", Toast.LENGTH_SHORT).show();
            }
        }
    }
}