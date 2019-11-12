 package com.moringa.architecturemvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

 public class AddEditNoteActivity extends AppCompatActivity {

     //we use the activityStartResult inorder to this activity from the main activity.
     // we use getIntentExtras to get the field information to display in the main activity;
     // In order to use the activityStartResult method,we declare key for the input we take from the add note activity.
     public static final String EXTRA_ID ="com.moringa.architecturemvvm.EXTRAID";
     public static final String EXTRA_TITLE ="com.moringa.architecturemvvm.EXTRATITLE";
     public static final String EXTRA_DESCRIPTION ="com.moringa.architecturemvvm.EXTRADESCRIPTION";

     public static final String EXTRA_PRIORITY ="com.moringa.architecturemvvm.EXTRAPRIORITY";


     private EditText editTextTitle;
     private EditText editTextDescription;
     private NumberPicker mNumberPriorityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle =findViewById(R.id.title_edit_text);
        editTextDescription =findViewById(R.id.description_edit_text);
        mNumberPriorityPicker =findViewById(R.id.priority_Picker);

        mNumberPriorityPicker.setMinValue(1);
        mNumberPriorityPicker.setMaxValue(10);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mNumberPriorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else{
            setTitle("Add note");

        }

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater  menuInflater = getMenuInflater();
         menuInflater.inflate(R.menu.menu,menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note :
                saveNote();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

     }

     private void saveNote() {

        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int priority =mNumberPriorityPicker.getValue();

        if (title.isEmpty() || description.isEmpty() ){
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }

         Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);

        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
     }
 }
