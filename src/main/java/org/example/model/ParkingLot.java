package org.example.model;
import lombok.*;

import java.util.List;




@Getter
@Data
public class ParkingLot {

    private static ParkingLot parkingLot;
    private String PARKING_LOT_ID;
    private int noOfFloors;
    int noOfSlotsPerFloor;
    private List<Floor> floors;

    public static ParkingLot getInstance() {
        if(parkingLot == null) {
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

}
