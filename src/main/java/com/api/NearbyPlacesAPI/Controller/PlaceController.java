package com.api.NearbyPlacesAPI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.NearbyPlacesAPI.Model.Place;
import com.api.NearbyPlacesAPI.Service.PlaceService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Place>> getNearbyPlaces(
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam Double radius) {
        List<Place> nearbyPlaces = placeService.getNearbyPlaces(longitude, latitude, radius);
        return ResponseEntity.ok(nearbyPlaces);
    }
}
