package com.cso.sikolingrestful.provider;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.Service;
import io.jsonwebtoken.Claims;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikolingrestful.annotation.WopiRequiredAuthorization;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Provider
@Stateless
@LocalBean
@WopiRequiredAuthorization
public class WopiAuthorization implements ContainerRequestFilter {
    
    @Inject
    private TokenService<Token> tokenService;
    
    @Inject
    private Service<Otorisasi> otorisasiService;
    
    @Inject
    private Service<RegisterDokumen> registerDokumenService;
    
    @Inject
    private Service<Pegawai> pegawaiService;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        UriInfo uriInfo = crc.getUriInfo();
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        String accessToken = Optional.ofNullable(queryParameters.getFirst("access_token"))
                                        .orElseThrow(() -> new NotAuthorizedException("Wopi authorization not found"));
        Claims claims = Optional.ofNullable(tokenService.validateAccessToken(accessToken))
                                .orElseThrow(() -> new NotAuthorizedException("Wopi authorization tidak valid"));
        MultivaluedMap<String, String> pathParams = uriInfo.getPathParameters();
        String fileId = Optional.ofNullable(pathParams.getFirst("file_id"))
                            .orElseThrow(() -> new NotAuthorizedException("Wopi file tidak ditemukan"));
        try {            
            List<Filter> fields_filter = new ArrayList<>();
            String idUser = claims.getAudience().stream().findFirst().orElseThrow();
            Filter filter = new Filter("id_user", idUser);
            fields_filter.add(filter);
            QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
            Otorisasi otorisasi = otorisasiService.getDaftarData(qFilter).getFirst();
            
            fields_filter.remove(filter);
            filter = new Filter("id", fileId);
            fields_filter.add(filter);
            RegisterDokumen registerDokumen = registerDokumenService.getDaftarData(qFilter).getFirst();
            
            try {
                checkPermissions(otorisasi, registerDokumen, crc);
//                crc.setProperty("otorisasi", otorisasi);
                crc.setProperty("registerDokumen", registerDokumen);
            } catch (Exception e) {
                throw new NotAuthorizedException(e.toString());
            }            
        } catch (NoSuchElementException e) {
            throw new NotAuthorizedException(e.toString());
        }
        
    }
    
    private void checkPermissions(Otorisasi otorisasi, RegisterDokumen registerDokumen, ContainerRequestContext crc) throws Exception {        
        switch (otorisasi.getHak_akses().getId()) {
            case "01" -> {
            }
            case "09" -> {
                try {  
                    List<Filter> fields_filter = new ArrayList<>();
                    Filter filter = new Filter("id_person", otorisasi.getPerson().getId());
                    fields_filter.add(filter);
                    filter = new Filter("id_perusahaan", registerDokumen.getPerusahaan().getId());
                    fields_filter.add(filter);
                    QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
                    Pegawai pegawai = pegawaiService.getDaftarData(qFilter).getFirst();
                    crc.setProperty("pegawai", pegawai);
                } catch (NoSuchElementException e) {
                    throw new NotAuthorizedException(e.toString());
                }
            }
            default -> throw new Exception("unauthorized Role");
        }
    }

}
