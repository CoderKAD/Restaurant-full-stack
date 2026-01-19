package com.example.restaurant.mapper;

import com.example.restaurant.dto.request.CustomerRequestDto;
import com.example.restaurant.dto.response.CustomerResponseDto;
import com.example.restaurant.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CastomerMapper {
    Customer toCustomerEntity(CustomerRequestDto customerDto);
    CustomerResponseDto toCustpmerDto(Customer customer);
    List<CustomerResponseDto> toCustpmerDto(List<Customer> customers);
}
