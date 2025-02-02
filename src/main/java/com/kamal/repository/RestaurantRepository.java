package com.kamal.repository;

import com.kamal.model.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    //Search and Filter Methods
    List<Restaurant> findByCuisineTypeIgnoreCase(String cuisineType);

    @Query("SELECT r FROM Restaurant r WHERE r.isOpen = true")
    List<Restaurant> findByIsOpenTrue();

    List<Restaurant> findByRatingGreaterThanEqual(Double minRating);

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    //Analytical Methods
    List<Restaurant> findTop10ByOrderByRatingDesc(Pageable pageable);
}
