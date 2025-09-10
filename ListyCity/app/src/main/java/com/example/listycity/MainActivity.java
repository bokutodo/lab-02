package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText editTextCityName;
    Button buttonAddCity;
    Button buttonDeleteCity;

    int selectedCityPosition = -1;
    View previouslySelectedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editTextCityName = findViewById(R.id.editTextCityName);
        buttonAddCity = findViewById(R.id.buttonAddCity);
        buttonDeleteCity = findViewById(R.id.buttonDeleteCity);

        cityList= findViewById(R.id.cityList);
        String[] cities={"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList= new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter= new ArrayAdapter<>(this,R.layout.content,dataList);
        cityList.setAdapter(cityAdapter);

        buttonAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editTextCityName.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged(); // Important: tell the adapter data has changed
                    editTextCityName.setText("");
                    Toast.makeText(MainActivity.this, "New City Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (previouslySelectedView != null) {
                    previouslySelectedView.setBackgroundColor(Color.TRANSPARENT);
                }


                view.setBackgroundColor(Color.LTGRAY);

                // Update tracking variables
                selectedCityPosition = position;
                previouslySelectedView = view;
                Toast.makeText(MainActivity.this, dataList.get(position) + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDeleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCityPosition != -1 && selectedCityPosition < dataList.size()) {
                    String removedCity = dataList.remove(selectedCityPosition);
                    cityAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, removedCity + " deleted", Toast.LENGTH_SHORT).show();
                    selectedCityPosition = -1; // Reset selection
                } else {
                    Toast.makeText(MainActivity.this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
