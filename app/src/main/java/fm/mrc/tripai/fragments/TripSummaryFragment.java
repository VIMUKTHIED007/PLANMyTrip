package fm.mrc.tripai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import fm.mrc.tripai.R;
import fm.mrc.tripai.model.Place;
import fm.mrc.tripai.model.TripDetails;
import fm.mrc.tripai.viewmodel.TripViewModel;
import fm.mrc.tripai.adapter.PlaceAdapter;

public class TripSummaryFragment extends Fragment {
    private TextView locationText;
    private TextView datesText;
    private TextView travelerTypeText;
    private TextView budgetText;
    private RecyclerView placesRecyclerView;
    private RecyclerView hotelsRecyclerView;
    private TripViewModel tripViewModel;
    private SimpleDateFormat dateFormat;
    private PlaceAdapter placesAdapter;
    private PlaceAdapter hotelsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_summary, container, false);
        
        locationText = view.findViewById(R.id.location_text);
        datesText = view.findViewById(R.id.dates_text);
        travelerTypeText = view.findViewById(R.id.traveler_type_text);
        budgetText = view.findViewById(R.id.budget_text);
        placesRecyclerView = view.findViewById(R.id.places_recycler_view);
        hotelsRecyclerView = view.findViewById(R.id.hotels_recycler_view);
        
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);
        dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        
        setupRecyclerViews();
        observeTripDetails();
        observePlacesAndHotels();
        
        // Load places and hotels
        tripViewModel.loadPlacesAndHotels();
        
        return view;
    }

    private void setupRecyclerViews() {
        placesAdapter = new PlaceAdapter();
        hotelsAdapter = new PlaceAdapter();
        
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        placesRecyclerView.setAdapter(placesAdapter);
        hotelsRecyclerView.setAdapter(hotelsAdapter);
    }

    private void observeTripDetails() {
        tripViewModel.getTripDetails().observe(getViewLifecycleOwner(), new Observer<TripDetails>() {
            @Override
            public void onChanged(TripDetails tripDetails) {
                if (tripDetails != null) {
                    locationText.setText(tripDetails.getLocation());
                    
                    if (tripDetails.getStartDate() != null && tripDetails.getEndDate() != null) {
                        String dates = dateFormat.format(tripDetails.getStartDate()) + " - " + 
                                     dateFormat.format(tripDetails.getEndDate());
                        datesText.setText(dates);
                    }
                    
                    travelerTypeText.setText(tripDetails.getTravelerType());
                    budgetText.setText(tripDetails.getBudgetType());
                }
            }
        });
    }

    private void observePlacesAndHotels() {
        tripViewModel.getPlacesToVisit().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                placesAdapter.setPlaces(places);
            }
        });

        tripViewModel.getHotels().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> hotels) {
                hotelsAdapter.setPlaces(hotels);
            }
        });
    }
} 