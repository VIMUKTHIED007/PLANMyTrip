package fm.mrc.tripai.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import fm.mrc.tripai.MainActivity;
import fm.mrc.tripai.R;
import fm.mrc.tripai.viewmodel.TripViewModel;

public class LocationSearchFragment extends Fragment {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText locationEditText;
    private Button nextButton;
    private TripViewModel tripViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "YOUR_API_KEY");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_search, container, false);
        
        locationEditText = view.findViewById(R.id.location_edit_text);
        nextButton = view.findViewById(R.id.next_button);
        
        tripViewModel = ((MainActivity) requireActivity()).getTripViewModel();
        
        locationEditText.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(requireContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });
        
        nextButton.setOnClickListener(v -> {
            if (tripViewModel.getTripDetails().getValue() != null && 
                tripViewModel.getTripDetails().getValue().getLocation() != null) {
                ((MainActivity) requireActivity()).navigateToFragment(new TravelerTypeFragment());
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                locationEditText.setText(place.getName());
                tripViewModel.setLocation(place.getName());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
} 