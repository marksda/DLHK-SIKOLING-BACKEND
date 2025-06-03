package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;


public interface UserService<T> extends Service<T> {
    User authentication(Credential credential);
}
