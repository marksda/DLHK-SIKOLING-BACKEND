package com.cso.sikoling.main.repository.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class PersonRepositoryEJB implements Repository<Person, QueryParamFilters, Filter> {
    
    @Inject
    private PersonRepositoryJPA personRepositoryJPA;

    @Override
    public Person updateId(String idLama, Person t) throws SQLException {
        return personRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Person save(Person t) throws SQLException {
        return personRepositoryJPA.save(t);
    }

    @Override
    public Person update(Person t) throws SQLException {
        return personRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return personRepositoryJPA.delete(id);
    }

    @Override
    public List<Person> getDaftarData(QueryParamFilters q) {
        return personRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return personRepositoryJPA.getJumlahData(f);
    }
    
}
