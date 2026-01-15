package com.cso.sikoling.abstraction.repository;

public interface RegisterDokumenRepository<T, Q, F> extends Repository<T, Q, F> {    
    String generateId();
}