package com.api.NearbyPlacesAPI.PlaceRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.NearbyPlacesAPI.Model.Place;


public interface PlaceRepository extends JpaRepository<Place, Long> {

}

