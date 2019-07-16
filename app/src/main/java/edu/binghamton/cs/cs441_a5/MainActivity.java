package edu.binghamton.cs.cs441_a5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editNumber;
    private Button addButton;
    private Button clearButton;
    private TextView runningTotalText;
    private ArrayList<Integer> numbers;
    private int runningTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = new ArrayList<>();
        runningTotal = 0;

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, numbers);
        recyclerView.setAdapter(mAdapter);

        editNumber = findViewById(R.id.numberEdit);
        runningTotalText = findViewById(R.id.runningTotal);
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
            }
        });
    }
}
