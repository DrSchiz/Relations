package com.example.demo.repositories;

import com.example.demo.models.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Passport findByNumber(String number);
}
