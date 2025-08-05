package com.bhagwat.retail.community.controller;

import com.bhagwat.retail.community.dto.HashKeyRequest;
import com.bhagwat.retail.community.dto.HashKeyResponse;
import com.bhagwat.retail.community.entity.HashKey;
import com.bhagwat.retail.community.repository.HashKeyRepository;
import com.bhagwat.retail.community.service.HashKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hashkeys") // Base path for these endpoints
public class HashKeyController {

    private HashKeyService hashKeyService;
    private HashKeyRepository hashKeyRepository;
    /**
     * POST /api/hashkeys
     * Stores a new hash key and its associated data value.
     * Returns 201 Created on success, 409 Conflict if key already exists, 400 Bad Request on validation error.
     * @param request The HashKeyRequest DTO containing hashKey and dataValue.
     * @return ResponseEntity with HashKeyResponse.
     */
    @PostMapping
    public ResponseEntity<HashKeyResponse> createHashKey(@Valid @RequestBody HashKeyRequest request) {
        // Check if the hash key already exists
        if (hashKeyRepository.existsById(request.getHashKey())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hash key '" + request.getHashKey() + "' already exists.");
        }

        HashKey hashKey = new HashKey();
        hashKey.setHashKey(request.getHashKey());
        hashKey.setDataValue(request.getDataValue());
        // createdAt is automatically set by @CreationTimestamp

        HashKey savedHashKey = hashKeyRepository.save(hashKey);
        return new ResponseEntity<>(new HashKeyResponse(savedHashKey), HttpStatus.CREATED);
    }

    /**
     * GET /api/hashkeys/{hashKey}
     * Retrieves the data value associated with a given hash key.
     * Returns 200 OK on success, 404 Not Found if key does not exist.
     * @param hashKey The hash key to retrieve.
     * @return ResponseEntity with HashKeyResponse.
     */
    @GetMapping("/{hashKey}")
    public ResponseEntity<HashKeyResponse> getHashKey(@PathVariable String hashKey) {
        // Validate hashKey length from path variable
        if (hashKey.length() < 3 || hashKey.length() > 17) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hash key must be between 3 and 17 characters long.");
        }

        Optional<HashKey> foundHashKey = hashKeyRepository.findById(hashKey);

        return foundHashKey.map(hk -> new ResponseEntity<>(new HashKeyResponse(hk), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hash key '" + hashKey + "' not found."));
    }

    /**
     * GET /api/hashkeys/search?prefix={prefix}
     * Searches for hash keys that start with the given prefix.
     * Returns 200 OK with a list of matching HashKeyResponse objects.
     * @param prefix The partial string to search for (min 1 character, max 17 characters).
     * @return ResponseEntity with a list of HashKeyResponse.
     */
    @GetMapping("/search")
    public ResponseEntity<List<HashKeyResponse>> searchHashKeysByPrefix(@RequestParam String prefix) {
        // Basic validation for the prefix length
        if (prefix == null || prefix.isEmpty() || prefix.length() > 17) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search prefix must be between 1 and 17 characters long.");
        }

        List<HashKey> foundHashKeys = hashKeyRepository.findByHashKeyStartingWith(prefix);

        List<HashKeyResponse> responses = foundHashKeys.stream()
                .map(HashKeyResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<HashKeyResponse>> createHashKeysBatch(/*@Valid*/ @RequestBody List<HashKeyRequest> requests) {
        if (requests.size() > 1000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Batch size cannot exceed 1000 keys.");
        }

        List<HashKeyResponse> savedResponses = new ArrayList<>();
        List<HashKey> newHashKeys = new ArrayList<>();

        for (HashKeyRequest request : requests) {
            // Check if the hash key already exists before attempting to save
            if (!hashKeyRepository.existsById(request.getHashKey())) {
                HashKey hashKey = new HashKey();
                hashKey.setHashKey(request.getHashKey());
                hashKey.setDataValue(request.getDataValue());
                newHashKeys.add(hashKey);
            } else {
                System.out.println("Skipping duplicate hash key: " + request.getHashKey());
            }
        }

        // Save all new hash keys in a single batch operation for efficiency
        List<HashKey> savedHashKeys = hashKeyRepository.saveAll(newHashKeys);

        savedResponses = savedHashKeys.stream()
                .map(HashKeyResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(savedResponses, HttpStatus.OK);
    }

    @PatchMapping("/{hashKey}/increment-weight")
    public ResponseEntity<HashKeyResponse> incrementHashKeyWeight(
            @PathVariable String hashKey,
            @RequestParam(name = "delta", defaultValue = "1") int delta) { // Default delta is 1

        return hashKeyService.updateWeightToHashKey(hashKey, delta);
    }


}