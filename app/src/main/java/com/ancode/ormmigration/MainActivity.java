package com.ancode.ormmigration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.ancode.ormmigration.model.Address;
import com.ancode.ormmigration.model.DaoSession;
import com.ancode.ormmigration.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Person selectedPerson;

    private DataBaseProvider db;
    private EditText personName;
    private EditText personAddress;
    private ArrayAdapter<Person> personsAdapter;
    private ArrayAdapter<Address> addressesAdapter;
    private ListView personsList;
    private ListView addressesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoApp app = (DaoApp) getApplication();
        DaoSession session = app.getSession();
        db = new DataBaseProvider(app.getFallbackDb(),
                session.getPersonDao(),
                session.getAddressDao());

        personName = findViewById(R.id.person_name);
        personsList = findViewById(R.id.persons_list);
        addressesList = findViewById(R.id.addresses_list);
        personAddress = findViewById(R.id.person_address);

        addressesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, new ArrayList<Address>());
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
        List<Address> addresses = db.getAddresses(selectedPerson.id);
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
