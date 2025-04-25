package fm.mrc.tripai.model;

public class Place {
    private String id;
    private String name;
    private String address;
    private double rating;
    private String photoReference;
    private String type;

    public Place() {
    }

    public Place(String id, String name, String address, double rating, String photoReference, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.photoReference = photoReference;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 