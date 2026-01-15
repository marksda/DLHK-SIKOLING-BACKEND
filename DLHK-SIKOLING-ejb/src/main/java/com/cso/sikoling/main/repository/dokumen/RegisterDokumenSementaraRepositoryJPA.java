package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.repository.RegisterDokumenRepository;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
import com.cso.sikoling.main.util.GeneratorID;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class RegisterDokumenSementaraRepositoryJPA implements 
        RegisterDokumenRepository<RegisterDokumenSementara, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public RegisterDokumenSementaraRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public RegisterDokumenSementara save(RegisterDokumenSementara t) throws SQLException {   
        try {
            RegisterDokumenSementaraData registerDokumenSementaraData = 
                    convertRegisterDokumenSementaraToRegisterDokumenSementaraData(t);
            entityManager.persist(registerDokumenSementaraData);
            entityManager.flush();             
            return convertRegisterDokumenSementaraDataToRegisterDokumenSementara(registerDokumenSementaraData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen sementara harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data register dokumen sementara");
        }        
    }

    @Override
    public RegisterDokumenSementara update(RegisterDokumenSementara t) throws SQLException {
        
        try {
            RegisterDokumenSementaraData registerDokumenSementaraData = 
                    convertRegisterDokumenSementaraToRegisterDokumenSementaraData(t);  
            registerDokumenSementaraData = entityManager.merge(registerDokumenSementaraData);
            return convertRegisterDokumenSementaraDataToRegisterDokumenSementara(registerDokumenSementaraData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen sementara harus bilangan 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data register dokumen sementara");
        }
        
    }

    @Override
    public RegisterDokumenSementara updateId(String idLama, RegisterDokumenSementara t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("RegisterDokumenSementaraData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id status dokumen");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen sementara harus bilangan 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id register dokumen sementara");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            RegisterDokumenSementaraData registerDokumenSementaraData = entityManager.find(RegisterDokumenSementaraData.class, id);
            if(registerDokumenSementaraData != null) {
                entityManager.remove(registerDokumenSementaraData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("register dokumen sementara dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen sementara harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<RegisterDokumenSementara> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<RegisterDokumenSementaraData> cq = cb.createQuery(RegisterDokumenSementaraData.class);
            Root<RegisterDokumenSementaraData> root = cq.from(RegisterDokumenSementaraData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama_file" -> daftarPredicate.add(cb.equal(root.get("namaFile"), filter.getValue()));
                        case "id_dokumen" -> daftarPredicate.add(cb.equal(root.get("dokumen").get("id"), filter.getValue()));
                        case "tanggal" -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDate targetDate = LocalDate.parse(filter.getValue(), formatter);
                            LocalDateTime startOfDay = targetDate.atStartOfDay();
                            LocalDateTime startOfNextDay = targetDate.plusDays(1).atStartOfDay();
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggal"), 
                                    startOfDay, 
                                    startOfNextDay
                                )
                            );
                        }
                        case "rentang_tanggal" -> {
                            Jsonb jsonb = JsonbBuilder.create();
                            JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");       
                            LocalDate fromDate = LocalDate.parse(d.getString("from"), formatter);
                            LocalDateTime fromDateTime = fromDate.atStartOfDay();
                            LocalDate toDate = LocalDate.parse(d.getString("to"), formatter);
                            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggal"), 
                                    fromDateTime, 
                                    toDateTime
                                )
                            );
                        }
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
                        case "nama_file" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("namaFile")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("namaFile")));
                            }
                        }                        
                        case "id_dokumen" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("dokumen").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("dokumen").get("id")));
                            }
                        }
                        case "tanggal" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("tanggal")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("tanggal")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<RegisterDokumenSementaraData> typedQuery;	

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
                            .map(d -> convertRegisterDokumenSementaraDataToRegisterDokumenSementara(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("RegisterDokumenSementaraData.findAll", RegisterDokumenSementaraData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertRegisterDokumenSementaraDataToRegisterDokumenSementara(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<RegisterDokumenSementaraData> root = cq.from(RegisterDokumenSementaraData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "nama_file" -> daftarPredicate.add(cb.equal(root.get("namaFile"), filter.getValue()));
                case "id_dokumen" -> daftarPredicate.add(cb.equal(root.get("dokumen").get("id"), filter.getValue()));
                case "tanggal" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDate targetDate = LocalDate.parse(filter.getValue(), formatter);
                    LocalDateTime startOfDay = targetDate.atStartOfDay();
                    LocalDateTime startOfNextDay = targetDate.plusDays(1).atStartOfDay();

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggal"), 
                            startOfDay, 
                            startOfNextDay
                        )
                    );
                }
                case "rentang_tanggal" -> {
                    Jsonb jsonb = JsonbBuilder.create();
                    JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");       
                    LocalDate fromDate = LocalDate.parse(d.getString("from"), formatter);
                    LocalDateTime fromDateTime = fromDate.atStartOfDay();
                    LocalDate toDate = LocalDate.parse(d.getString("to"), formatter);
                    LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggal"), 
                            fromDateTime, 
                            toDateTime
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
    
    private RegisterDokumenSementara convertRegisterDokumenSementaraDataToRegisterDokumenSementara(RegisterDokumenSementaraData d) {
        RegisterDokumenSementara registerDokumenSementara = null;
		
        if(d != null) {
            DokumenData dokumenData = d.getDokumen();
            PerusahaanData perusahaanData = d.getPerusahaan();            
            JsonObject metaFile = d.getMetaFile();
            registerDokumenSementara = new RegisterDokumenSementara(
                    d.getId(), 
                    dokumenData != null ? dokumenData.getId() : null,
                    perusahaanData != null ? perusahaanData.getId() : null,
                    d.getNamaFile(),
                    d.getTanggal(),
                    metaFile
            );
        }

        return registerDokumenSementara;	
    }
    
    private RegisterDokumenSementaraData convertRegisterDokumenSementaraToRegisterDokumenSementaraData(RegisterDokumenSementara t) {
        RegisterDokumenSementaraData registerDokumenSementaraData = null;
		
        if(t != null) {
            registerDokumenSementaraData = new RegisterDokumenSementaraData();  
            registerDokumenSementaraData.setId(t.getId());
            registerDokumenSementaraData.setDokumen(new DokumenData(t.getIdJenisDokumen()));
            registerDokumenSementaraData.setPerusahaan(new PerusahaanData(t.getIdPerusahaan()));
            registerDokumenSementaraData.setNamaFile(t.getNamaFile());
            registerDokumenSementaraData.setTanggal(t.getTanggal()); 
            registerDokumenSementaraData.setMetaFile(t.getMetaFile());
        }

        return registerDokumenSementaraData;
    }
    
    @Override
    public String generateId() {
        int tahun = LocalDate.now().getYear();
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(rd.id) "
                        + "FROM RegisterDokumenData rd "
                        + "WHERE EXTRACT(YEAR FROM rd.tanggalRegistrasi) = :tahun");

        q.setParameter("tahun", tahun);        
        hasil = (String) q.getSingleResult();
        if(hasil != null) {
            hasil = hasil.substring(0, 7);
            Long idBaru = Long.parseLong(hasil)  + 1;
            hasil = GeneratorID.LPad(Long.toString(idBaru), 7, '0');
            return hasil.concat(Integer.toString(tahun));
        }
        else {
            hasil = "0000001";
            return hasil.concat(Integer.toString(tahun));
        }

//        try {
//            hasil = (String) q.getSingleResult();
//            hasil = hasil.substring(0, 7);
//            Long idBaru = Long.parseLong(hasil)  + 1;
//            hasil = GeneratorID.LPad(Long.toString(idBaru), 7, '0');
//            return hasil.concat(Integer.toString(tahun));
//        } catch (NoResultException e) {	
//                hasil = "0000001";			
//                return hasil.concat(Integer.toString(tahun));
//        }		
    }

}
