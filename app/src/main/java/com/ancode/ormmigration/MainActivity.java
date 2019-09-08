package com.ancode.ormmigration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DataBaseProvider.Person selectedPerson;

    private DataBaseProvider db;
    private EditText personName;
    private EditText personAddress;
    private ArrayAdapter<DataBaseProvider.Person> personsAdapter;
    private ArrayAdapter<DataBaseProvider.Address> addressesAdapter;
    private ListView personsList;
    private ListView addressesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoApp app = (DaoApp) getApplication();
        db = new DataBaseProvider(app.getFallbackDb());

        personName = findViewById(R.id.person_name);
        personsList = findViewById(R.id.persons_list);
        addressesList = findViewById(R.id.addresses_list);
        personAddress = findViewById(R.id.person_address);


        addressesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, new ArrayList<DataBaseProvider.Address>());
        addressesList.setAdapter(addressesAdapter);

        personsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, db.getPersons());
        personsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPerson = personsAdapter.getItem(position);
                loadAdresses();
            }
        });

        personsList.setAdapter(personsAdapter);
    }

    private void loadAdresses() {
        List<DataBaseProvider.Address> addresses = db.getAddresses(selectedPerson.id);
        addressesAdapter.clear();
        addressesAdapter.addAll(addresses);
    }

    public void savePerson(View view) {
        db.insertPerson(personName.getText().toString());
        personName.setText("");
        personsAdapter.clear();
        personsAdapter.addAll(db.getPersons());
    }

    public void saveAddress(View view) {
        if (selectedPerson == null) {
            return;
        }
        db.insertAddress(selectedPerson.id, personAddress.getText().toString());
        personAddress.setText("");
        loadAdresses();
    }
}
