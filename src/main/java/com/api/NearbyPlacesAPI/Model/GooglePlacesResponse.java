package com.api.NearbyPlacesAPI.Model;

import java.util.List;

public class GooglePlacesResponse {
    private List<GooglePlace> places;

    public List<GooglePlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<GooglePlace> places) {
        this.places = places;
    }
}
