package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Credential;


public interface UserService<T> extends Service<T> {
    boolean authentication(Credential credential);
}
