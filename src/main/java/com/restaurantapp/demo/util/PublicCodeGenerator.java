package com.restaurantapp.demo.util;

import java.util.function.Predicate;

public final class PublicCodeGenerator {
    private PublicCodeGenerator() {
    }

    public static String generateOrderCode(long start, Predicate<String> existsCheck) {
        return generate("ORD", start, existsCheck);
    }

    public static String generateTableCode(long start, Predicate<String> existsCheck) {
        return generate("TAB", start, existsCheck);
    }

    private static String generate(String prefix, long start, Predicate<String> existsCheck) {
        if (prefix == null || prefix.isBlank()) {
            throw new IllegalArgumentException("prefix is required");
        }
        if (existsCheck == null) {
            throw new IllegalArgumentException("existsCheck predicate is required");
        }

        long next = Math.max(start, 1);
        String candidate;
        do {
            candidate = String.format("%s-%04d", prefix, next++);
        } while (existsCheck.test(candidate));

        return candidate;
    }
}
