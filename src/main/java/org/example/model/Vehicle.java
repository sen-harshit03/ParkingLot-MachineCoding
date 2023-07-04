package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constants.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    private VehicleType vehicleType;
    private String regNo;
    private String colour;
    private String ticketId;

}

