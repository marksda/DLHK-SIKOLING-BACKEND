package com.cso.sikolingrestful;

import java.util.HashMap;
import java.util.Map;

public enum Role implements Labeled {
    UNTULAN("00"),
    ADMINISTRATOR("01"), 
    ADMIN("02"),
    TU("03"),
    KABID("04"),
    KASUBID("05"),
    BO("06"),
    FO("07"),
    ARSIP("08"),
    UMUM("09");
    
    public final String label;
    
    private static final Map<String, Role> BY_LABEL = new HashMap<>();
    
    static {
        for (Role agt : values()) {
            BY_LABEL.put(agt.label, agt);
        }
    }    
    
    private Role(String label) {
        this.label = label;
    }
    
    public static Role valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    @Override
    public String label() {
        return label;
    }
    
}
