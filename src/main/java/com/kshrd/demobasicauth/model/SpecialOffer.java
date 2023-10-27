package com.kshrd.demobasicauth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String price;
    private String duration;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialOffer")
    private List<SpecialOfferImage> imgList;
}
