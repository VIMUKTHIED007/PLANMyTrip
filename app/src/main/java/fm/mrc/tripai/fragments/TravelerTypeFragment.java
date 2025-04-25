package fm.mrc.tripai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fm.mrc.tripai.MainActivity;
import fm.mrc.tripai.R;
import fm.mrc.tripai.viewmodel.TripViewModel;

public class TravelerTypeFragment extends Fragment {
    private RadioGroup travelerTypeGroup;
    private Button nextButton;
    private TripViewModel tripViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traveler_type, container, false);
        
        travelerTypeGroup = view.findViewById(R.id.traveler_type_group);
        nextButton = view.findViewById(R.id.next_button);
        
        tripViewModel = ((MainActivity) requireActivity()).getTripViewModel();
        
        travelerTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            if (selectedButton != null) {
                tripViewModel.setTravelerType(selectedButton.getText().toString());
            }
        });
        
        nextButton.setOnClickListener(v -> {
            if (tripViewModel.getTripDetails().getValue() != null && 
                tripViewModel.getTripDetails().getValue().getTravelerType() != null) {
                ((MainActivity) requireActivity()).navigateToFragment(new TravelDatesFragment());
            }
        });
        
        return view;
    }
} 