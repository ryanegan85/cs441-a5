package edu.binghamton.cs.cs441_a5;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "data.txt";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editNumber;
    private Button addButton;
    private Button clearButton;
    private TextView runningTotalText;
    private ArrayList<Integer> numbers;
    private int runningTotal;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(this);

        numbers = new ArrayList<>();
        numbers = tinyDB.getListInt("Numbers");

        runningTotalText = findViewById(R.id.runningTotal);
        runningTotal = 0;

        if(numbers.size() > 0) {
            for (int i = 0; i < numbers.size(); i++) {
                runningTotal += numbers.get(i);
            }
            runningTotalText.setText("Running Total: " + runningTotal);
        }

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, numbers);
        recyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        editNumber = findViewById(R.id.numberEdit);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                int num;
                s = editNumber.getText().toString();
                if(s.length() > 0) {
                    try {
                        num = Integer.parseInt(s);
                        numbers.add(num);

                        runningTotal += num;
                        runningTotalText.setText("Running Total: " + runningTotal);

                        mAdapter.notifyDataSetChanged();
                        editNumber.getText().clear();

                        tinyDB.putListInt("Numbers", numbers);
                    } catch(NumberFormatException e) {
                        System.out.println("Error converting input to integer");
                    }
                }
            }
        });

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers.clear();
                mAdapter.notifyDataSetChanged();

                runningTotal = 0;
                runningTotalText.setText("Running Total: 0");
                tinyDB.putListInt("Numbers", numbers);
            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int x = numbers.get(viewHolder.getAdapterPosition());
            numbers.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();

            runningTotal -= x;
            runningTotalText.setText("Running Total: " + runningTotal);
        }
    };

    /*
    public void save() {
        String text;
        FileOutputStream fos = null;
        File f = new File(FILE_NAME);

        if(f.exists()) f.delete();

        try  {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);

            for(int i=0; i<numbers.size(); i++) {
                text = numbers.get(i) + "\n";
                fos.write(text.getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load() {
        FileInputStream fis = null;
        File f = new File(FILE_NAME);

        if(f.exists()) {
            try {
                fis = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    numbers.add(Integer.parseInt(line));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    */

}
