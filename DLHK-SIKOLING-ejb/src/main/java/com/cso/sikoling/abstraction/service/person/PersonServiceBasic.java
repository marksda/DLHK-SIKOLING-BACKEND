package com.cso.sikoling.abstraction.service.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class PersonServiceBasic implements DAOService<Person> {
    
    private final Repository<Person, QueryParamFilters, Filter> repository;

    public PersonServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public Person save(Person t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Person update(Person t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Person updateId(String idLama, Person t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Person> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
