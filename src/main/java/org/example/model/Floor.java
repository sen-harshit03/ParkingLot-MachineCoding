package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.constants.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Floor {

    private int floorId;
    private int noOfSlots;
    private List<ParkingSlot> parkingSlotList;

    Map<VehicleType, Integer> freeSlot = new HashMap<>();
    Map<VehicleType, Integer> occupiedSlot = new HashMap<>();
}
