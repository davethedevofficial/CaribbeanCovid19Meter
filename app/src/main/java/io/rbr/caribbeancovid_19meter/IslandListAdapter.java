package io.rbr.caribbeancovid_19meter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class IslandListAdapter extends RecyclerView.Adapter<IslandListAdapter.IslandViewHolder> {
    private LinkedList<Island> islandList;
    private LayoutInflater mInflater;
    private Context context;


    public IslandListAdapter(Context context, LinkedList<Island> islandList) {
        mInflater = LayoutInflater.from(context);
        this.islandList = islandList;
    }

    @NonNull
    @Override
    public IslandListAdapter.IslandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView =
                mInflater.inflate(R.layout.island_list_item,
                        parent, false);
        return new IslandViewHolder(context, mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull IslandListAdapter.IslandViewHolder holder, int position) {
            // Retrieve the data for that position
            Island island = islandList.get(position);
            // Add the data to the view
            holder.name.setText(island.name);
    }

    @Override
    public int getItemCount() {
        return islandList.size();
    }

    public class IslandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        private TextView name;
        private TextView population;
        private IslandListAdapter mAdapter;
        public IslandViewHolder(Context context, View itemView, IslandListAdapter adapter) {
            super(itemView);
            this.context = context;
            name = itemView.findViewById(R.id.nameLabel);
            population = itemView.findViewById(R.id.populationLabel);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Intent messageIntent = new Intent(context, IslandDetailActivity.class);
//            // TODO::Pass data
//            context.startActivity(messageIntent);
        }
    }
}

