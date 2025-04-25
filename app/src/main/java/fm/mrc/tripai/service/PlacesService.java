package fm.mrc.tripai.service;

import android.content.Context;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.net.PlacesSearchResponse;
import com.google.android.libraries.places.api.net.SearchNearbyRequest;
import com.google.android.libraries.places.api.net.SearchNearbyResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import fm.mrc.tripai.model.Place;

public class PlacesService {
    private static final String API_KEY = "YOUR_API_KEY";
    private final PlacesClient placesClient;

    public PlacesService(Context context) {
        if (!Places.isInitialized()) {
            Places.initialize(context, API_KEY);
        }
        placesClient = Places.createClient(context);
    }

    public CompletableFuture<List<Place>> searchNearbyPlaces(String location, String type) {
        CompletableFuture<List<Place>> future = new CompletableFuture<>();
        
        // Create a request for nearby places
        SearchNearbyRequest request = SearchNearbyRequest.builder()
            .setLocationBias(com.google.android.libraries.places.api.model.LocationBias.circular(
                com.google.android.libraries.places.api.model.LatLng.parse(location), 5000))
            .setTypeFilter(Arrays.asList(type))
            .build();

        placesClient.searchNearby(request).addOnSuccessListener((response) -> {
            List<Place> places = new ArrayList<>();
            for (com.google.android.libraries.places.api.model.Place place : response.getPlaces()) {
                places.add(convertToPlaceModel(place));
            }
            future.complete(places);
        }).addOnFailureListener((exception) -> {
            future.completeExceptionally(exception);
        });

        return future;
    }

    public CompletableFuture<List<Place>> searchHotels(String location, String budgetType) {
        CompletableFuture<List<Place>> future = new CompletableFuture<>();
        
        // Create a request for hotels
        SearchNearbyRequest request = SearchNearbyRequest.builder()
            .setLocationBias(com.google.android.libraries.places.api.model.LocationBias.circular(
                com.google.android.libraries.places.api.model.LatLng.parse(location), 5000))
            .setTypeFilter(Arrays.asList("lodging"))
            .build();

        placesClient.searchNearby(request).addOnSuccessListener((response) -> {
            List<Place> hotels = new ArrayList<>();
            for (com.google.android.libraries.places.api.model.Place place : response.getPlaces()) {
                Place hotel = convertToPlaceModel(place);
                // Filter hotels based on budget type
                if (matchesBudgetType(hotel, budgetType)) {
                    hotels.add(hotel);
                }
            }
            future.complete(hotels);
        }).addOnFailureListener((exception) -> {
            future.completeExceptionally(exception);
        });

        return future;
    }

    private Place convertToPlaceModel(com.google.android.libraries.places.api.model.Place place) {
        Place placeModel = new Place();
        placeModel.setId(place.getId());
        placeModel.setName(place.getName());
        placeModel.setAddress(place.getAddress());
        placeModel.setRating(place.getRating());
        if (place.getPhotoMetadatas() != null && !place.getPhotoMetadatas().isEmpty()) {
            placeModel.setPhotoReference(place.getPhotoMetadatas().get(0).getAttributions());
        }
        return placeModel;
    }

    private boolean matchesBudgetType(Place hotel, String budgetType) {
        // Implement budget type matching logic based on hotel rating or price level
        switch (budgetType.toLowerCase()) {
            case "cheap":
                return hotel.getRating() <= 3.0;
            case "moderate":
                return hotel.getRating() > 3.0 && hotel.getRating() <= 4.0;
            case "luxury":
                return hotel.getRating() > 4.0;
            default:
                return false;
        }
    }
} 