package com.dailyup.dup_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailyup.dup_backend.entity.SalesData;
import com.dailyup.dup_backend.repository.SalesDataRepository;

@Service
public class SalesDataService {

    @Autowired
    private SalesDataRepository repository;

    // Create new sales data
    public SalesData createData(SalesData data) {
        return repository.save(data);
    }

    // Read all sales data
    public List<SalesData> getAllData() {
        return repository.findAll();
    }

     // Read sales data by ID
     public Optional<SalesData> getDataById(Long id) {
        return repository.findById(id);
    }

    // Update existing sales data
    public SalesData updateData(Long id, SalesData updatedData) {
        SalesData existingData = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found for ID: " + id));

        existingData.setPartName(updatedData.getPartName());
        existingData.setQuantity(updatedData.getQuantity());
        existingData.setPrice(updatedData.getPrice());
        return repository.save(existingData);
    }

    // Delete sales data by ID
    public void deleteData(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Data not found for ID: " + id);
        }
    }

     // Sell product and update quantity
     public SalesData sellProduct(Long id, int soldQuantity) {
        SalesData data = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found for ID: " + id));

        if (data.getQuantity() >= soldQuantity) {
            data.setQuantity(data.getQuantity() - soldQuantity);
            return repository.save(data);
        } else {
            throw new RuntimeException("Insufficient stock. Available quantity: " + data.getQuantity());
        }
    }
}