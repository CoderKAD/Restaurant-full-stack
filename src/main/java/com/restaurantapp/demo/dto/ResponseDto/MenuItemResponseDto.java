package com.restaurantapp.demo.dto.ResponseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Boolean active;
    private String imageUrl;
    private String prepStation;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
    private UUID categoryId;
    private String categoryName;
}
