package com.example.mypages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Map<String, String>> expenseList;

    public ExpenseAdapter(List<Map<String, String>> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expence_card, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Map<String, String> expense = expenseList.get(position);
        holder.nameTextView.setText(expense.get("name"));
        holder.expenseTextView.setText("₹" + expense.get("expense"));
        holder.dateTextView.setText(expense.get("date"));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

static class ExpenseViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView, expenseTextView, dateTextView;

    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.expence_title);
        expenseTextView = itemView.findViewById(R.id.expence_amount);
        dateTextView = itemView.findViewById(R.id.expence_date);
    }
}
}
