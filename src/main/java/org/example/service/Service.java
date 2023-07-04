package org.example.service;

import lombok.ToString;
import org.example.constants.VehicleType;
import org.example.model.*;

import java.util.*;

@ToString
public class Service {

    ParkingLot parkingLot = ParkingLot.getInstance();

    Dashboard dashboard = Dashboard.getDashboard();
    Map<VehicleType, Map<Integer, Integer>> freeSlotMap = new HashMap<>();
    Map<VehicleType, Map<Integer, Integer>> occupiedSlotMap = new HashMap<>();

    public void createParkingLot(String parkinglotId, int noOfFloors, int noOfSlots) {

        parkingLot.setPARKING_LOT_ID(parkinglotId);
        parkingLot.setNoOfFloors(noOfFloors);
        parkingLot.setNoOfSlotsPerFloor(noOfSlots);
        parkingLot.setFloors(createParkingFloor(noOfFloors, noOfSlots));
        System.out.println(parkingLot);
        initializeDashboard(noOfSlots, noOfFloors);
    }

    public void parkVehicle(String vehicleType, String regNo, String color) {
        VehicleType type = VehicleType.valueOf(vehicleType.toUpperCase());

        SlotAddress address = searchForEmptySlot(type);

        if(Objects.isNull(address)) {
            System.out.println("No parking available in the lot for this vehicle type");
            return;
        }

        System.out.println(vehicleType + " parked at floor " + address.getLevelId() + " and slot " + address.getSlotId());

        String ticketId = generateTicketId(address);
        System.out.println("Ticket ID : " + ticketId);
    }

    private List<Floor> createParkingFloor(int floorCount, int slotCount) {

        List<Floor> floorList = new ArrayList<>();
        for(int floor = 1; floor <= floorCount; floor++) {
            floorList.add(Floor.builder()
                    .floorId(floor)
                    .noOfSlots(slotCount)
                    .parkingSlotList(createParkingSlot(slotCount, floor))
                    .build()
            );
        }
        return floorList;
    }

    private List<ParkingSlot> createParkingSlot(int slotCount, int floorNumber) {
        List<ParkingSlot> parkingSlotList = new ArrayList<>();

        for(int count = 1; count <= slotCount; count++) {
            parkingSlotList.add(ParkingSlot.builder()
                    .vehicleType(getVehicleType(count))
                    .address(getAddress(floorNumber, count))
                    .isFree(true)
                    .build()
            );
        }

        return parkingSlotList;
    }

    private VehicleType getVehicleType(int count) {
        if(count == 1) {
            return VehicleType.TRUCK;
        } else if(count == 2 || count == 3) {
            return VehicleType.BIKE;
        }
        return VehicleType.CAR;
    }

    private SlotAddress getAddress(int floorNo, int slotNo) {
        return SlotAddress.builder()
                .levelId(floorNo)
                .slotId(slotNo)
                .build();
    }

    private String generateTicketId(SlotAddress address) {
        return parkingLot.getPARKING_LOT_ID() + "_" + address.getLevelId() + "_" + address.getSlotId();
    }

    private SlotAddress searchForEmptySlot(VehicleType vehicleType) {

        for(Floor floor : parkingLot.getFloors()) {
            for (ParkingSlot slot : floor.getParkingSlotList()) {
                if (slot.getVehicleType() == vehicleType && slot.isFree()) {
                    slot.setFree(false);
                    updateOnSuccessfulParking(vehicleType, floor.getFloorId());
                    return slot.getAddress();
                }
            }
        }
        return null;
    }

    private void updateOnSuccessfulParking(VehicleType vehicleType, int floorId) {
        int occupiedCount = occupiedSlotMap.get(vehicleType).get(floorId);
        occupiedSlotMap.get(vehicleType).put(floorId, occupiedCount + 1);
        int freeCount = freeSlotMap.get(vehicleType).get(floorId);
        freeSlotMap.get(vehicleType).put(floorId, freeCount - 1);
    }

    private void initializeDashboard(int noOfSlots, int noOfFloors) {
        Map<Integer, Integer> truckFreeSlotMap = new HashMap<>();
        Map<Integer, Integer> carFreeSlotMap = new HashMap<>();
        Map<Integer, Integer> bikeFreeSlotMap = new HashMap<>();

        Map<Integer, Integer> truckOccupiedSlotMap = new HashMap<>();
        Map<Integer, Integer> carOccupiedSlotMap = new HashMap<>();
        Map<Integer, Integer> bikeOccupiedSlotMap = new HashMap<>();
        for(int floor = 1; floor<= noOfFloors; floor++ ) {
            truckFreeSlotMap.put(floor, 1);
            bikeFreeSlotMap.put(floor, 2);
            carFreeSlotMap.put(floor, noOfSlots - 3);

            truckOccupiedSlotMap.put(floor, 0);
            carOccupiedSlotMap.put(floor, 0);
            bikeOccupiedSlotMap.put(floor, 0);
        }
        freeSlotMap.put(VehicleType.TRUCK, truckFreeSlotMap);
        freeSlotMap.put(VehicleType.CAR, carFreeSlotMap);
        freeSlotMap.put(VehicleType.BIKE, bikeFreeSlotMap);

        occupiedSlotMap.put(VehicleType.TRUCK, truckOccupiedSlotMap);
        occupiedSlotMap.put(VehicleType.BIKE, bikeOccupiedSlotMap);
        occupiedSlotMap.put(VehicleType.CAR, carOccupiedSlotMap);
    }

    public void display(String displayType, String vehicleType) {
        VehicleType type = VehicleType.valueOf(vehicleType.toUpperCase());

        if(Objects.equals(displayType, "free_count")) {
            Map<Integer, Integer> freeCountMap = freeSlotMap.get(type);
            for(Map.Entry entry : freeCountMap.entrySet()){
                System.out.println("Floor " + entry.getKey() + " has " + entry.getValue() + " slots available for " + vehicleType);
            }

        } else if(Objects.equals(displayType, "occupied_count")) {
            Map<Integer, Integer> occupiedCountMap = occupiedSlotMap.get(type);
            for(Map.Entry entry : occupiedCountMap.entrySet()){
                System.out.println("Floor " + entry.getKey() + " has " + entry.getValue() + " occupied slots for " + vehicleType);
            }
        }
    }

    public void unparkVehicle(String ticketId) {
        String[] array = ticketId.split("_");
        int floorId = Integer.parseInt(array[1]);
        int slotId = Integer.parseInt(array[2]);

        ParkingSlot slot = parkingLot.getFloors().get(floorId - 1).getParkingSlotList().get(slotId - 1);
        VehicleType vehicleType = slot.getVehicleType();
        slot.setFree(true);
        updateOnUnParkVehicle(vehicleType, floorId);
        System.out.println(vehicleType + " unparked from " + "floor " + floorId + " slot " + slotId);
    }

    private void updateOnUnParkVehicle(VehicleType vehicleType, int floorId) {
        int occupiedCount = occupiedSlotMap.get(vehicleType).get(floorId);
        occupiedSlotMap.get(vehicleType).put(floorId, occupiedCount - 1);
        int freeCount = freeSlotMap.get(vehicleType).get(floorId);
        freeSlotMap.get(vehicleType).put(floorId, freeCount + 1);
    }


}
