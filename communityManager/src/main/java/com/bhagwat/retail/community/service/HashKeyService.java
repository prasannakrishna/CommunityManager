package com.bhagwat.retail.community.service;

import com.bhagwat.retail.community.dto.HashKeyResponse;
import com.bhagwat.retail.community.entity.HashKey;
import com.bhagwat.retail.community.repository.HashKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HashKeyService {
    private final HashKeyRepository hashKeyRepository;

    public ResponseEntity<HashKeyResponse> updateWeightToHashKey(String hashKey, int delta) {
        // Find the existing hash key
        HashKey existingHashKey = hashKeyRepository.findById(hashKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hash key '" + hashKey + "' not found."));

        // Calculate new weight percentage, capping at 100
        int currentWeight = existingHashKey.getWeightPercentage();
        double newWeightDouble = currentWeight + (100.0 - currentWeight) * (delta / 100.0);

        // Round up the double value to the nearest integer
        int newWeight = (int) Math.ceil(newWeightDouble);

        // Ensure the final integer weight does not exceed 100
        newWeight = Math.min(newWeight, 100);
        existingHashKey.setWeightPercentage(newWeight);
        HashKey updatedHashKey = hashKeyRepository.save(existingHashKey);

        return new ResponseEntity<>(new HashKeyResponse(updatedHashKey), HttpStatus.OK);
    }
}
