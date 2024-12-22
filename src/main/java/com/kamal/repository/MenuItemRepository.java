package com.kamal.repository;

import com.kamal.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT m FROM MenuItem m WHERE m.isAvailable = true")
    List<MenuItem> findByIsAvailableTrue();

    List<MenuItem> findByNameContainingIgnoreCase(String name);

    List<MenuItem> findAllByOrderByPriceAsc();

    List<MenuItem> findAllByOrderByPriceDesc();

    @Query("SELECT m FROM MenuItem M JOIN M.order o GROUP BY m ORDER BY COUNT(o) DESC")
    List<MenuItem> findPopularMenuItems(int limit);


}
