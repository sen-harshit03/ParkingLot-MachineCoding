package org.example.model;

import lombok.*;
import org.example.constants.VehicleType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Builder
public class ParkingSlot {

    private SlotAddress address;
    private VehicleType vehicleType;
    private boolean isFree;

}
