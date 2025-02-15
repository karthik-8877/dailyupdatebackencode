package com.dailyup.dup_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailyup.dup_backend.entity.SalesData;
import com.dailyup.dup_backend.service.SalesDataService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/sales_data")
public class SalesDataController {
    @Autowired
    private SalesDataService service;

    // Get all sales data
    @GetMapping
    public List<SalesData> getAllSalesData() {
        return service.getAllData();
    }

    // Get sales data by ID
    @GetMapping("/{id}")
    public ResponseEntity<SalesData> getSalesDataById(@PathVariable Long id) {
        Optional<SalesData> data = service.getDataById(id);
        return data.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create sales data
    @PostMapping
    public ResponseEntity<SalesData> createData(@RequestBody SalesData data) {
        SalesData savedData = service.createData(data);
        return ResponseEntity.ok(savedData);
    }

    // Update sales data
    @PutMapping("/{id}")
    public ResponseEntity<SalesData> updateData(@PathVariable Long id, @RequestBody SalesData updatedData) {
        try {
            SalesData updated = service.updateData(id, updatedData);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete sales data by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteData(@PathVariable Long id) {
        try {
            service.deleteData(id);
            return ResponseEntity.ok("Data successfully deleted.");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<String> sellProduct(@PathVariable Long id, @RequestParam int soldQuantity) {
        try {
            SalesData updatedData = service.sellProduct(id, soldQuantity);
            return ResponseEntity.ok("Sale successful! Remaining quantity: " + updatedData.getQuantity());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
