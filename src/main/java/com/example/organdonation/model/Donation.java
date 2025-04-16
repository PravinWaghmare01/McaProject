package com.example.organdonation.model;

import jakarta.persistence.*;

@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String organType;
    private String donorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganType() {
        return organType;
    }

    public void setOrganType(String organType) {
        this.organType = organType;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}