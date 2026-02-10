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
public class CategoryMenuResponseDto {
    private UUID id;
    private String categoryName;
    private Integer sortOrder;
    private Boolean active;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime updatedAt;
}
