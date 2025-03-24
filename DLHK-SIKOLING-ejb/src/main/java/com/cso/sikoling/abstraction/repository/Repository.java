package com.cso.sikoling.abstraction.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T, Q, F> {
    T save(T t) throws SQLException;
    T update(T t) throws SQLException;
    boolean delete(String id) throws SQLException;
    T updateId(String idLama, T t) throws SQLException;
    List<T> getDaftarData(Q q);
    Long getJumlahData(List<F> f);
}
