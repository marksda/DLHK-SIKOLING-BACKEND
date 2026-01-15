package com.cso.sikoling.main.util;

import java.util.UUID;
import com.github.f4b6a3.uuid.UuidCreator;

public final class GeneratorID {
    
    public static String getUserId() {
        UUID uuid = UuidCreator.getTimeOrderedEpoch();
        return uuid.toString();
    }
    
    public static String LPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "")
                .replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length()
            );
    }
}
