package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.util.HashMap;
import java.util.Map;

public enum AuthorizationGrantType implements Labeled {
    CODE("Authorization Code"),                         //front + back channel
    IMPLICIT("Implicit"),                               //front channel only
    PASSWORD("Resource Owner Password Credentials"),    //back channel only
    CLIENT("Client Credentials");                       //back channel only
    
    public final String label;
    
    private static final Map<String, AuthorizationGrantType> BY_LABEL = new HashMap<>();
    
    static {
        for (AuthorizationGrantType agt : values()) {
            BY_LABEL.put(agt.label, agt);
        }
    }    
    
    private AuthorizationGrantType(String label) {
        this.label = label;
    }
    
    public static AuthorizationGrantType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    @Override
    public String label() {
        return label;
    }
        
}
