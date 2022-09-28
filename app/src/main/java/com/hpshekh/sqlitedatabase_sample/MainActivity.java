package com.hpshekh.sqlitedatabase_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;


import com.hpshekh.sqlitedatabase_sample.adapter.ContactRVAdapter;
import com.hpshekh.sqlitedatabase_sample.clickListener.ContactItemListener;
import com.hpshekh.sqlitedatabase_sample.databinding.ActivityMainBinding;
import com.hpshekh.sqlitedatabase_sample.db.DatabaseHelper;
import com.hpshekh.sqlitedatabase_sample.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactItemListener {

    ActivityMainBinding binding;
    ContactRVAdapter adapter;
    ArrayList<Contact> contactsList = new ArrayList<>();

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        db = new DatabaseHelper(this);
        adapter = new ContactRVAdapter(contactsList, this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.UpdateList(db.contactsList());

        binding.saveOrUpdateBtn.setOnClickListener(view -> {
            String name = binding.nameEdt.getText().toString().trim();
            String mobile = binding.mobileEdt.getText().toString().trim();
            String email = binding.emailEdt.getText().toString().trim();
            if (!name.isEmpty() && !mobile.isEmpty()) {
                save(new Contact(name, mobile, email));
            } else {
                Toast.makeText(this, "Please Enter the data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void save(Contact contact) {
        boolean result = db.insert(contact);
        if (result) {
            clearData();
            adapter.UpdateList(db.contactsList());
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        binding.nameEdt.setText("");
        binding.mobileEdt.setText("");
        binding.emailEdt.setText("");
    }

    @Override
    public void onItemClick(Contact contact) {
        binding.nameEdt.setText(contact.getName());
        binding.mobileEdt.setText(contact.getMobile());
        binding.emailEdt.setText(contact.getEmail());
    }

    @Override
    public void onItemLongClick(Contact contact) {
        boolean result = db.delete(contact.getId());
        if (result) {
            Toast.makeText(this, "Contact is deleted!", Toast.LENGTH_SHORT).show();
            adapter.UpdateList(db.contactsList());
        } else {
            Toast.makeText(this, "Some Error!", Toast.LENGTH_SHORT).show();
        }
    }

}