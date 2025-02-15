package com.dailyup.dup_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dailyup.dup_backend.entity.SalesData;

public interface SalesDataRepository extends JpaRepository<SalesData, Long>{
    @Query("SELECT s FROM SalesData s WHERE s.quantity < :threshold")
    List<SalesData> findLowStockProducts(@Param("threshold") int threshold);
}
