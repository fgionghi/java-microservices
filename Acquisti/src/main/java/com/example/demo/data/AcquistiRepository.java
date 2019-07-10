package com.example.demo.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.domain.Acquisti;

public interface AcquistiRepository extends MongoRepository<Acquisti, String> {
	
	//definiamo campi speciali che lavorano su campi precisi (findAll e findById esistono di default) con camel case
	Page<Acquisti> findByUserId(String userId, Pageable pageRequest);
}
