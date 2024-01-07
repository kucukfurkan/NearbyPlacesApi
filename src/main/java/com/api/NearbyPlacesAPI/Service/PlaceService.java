package com.api.NearbyPlacesAPI.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.api.NearbyPlacesAPI.Model.GooglePlace;
import com.api.NearbyPlacesAPI.Model.GooglePlacesResponse;
import com.api.NearbyPlacesAPI.Model.Place;
import com.api.NearbyPlacesAPI.PlaceRepository.PlaceRepository;

//PlaceService.java
@Service
public class PlaceService {
 private final PlaceRepository placeRepository;

 @Value("${google.places.api.key}")
 private String googleApiKey;
 
 //@Value("${google.places.api.url}")
 //private String googleApiUrl;
 
 @Autowired
 public PlaceService(PlaceRepository placeRepository) {
     this.placeRepository = placeRepository;
 }

 public List<Place> getNearbyPlaces(Double longitude, Double latitude, Double radius) {

     List<Place> googlePlacesResponse = getPlacesFromGoogleAPI(longitude, latitude, radius);

     return googlePlacesResponse;
 }

 private List<Place> getPlacesFromGoogleAPI(Double longitude, Double latitude, Double radius) {
     // Set request body
     Map<String, Object> requestBody = new HashMap<>();
     requestBody.put("includedTypes", Arrays.asList("restaurant"));
     requestBody.put("maxResultCount", 10);

     Map<String, Object> locationRestriction = new HashMap<>();
     Map<String, Object> circle = new HashMap<>();
     Map<String, Object> center = new HashMap<>();
     center.put("latitude", latitude);
     center.put("longitude", longitude);
     circle.put("center", center);
     circle.put("radius", radius);
     locationRestriction.put("circle", circle);

     requestBody.put("locationRestriction", locationRestriction);

     // Set headers
     HttpHeaders headers = new HttpHeaders();
     headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
     headers.set("X-Goog-Api-Key", googleApiKey);
     headers.set("X-Goog-FieldMask", "places.displayName");
     
     String apiUrl = "https://places.googleapis.com/v1/places:searchNearby";
     
     RestTemplate restTemplate = new RestTemplate();
     // Make the request to Google Places API
     ResponseEntity<GooglePlacesResponse> responseEntity = restTemplate.postForEntity(
    		 apiUrl,
             new HttpEntity<>(requestBody, headers),
             GooglePlacesResponse.class
     );

     List<Place> places = convertToPlaces(responseEntity.getBody());

     return places;
 }
 
 private List<Place> convertToPlaces(GooglePlacesResponse googlePlacesResponse) {
	    List<Place> places = new ArrayList<>();

	    if (googlePlacesResponse != null && googlePlacesResponse.getPlaces() != null) {
	        for (GooglePlace googlePlace : googlePlacesResponse.getPlaces()) {
	            Place place = new Place();
	            
	            String displayName = googlePlace.getDisplayName().getText();
	            place.setPlaceName(displayName);

	            places.add(place);
	        }
	    }

	    return places;
	}


}

