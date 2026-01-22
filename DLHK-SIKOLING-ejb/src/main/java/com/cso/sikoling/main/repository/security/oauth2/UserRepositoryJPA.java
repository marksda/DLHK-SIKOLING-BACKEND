package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.repository.Repository;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class UserRepositoryJPA implements Repository<User, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public UserRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public User save(User t) throws SQLException {   
        try {
            UserData userData = convertUserToUserData(t);
            entityManager.persist(userData);
            entityManager.flush();             
            return convertUserDataToUser(userData);  
        } 
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data user");
        }        
    }

    @Override
    public User update(User t) throws SQLException {
        
        try {
            UserData userData = convertUserToUserData(t);  
            userData = entityManager.merge(userData);
            return convertUserDataToUser(userData);   
        } 
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data user");
        }
        
    }

    @Override
    public User updateId(String idLama, User t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("UserData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {    
                return t;
            }
            else {
                throw new SQLException("Gagal mengupdate id user");
            }
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi id user");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            UserData userAksesData = entityManager.find(UserData.class, id);
            if(userAksesData != null) {
                entityManager.remove(userAksesData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("hak akses dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hak akses harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<User> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserData> cq = cb.createQuery(UserData.class);
            Root<UserData> root = cq.from(UserData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> {
                            daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        }
                        case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "tanggal" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
                        case "rentang_tanggal" -> {
                            Jsonb jsonb = JsonbBuilder.create();
                            JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");                            
                            Date fromDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("from"), formatter));
                            Date toDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("to"), formatter));
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggalRegistrasi"), 
                                    fromDate, 
                                    toDate
                                )
                            );
                        }
                        default -> {}
                    }			
                }

                if(daftarPredicate.isEmpty()) {
                    cq.select(root);
                }
                else {
                    cq.select(root).where(cb.and(daftarPredicate.toArray(new Predicate[0])));
                }
            }        

            // sort clause
            if(q.getDaftarFieldsSorter() != null) {
                Iterator<SortOrder> iterSort = q.getDaftarFieldsSorter().iterator();
                while (iterSort.hasNext()) {
                    SortOrder sort = (SortOrder) iterSort.next();
                    switch (sort.getField_name()) {
                        case "id" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("id")));
                            }
                        }
                        case "user_name" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("userName")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("userName")));
                            }
                        }
                        case "tanggal_registrasi" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("tanggalRegistrasi")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("tanggalRegistrasi")));
                            }
                        }
                        default -> {}
                    }			
                }
            }


            TypedQuery<UserData> typedQuery;	

            if( q.isIsPaging()) { 
                Paging paging = q.getPaging();
                typedQuery = entityManager.createQuery(cq)
                                .setMaxResults(paging.getPage_size())
                                .setFirstResult((paging.getPage_number()-1)*paging.getPage_size());
            }
            else {
                typedQuery = entityManager.createQuery(cq);
            }

            return typedQuery.getResultList()
                            .stream()
                            .map(d -> convertUserDataToUser(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("UserData.findAll", UserData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertUserDataToUser(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<UserData> root = cq.from(UserData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
                case "tanggal" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
                case "rentang_tanggal" -> {
                    Jsonb jsonb = JsonbBuilder.create();
                    JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");                            
                    Date fromDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("from"), formatter));
                    Date toDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("to"), formatter));

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggalRegistrasi"), 
                            fromDate, 
                            toDate
                        )
                    );
                }
                default -> {
                }
            }			
        }

        if(daftarPredicate.isEmpty()) {
            cq.select(cb.count(root));
        }
        else {
            cq.select(cb.count(root)).where(cb.and(daftarPredicate.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(cq).getSingleResult();
        
    }
    
    private User convertUserDataToUser(UserData d) {
        User user = null;
		
        if(d != null) {
            user = new User(
                    d.getId(), d.getUserName(), d.getPassword(), 
                    d.getTanggalRegistrasi(), d.getHashing_password_type().getId());
        }

        return user;	
    }
    
    private UserData convertUserToUserData(User t) {
        UserData userData = null;
		
        if(t != null) {
            userData = new UserData();
            userData.setId(t.getId());
            userData.setUserName(t.getUser_name());
            userData.setPassword(t.getPassword());
            userData.setTanggalRegistrasi(t.getTanggal_registrasi());
            userData.setHashing_password_type(new HashingPasswordTypeData(t.getHashing_password_type_id()));            
        }

        return userData;
    }

}
