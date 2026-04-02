package com.restaurantapp.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping("/all")
    public ResponseEntity<String> allAccess() {
        return ResponseEntity.ok("Accessible by any authenticated user");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN','KITCHEN','CASHIER')")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("Protected user content");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Admin-only content");
    }
}
