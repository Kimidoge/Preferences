package com.example.persistent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView output;
    EditText input;
    Button savePref, loadPref, saveFile, loadFile;

    SharedPreferences myPref;
    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        output = findViewById(R.id.outputText);
        input = findViewById(R.id.inputText);
        saveFile = findViewById(R.id.saveFile);
        loadFile = findViewById(R.id.loadFile);
        savePref = findViewById(R.id.savePref);
        loadPref = findViewById(R.id.loadPref);

        myPref = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPref.edit();

        savePref.setOnClickListener(new View.OnClickListener() { //upon clicked, capture the string input
            @Override
            public void onClick(View v) {
                String inputString = input.getText().toString();
                myEditor.putString(getString(R.string.com_example_persistent_preferenceString),inputString);
                myEditor.commit();
                Toast.makeText(MainActivity.this, inputString+"saved", Toast.LENGTH_SHORT).show();

            }
        });

        loadPref.setOnClickListener(new View.OnClickListener() {  // FOR LOADING THE PREFERENCE, FROM PREFERENCES
            @Override
            public void onClick(View v) {
                String display = myPref.getString(getString(R.string.com_example_persistent_preferenceString), "NULL" ); // IF GOT VALUE, DISPLAY. IF NOT DISPLAY NULL.
                output.setText(display);
            }
        });

        saveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = input.getText().toString();
              // ================= THIS IS FOR EXTERNAL FILES ==========================================
              String path = getExternalFilesDir("notes")+"/notes.txt";
              Log.i("filePath", path);
              FileWriter fw = null;
                try {
                    fw = new FileWriter(path);
                    fw.write(inputString);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // =========== THIS IS FOR INTERNAL FILES ========================================
                /*OutputStreamWriter out=null;
                try {
                    out = new  OutputStreamWriter(openFileOutput("notes.txt",MODE_APPEND));
                    out.write(inputString);
                    Toast.makeText(MainActivity.this, "Output Saved to File", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {

                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } */
            }
        });

        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // =================THIS IS FOR EXTERNAL FILE ======================================
              String path = getExternalFilesDir("notes")+"/notes.txt";
              FileReader fr = null;
                try {
                    fr = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fr);
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while((line= reader.readLine())!=null){
                        sb.append(line+ "\n");
                    }
                    output.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                // ================== THIS IS FOR INTERNAL FILE ========================================
               /* InputStream is = null;
                try {
                    is = openFileInput("notes.txt");
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    output.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        });
    }
}