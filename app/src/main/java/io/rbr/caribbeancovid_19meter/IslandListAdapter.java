package io.rbr.caribbeancovid_19meter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class IslandListAdapter extends RecyclerView.Adapter<IslandListAdapter.IslandViewHolder> {
    private LinkedList<Island> islandList;
    private LayoutInflater mInflater;
    private Context context;


    public IslandListAdapter(Context context, LinkedList<Island> islandList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.islandList = islandList;
    }

    @NonNull
    @Override
    public IslandListAdapter.IslandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView =
                mInflater.inflate(R.layout.island_list_item,
                        parent, false);
        return new IslandViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull IslandListAdapter.IslandViewHolder holder, int position) {
            // Retrieve the data for that position
            Island island = islandList.get(position);
            // Add the data to the view
            holder.name.setText(island.name);

            holder.cardView.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(context, IslandDetailActivity.class);
                    // TODO::Pass data
                    context.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return islandList.size();
    }

    public class IslandViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView name;
        private CardView cardView;
        private TextView population;
        private IslandListAdapter mAdapter;
        public IslandViewHolder(View itemView, IslandListAdapter adapter) {
            super(itemView);
            this.context = context;
            cardView = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.nameLabel);
            population = itemView.findViewById(R.id.populationLabel);
            population = itemView.findViewById(R.id.populationLabel);
        }
    }
}

