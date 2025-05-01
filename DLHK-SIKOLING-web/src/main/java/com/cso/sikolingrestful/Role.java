package com.cso.sikolingrestful;

import java.util.HashMap;
import java.util.Map;


public enum Role implements Labeled {
    ADMINISTRATOR("Administrator"), 
    ADMIN("Admin/KA"),
    TU("Tata Usaha"),
    KABID("Kepala Bidang"),
    KASUBID("Kepala Sub Bidang"),
    BO("Back Office"),
    UMUM("Pemohon"),
    FO("Front Office"),
    ARSIP("Arsip");
    
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
