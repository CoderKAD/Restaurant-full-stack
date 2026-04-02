package com.restaurantapp.demo.dto.ResponseDto.authRespons;
import com.restaurantapp.demo.entity.enums.Role;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Role role;
}
