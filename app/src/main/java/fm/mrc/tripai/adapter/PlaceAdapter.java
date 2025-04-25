package fm.mrc.tripai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fm.mrc.tripai.R;
import fm.mrc.tripai.model.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
    private List<Place> places = new ArrayList<>();

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = places.get(position);
        holder.nameTextView.setText(place.getName());
        holder.addressTextView.setText(place.getAddress());
        holder.ratingTextView.setText(String.format("%.1f", place.getRating()));

        // Load image using Glide if photo reference is available
        if (place.getPhotoReference() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(place.getPhotoReference())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.photoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView nameTextView;
        TextView addressTextView;
        TextView ratingTextView;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photo_image_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            addressTextView = itemView.findViewById(R.id.address_text_view);
            ratingTextView = itemView.findViewById(R.id.rating_text_view);
        }
    }
} 