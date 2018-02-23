package com.example.android.interectivestoryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.interectivestoryapp.R;

public class MainActivity extends AppCompatActivity {



    private EditText nameEditText;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.name_edit_text);
        startButton = findViewById(R.id.start_button);



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                startStory(name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameEditText.setText("");
    }

    private void startStory(String name) {

        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(getString(R.string.name_key), name);
        startActivity(intent);
    }
}
