package com.cso.sikoling.main.repository.storage;

import com.cso.sikoling.abstraction.repository.LocalStorageRepository;
import com.cso.sikoling.main.Infrastructure;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
@Local
@Infrastructure
public class LocalStorageEJB implements LocalStorageRepository {
	
    @Inject
    private LocalStorageImpl localStorageImpl;

    @Override
    public void upload(String fileKey, InputStream inputStream, String subPath) throws IOException {
        localStorageImpl.upload(fileKey, inputStream, subPath);
    }

    @Override
    public File download(String fileNameParam) throws IOException {
        return localStorageImpl.download(fileNameParam);
    }

    @Override
    public void delete(String fileName, String subPath) throws IOException {
        localStorageImpl.delete(fileName, subPath);
    }

    @Override
    public void create(String fileKey, String subPath) throws IOException {
        localStorageImpl.create(fileKey, subPath);		
    }

    @Override
    public void move(String fileNameParamAsal, String fileNameParamTujuan) throws IOException {
        localStorageImpl.move(fileNameParamAsal, fileNameParamTujuan);
    }

    @Override
    public void moveDir(String directoryAsal, String directoryTujuan) throws IOException {
        localStorageImpl.moveDir(directoryAsal, directoryTujuan);
    }

}
