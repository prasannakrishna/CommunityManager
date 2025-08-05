package com.bhagwat.retail.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashKeyRequest {
    @NotBlank(message = "Hash key cannot be empty")
    @Size(min = 3, max = 17, message = "Hash key must be between 3 and 17 characters long") // MODIFIED: Min length 3
    private String hashKey;

    @NotBlank(message = "Data value cannot be empty")
    private String dataValue;

    private int weight;

}