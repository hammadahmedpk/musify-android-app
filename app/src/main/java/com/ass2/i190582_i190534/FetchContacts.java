package com.ass2.i190582_i190534;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FetchContacts extends AppCompatActivity {
    public static final int REQUEST_READ_CONTACTS = 79;
    ArrayList<Person> contacts_list;
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    Button newContacts;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_contacts);

       // contacts = findViewById(R.id.contacts);
        contacts_list = new ArrayList<>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission((Manifest.permission.READ_CONTACTS))!=
                        PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else {
            getContact();
        }
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ContactsAdapter(contacts_list, getApplicationContext());
        recyclerView.setAdapter(adapter) ;


        // For search functionality
        SearchView searchView = (SearchView) findViewById(R.id.searchText); // inititate a search view
        CharSequence query = searchView.getQuery();
        searchView.setQueryHint("Search Contacts");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });

        // New Contacts
        newContacts = findViewById(R.id.newContacts);
        newContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewContacts.class);
                startActivity(intent);
            }
        });

    }

    private void getContact() {
        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while(c.moveToNext()){
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts_list.add(new Person(name, mobile));
//            Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show();
           // contacts_list.add(name+ " "+mobile +"\n");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
    }
}