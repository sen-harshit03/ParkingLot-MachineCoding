package org.example.model;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.constants.VehicleType;

import java.util.Map;


public class Dashboard {

    static Dashboard dashboard;
    static Map<VehicleType, Map<Integer, Integer>> freeSlotMap;
    static Map<VehicleType, Map<Integer, Integer>> occupiedSlotMap;

    private Dashboard() {

    }

    public static Dashboard getDashboard() {
        if(dashboard == null) {
            dashboard = new Dashboard();
        }
        return dashboard;
    }
}
