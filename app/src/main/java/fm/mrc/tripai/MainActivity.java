package fm.mrc.tripai;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import fm.mrc.tripai.fragments.LocationSearchFragment;
import fm.mrc.tripai.fragments.TravelerTypeFragment;
import fm.mrc.tripai.fragments.TravelDatesFragment;
import fm.mrc.tripai.fragments.BudgetSelectionFragment;
import fm.mrc.tripai.fragments.TripSummaryFragment;
import fm.mrc.tripai.viewmodel.TripViewModel;

public class MainActivity extends AppCompatActivity {
    private TripViewModel tripViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripViewModel = new TripViewModel();
        
        // Start with the location search fragment
        if (savedInstanceState == null) {
            navigateToFragment(new LocationSearchFragment());
        }
    }

    public void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public TripViewModel getTripViewModel() {
        return tripViewModel;
    }
} 