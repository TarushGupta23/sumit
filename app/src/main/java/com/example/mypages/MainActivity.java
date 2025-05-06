package com.example.mypages;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.mypages.calculator.CalculatorMainActivity;
import com.example.mypages.rssFeed.RssFeedActivity;
import com.example.mypages.ExpenseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculatorButton = findViewById(R.id.calculator_btn_main);
        Button expenseButton = findViewById(R.id.expence_btn_main);
        Button rssFeedButton = findViewById(R.id.rss_feed_main);
        RecyclerView recyclerView = findViewById(R.id.expence_adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<java.util.Map<String, String>> expenses = new ArrayList<>();
        ExpenseAdapter adapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("sumit").child("expenses")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    expenses.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        java.util.Map<String, String> item = (java.util.Map<String, String>) data.getValue();
                        expenses.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });


        calculatorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorMainActivity.class);
            startActivity(intent);
        });

        expenseButton.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            android.widget.LinearLayout layout = new android.widget.LinearLayout(MainActivity.this);
            layout.setOrientation(android.widget.LinearLayout.VERTICAL);

            final android.widget.EditText nameInput = new android.widget.EditText(MainActivity.this);
            nameInput.setHint("Name");
            layout.addView(nameInput);

            final android.widget.EditText expenseInput = new android.widget.EditText(MainActivity.this);
            expenseInput.setHint("Expense");
            expenseInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            layout.addView(expenseInput);

            builder.setTitle("Add Expense");
            builder.setView(layout);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String name = nameInput.getText().toString().trim();
                String expense = expenseInput.getText().toString().trim();

                if (!name.isEmpty() && !expense.isEmpty()) {
                    String currentDate = java.text.DateFormat.getDateInstance().format(new java.util.Date());
                    FirebaseDatabase.getInstance()
                        .getReference().child("sumit").child("expenses")
                        .push().setValue(new java.util.HashMap<String, String>() {{
                            put("name", name);
                            put("expense", expense);
                            put("date", currentDate);
                        }});
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        rssFeedButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RssFeedActivity.class));
        });
    }

}