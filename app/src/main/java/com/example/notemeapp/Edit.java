package com.example.notemeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends AppCompatActivity {

    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String todaysDate;
    String currentTime;
    NoteDatabase db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Long id = i.getLongExtra("ID", 0);
        db =  new NoteDatabase(this);
        note = db.getNote(id);



        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(note.getTitle());
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);

        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());


        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));

    }
    private String pad(int time){
        if(time<10){
            return "0" + time;
        }
        return String.valueOf(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save);
        if(noteTitle.getText().length() != 0){
            note.setTitle(noteTitle.getText().toString());
            note.setContent(noteDetails.getText().toString());
            int id = db.editNote(note);
            if(id == note.getID()){
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error Updating Note", Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(getApplicationContext(), Details.class);
            i.putExtra("ID", note.getID());
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}