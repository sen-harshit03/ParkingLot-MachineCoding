package org.example.constants;
import java.util.*;



public enum CommandType {
    CREATE_PARKING_LOT("create_parking_lot"),
    PARK_VEHICLE("park_vehicle"),
    UNPARK_VEHICLE("unpark_vehicle"),
    DISPLAY("display"),
    EXIT("exit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    private final static Map<String, CommandType> commandTypeMap = new HashMap<>();

    static {
        for (CommandType commandType : CommandType.values()) {
            commandTypeMap.put(commandType.command, commandType);
        }
    }

    public static CommandType getCommandType(String type) {
        return commandTypeMap.get(type);
    }

}
