package sportapp.rider.com.myapplication.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

import sportapp.rider.com.myapplication.R;
import sportapp.rider.com.myapplication.model.NewsResponseModel;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportsNewsHolder> {

    private List<NewsResponseModel> sportNewsResponseModelList;
    private Context context;
    private SportAdapter.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(SportAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    public SportAdapter(Context context, List<NewsResponseModel> sportNewsResponseModelList) {
        this.sportNewsResponseModelList = sportNewsResponseModelList;
        this.context = context;
    }

    public class SportsNewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewSportName, textViewDescription, textViewDate, textViewLeagueDescription;
        public ImageView imageViewPicture;

        public SportsNewsHolder(View view) {
            super(view);
            textViewSportName = view.findViewById(R.id.site_name);
            textViewDescription = view.findViewById(R.id.title);
            textViewDate = view.findViewById(R.id.dates);
            textViewLeagueDescription = view.findViewById(R.id.desc);
            imageViewPicture = view.findViewById(R.id.player_img);

            view.setOnClickListener(v -> {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public SportAdapter.SportsNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);
        return new SportAdapter.SportsNewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsNewsHolder holder, int position) {

        NewsResponseModel newsResponseModel = sportNewsResponseModelList.get(position);

        if (newsResponseModel != null) {
            holder.textViewSportName.setText(newsResponseModel.category);
            holder.textViewDescription.setText(newsResponseModel.headline);
            holder.textViewDate.setText(newsResponseModel.urlFriendlyDate);
            holder.textViewLeagueDescription.setText(newsResponseModel.blurb);

            String url = newsResponseModel.imageUrlLocal + newsResponseModel.largeImageName;
            Glide
                    .with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageViewPicture);
        }

    }


    @Override
    public int getItemCount() {
        return sportNewsResponseModelList.size();
    }
}

