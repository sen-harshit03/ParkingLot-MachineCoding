package org.example.constants;

import java.util.HashMap;
import java.util.Map;

public enum DisplayType {

    FREE_COUNT("free_count"),
    OCCUPIED_COUNT("occupied_count");

    private final String type;

    DisplayType(String type) {
        this.type = type;
    }

    private static final Map<String, DisplayType> displayTypeMap = new HashMap<>(values().length);

    static {
        for (DisplayType displayType : DisplayType.values()) {
            displayTypeMap.put(displayType.type, displayType);
        }
    }

    public static DisplayType getDisplayType(String type) {
        return displayTypeMap.get(type);
    }

}
