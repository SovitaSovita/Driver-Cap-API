package com.kshrd.demobasicauth.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourTypeRequest {
    private String title;
    private String description;
}
