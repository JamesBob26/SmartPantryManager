package com.robertklisiewicz.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryList;

    public PantryAdapter(List<PantryItem> pantryList) {
        this.pantryList = pantryList;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry_row, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryList.get(position);
        holder.tvIngredientName.setText(item.getName());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvUnit.setText(item.getUnit());

        // Handle optional expiry date
        if (item.getExpiryDate() != null && !item.getExpiryDate().isEmpty()) {
            holder.tvExpiryDate.setText("Exp: " + item.getExpiryDate());
            holder.tvExpiryDate.setVisibility(View.VISIBLE);
        } else {
            holder.tvExpiryDate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return pantryList != null ? pantryList.size() : 0;
    }

    // Inner class holding the UI elements for a single row
    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName, tvExpiryDate, tvQuantity, tvUnit;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvUnit = itemView.findViewById(R.id.tvUnit);
        }
    }
}

