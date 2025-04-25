package fm.mrc.tripai.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import fm.mrc.tripai.model.Place;
import fm.mrc.tripai.model.TripDetails;
import fm.mrc.tripai.repository.TripRepository;

public class TripViewModel extends AndroidViewModel {
    private final MutableLiveData<TripDetails> tripDetails = new MutableLiveData<>();
    private final MutableLiveData<List<Place>> placesToVisit = new MutableLiveData<>();
    private final MutableLiveData<List<Place>> hotels = new MutableLiveData<>();
    private final TripRepository repository;

    public TripViewModel(Application application) {
        super(application);
        tripDetails.setValue(new TripDetails());
        repository = new TripRepository(application);
    }

    public LiveData<TripDetails> getTripDetails() {
        return tripDetails;
    }

    public LiveData<List<Place>> getPlacesToVisit() {
        return placesToVisit;
    }

    public LiveData<List<Place>> getHotels() {
        return hotels;
    }

    public void setLocation(String location) {
        TripDetails currentDetails = tripDetails.getValue();
        if (currentDetails != null) {
            currentDetails.setLocation(location);
            tripDetails.setValue(currentDetails);
        }
    }

    public void setTravelerType(String travelerType) {
        TripDetails currentDetails = tripDetails.getValue();
        if (currentDetails != null) {
            currentDetails.setTravelerType(travelerType);
            tripDetails.setValue(currentDetails);
        }
    }

    public void setBudgetType(String budgetType) {
        TripDetails currentDetails = tripDetails.getValue();
        if (currentDetails != null) {
            currentDetails.setBudgetType(budgetType);
            tripDetails.setValue(currentDetails);
        }
    }

    public void setStartDate(java.util.Date startDate) {
        TripDetails currentDetails = tripDetails.getValue();
        if (currentDetails != null) {
            currentDetails.setStartDate(startDate);
            tripDetails.setValue(currentDetails);
        }
    }

    public void setEndDate(java.util.Date endDate) {
        TripDetails currentDetails = tripDetails.getValue();
        if (currentDetails != null) {
            currentDetails.setEndDate(endDate);
            tripDetails.setValue(currentDetails);
        }
    }

    public void loadPlacesAndHotels() {
        TripDetails details = tripDetails.getValue();
        if (details != null) {
            // Load places to visit
            CompletableFuture<List<Place>> placesFuture = repository.getPlacesToVisit(details);
            placesFuture.thenAccept(places -> placesToVisit.postValue(places));

            // Load hotels
            CompletableFuture<List<Place>> hotelsFuture = repository.getHotels(details);
            hotelsFuture.thenAccept(hotelsList -> hotels.postValue(hotelsList));
        }
    }
} 