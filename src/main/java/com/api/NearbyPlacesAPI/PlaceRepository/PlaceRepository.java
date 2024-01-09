package com.api.NearbyPlacesAPI.PlaceRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.NearbyPlacesAPI.Model.Place;


public interface PlaceRepository extends JpaRepository<Place, Long> {
	List<Place> findByLongitudeAndLatitudeAndRadius(Double longitude, Double latitude, Double radius);
}

