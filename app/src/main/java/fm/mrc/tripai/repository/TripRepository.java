package fm.mrc.tripai.repository;

import android.content.Context;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import fm.mrc.tripai.model.Place;
import fm.mrc.tripai.model.TripDetails;
import fm.mrc.tripai.service.PlacesService;

public class TripRepository {
    private final PlacesService placesService;

    public TripRepository(Context context) {
        placesService = new PlacesService(context);
    }

    public CompletableFuture<List<Place>> getPlacesToVisit(TripDetails tripDetails) {
        // Determine place types based on traveler type
        String placeType = getPlaceTypeForTraveler(tripDetails.getTravelerType());
        
        // Search for places based on location and type
        return placesService.searchNearbyPlaces(tripDetails.getLocation(), placeType);
    }

    public CompletableFuture<List<Place>> getHotels(TripDetails tripDetails) {
        // Search for hotels based on location and budget
        return placesService.searchHotels(tripDetails.getLocation(), tripDetails.getBudgetType());
    }

    private String getPlaceTypeForTraveler(String travelerType) {
        switch (travelerType.toLowerCase()) {
            case "just me":
                return "tourist_attraction"; // Solo traveler might prefer tourist attractions
            case "a couple":
                return "restaurant"; // Couples might prefer romantic restaurants
            case "family":
                return "amusement_park"; // Families might prefer amusement parks
            case "friends":
                return "night_club"; // Friends might prefer nightlife
            default:
                return "tourist_attraction";
        }
    }
} 