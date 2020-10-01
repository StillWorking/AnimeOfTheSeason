package edu.lewisu.android.mts.animeoftheseason;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AnimeViewHolder> {

    private Context context;
    private List<Anime> data;
    private RequestOptions option;
    private final AnimeAdapterClickHandler clickHandler;

    public interface AnimeAdapterClickHandler {
        void onClick(Anime anime);
    }

    public RecyclerViewAdapter(AnimeAdapterClickHandler animeAdapterClickHandler) {
        clickHandler = animeAdapterClickHandler;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.anime_row, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = data.get(position);
        String title = anime.getTitle();
        String rating = anime.getRating();
        String producer = anime.getProducers();
        String type = anime.getType();
        holder.animeTitleView.setText(title);
        holder.mediumTypeTextView.setText(type);
        holder.producerTextView.setText(producer);
        holder.ratingTextView.setText(rating);

        Glide.with(context).load(data.get(position).getImg()).apply(option).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public void setViewData(List<Anime> animeData) {
        data = animeData;
        notifyDataSetChanged();
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView animeTitleView;
        private final TextView mediumTypeTextView;
        private final TextView ratingTextView;
        private final TextView producerTextView;
        private final ImageView imgView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);

            animeTitleView = itemView.findViewById(R.id.anime_title);
            mediumTypeTextView = itemView.findViewById(R.id.type);
            ratingTextView = itemView.findViewById(R.id.rating);
            producerTextView = itemView.findViewById(R.id.producer);
            imgView = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Anime anime = data.get(adapterPosition);
            clickHandler.onClick(anime);
        }
    }
}
