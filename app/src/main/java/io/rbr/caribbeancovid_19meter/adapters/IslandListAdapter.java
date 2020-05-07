package io.rbr.caribbeancovid_19meter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import io.rbr.caribbeancovid_19meter.IslandDetailActivity;
import io.rbr.caribbeancovid_19meter.R;
import io.rbr.caribbeancovid_19meter.models.Island;

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
            holder.nameLabel.setText(island.name);
            holder.populationLabel.setText("Population " + island.population);
            holder.totalCasesLabel.setText(island.totalCases + " Total Cases");
            holder.totalDeathsLabel.setText(island.totalDeaths + " Total Deaths");
            holder.todayCasesLabel.setText(island.todayCases + "");
            holder.noTrendImageView.setVisibility(island.todayCases == 0 ? View.VISIBLE : View.GONE);
            holder.upTrendImageView.setVisibility(island.todayCases > 0 ? View.VISIBLE : View.GONE);
            try {
                if (getDrawable(island.geoId.toLowerCase()) != null)
                    holder.flagImageView.setImageDrawable(getDrawable(island.geoId.toLowerCase()));
            } catch (Exception e) {}

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

    Drawable getDrawable(String name) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());

        if (resourceId == 0)
            return null;

        return resources.getDrawable(resourceId);
    }

    @Override
    public int getItemCount() {
        return islandList.size();
    }

    public class IslandViewHolder extends RecyclerView.ViewHolder {
        private TextView nameLabel;
        private TextView populationLabel;
        private TextView totalCasesLabel;
        private TextView totalDeathsLabel;
        private TextView todayCasesLabel;
        private ImageView flagImageView;
        private ImageView noTrendImageView;
        private ImageView upTrendImageView;
        private CardView cardView;
        private IslandListAdapter mAdapter;
        public IslandViewHolder(View itemView, IslandListAdapter adapter) {
            super(itemView);
            cardView = itemView.findViewById(R.id.parent);
            nameLabel = itemView.findViewById(R.id.nameLabel);
            populationLabel = itemView.findViewById(R.id.populationLabel);
            totalCasesLabel = itemView.findViewById(R.id.totalCasesLabel);
            totalDeathsLabel = itemView.findViewById(R.id.totalDeathsLabel);
            todayCasesLabel = itemView.findViewById(R.id.todayCasesLabel);
            flagImageView = itemView.findViewById(R.id.flagImageView);
            noTrendImageView = itemView.findViewById(R.id.noTrendImageView);
            upTrendImageView = itemView.findViewById(R.id.upTrendImageView);
        }
    }
}

