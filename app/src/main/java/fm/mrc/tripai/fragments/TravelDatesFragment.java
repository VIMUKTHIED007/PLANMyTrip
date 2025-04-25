package fm.mrc.tripai.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import fm.mrc.tripai.MainActivity;
import fm.mrc.tripai.R;
import fm.mrc.tripai.viewmodel.TripViewModel;

public class TravelDatesFragment extends Fragment {
    private TextView startDateText;
    private TextView endDateText;
    private Button nextButton;
    private TripViewModel tripViewModel;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_dates, container, false);
        
        startDateText = view.findViewById(R.id.start_date_text);
        endDateText = view.findViewById(R.id.end_date_text);
        nextButton = view.findViewById(R.id.next_button);
        
        tripViewModel = ((MainActivity) requireActivity()).getTripViewModel();
        calendar = Calendar.getInstance();
        
        startDateText.setOnClickListener(v -> showDatePicker(true));
        endDateText.setOnClickListener(v -> showDatePicker(false));
        
        nextButton.setOnClickListener(v -> {
            if (tripViewModel.getTripDetails().getValue() != null && 
                tripViewModel.getTripDetails().getValue().getStartDate() != null &&
                tripViewModel.getTripDetails().getValue().getEndDate() != null) {
                ((MainActivity) requireActivity()).navigateToFragment(new BudgetSelectionFragment());
            }
        });
        
        return view;
    }

    private void showDatePicker(final boolean isStartDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            requireContext(),
            (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();
                
                if (isStartDate) {
                    tripViewModel.setStartDate(selectedDate);
                    startDateText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                } else {
                    tripViewModel.setEndDate(selectedDate);
                    endDateText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
} 