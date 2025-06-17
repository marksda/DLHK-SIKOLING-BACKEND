package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.Repository;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class TokenRepositoryJPA implements Repository<Token, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public TokenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Token save(Token t) throws SQLException {
        try {
            TokenData tokenData = convertTokenToTokenData(t);
            entityManager.persist(tokenData);
            entityManager.flush();
            return convertTokenDataToToken(tokenData);
        }
        catch(PersistenceException e) {
            throw new SQLException("Duplikasi data token");
        }       
    }

    @Override
    public Token update(Token t) throws SQLException {
        try {
            TokenData tokenData = convertTokenToTokenData(t);
            tokenData = entityManager.merge(tokenData);
            return convertTokenDataToToken(tokenData);
        }
        catch(PersistenceException e) {
            throw new SQLException("Duplikasi data token");
        }     
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            TokenData tokenData = entityManager.find(TokenData.class, id);
            if(tokenData != null) {
                entityManager.remove(tokenData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("token dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id token harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
    }

    @Override
    public Token updateId(String idLama, Token t) throws SQLException {
        Query query = entityManager.createNamedQuery("TokenData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id token");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id token harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id token");
        }
    }

    @Override
    public List<Token> getDaftarData(QueryParamFilters q) {
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TokenData> cq = cb.createQuery(TokenData.class);
            Root<TokenData> root = cq.from(TokenData.class);
            
            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        default -> {
                        }
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
            if(q.getFields_sorter() != null) {
                Iterator<SortOrder> iterSort = q.getFields_sorter().iterator();
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
                        case "tanggal_generate" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("tanggal_generate")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("tanggal_generate")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }
            
            TypedQuery<TokenData> typedQuery;

            if( q.getIs_paging()) { 
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
                            .map(d -> convertTokenDataToToken(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("TokenData.findAll", TokenData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertTokenDataToToken(d))
                            .collect(Collectors.toList());
        }
    }

    @Override
    public Long getJumlahData(List f) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<TokenData> root = cq.from(TokenData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        
        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
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
    
    private Token convertTokenDataToToken(TokenData d) {
        Token token = null;
        
        if(d != null) {
            token = new Token(
                    d.getId(), 
                    d.getAccessToken(), 
                    d.getRefreshToken(), 
                    d.getExpiresIn().longValue(),
                    d.getTanggalGenerate()
            );
        }
        
        return token;
    }
    
    private TokenData convertTokenToTokenData(Token t) {
        TokenData tokenData = null;
        
        if(t != null) {
            tokenData = new TokenData();
            
            if(t.getId() != null) {
                tokenData.setId(t.getId());
            }
            else {
               UUID uuid = UuidCreator.getTimeOrderedEpoch();
               tokenData.setId(uuid.toString()); 
            }
            
            tokenData.setAccessToken(t.getAccess_token());
            tokenData.setRefreshToken(t.getRefresh_token());
            tokenData.setExpiresIn(BigInteger.valueOf(t.getExpires_in()));
            tokenData.setTanggalGenerate(t.getTanggal_generate());
        }
        
        return tokenData;
    }
    
}
