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

public class BudgetSelectionFragment extends Fragment {
    private RadioGroup budgetGroup;
    private Button nextButton;
    private TripViewModel tripViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_selection, container, false);
        
        budgetGroup = view.findViewById(R.id.budget_group);
        nextButton = view.findViewById(R.id.next_button);
        
        tripViewModel = ((MainActivity) requireActivity()).getTripViewModel();
        
        budgetGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = view.findViewById(checkedId);
            if (selectedButton != null) {
                tripViewModel.setBudgetType(selectedButton.getText().toString());
            }
        });
        
        nextButton.setOnClickListener(v -> {
            if (tripViewModel.getTripDetails().getValue() != null && 
                tripViewModel.getTripDetails().getValue().getBudgetType() != null) {
                ((MainActivity) requireActivity()).navigateToFragment(new TripSummaryFragment());
            }
        });
        
        return view;
    }
} 