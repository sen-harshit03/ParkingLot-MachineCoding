package org.example;

import org.example.constants.CommandType;
import org.example.service.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Service service = new Service();



        while(true) {
            Scanner scanner = new Scanner(System.in);
            CommandType type = CommandType.getCommandType(scanner.next());
            switch (type) {
                case CREATE_PARKING_LOT:
                    service.createParkingLot(scanner.next(), scanner.nextInt(), scanner.nextInt());
                    break;

                case PARK_VEHICLE:
                    service.parkVehicle(scanner.next(), scanner.next(), scanner.next());
                    break;

                case DISPLAY:
                    service.display(scanner.next(), scanner.next());
                    break;

                case UNPARK_VEHICLE:
                    service.unparkVehicle(scanner.next());
                    break;

                case EXIT: return;

            }

            System.out.println();
        }



    }
}