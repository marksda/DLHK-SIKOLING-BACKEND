package com.cso.sikoling.abstraction.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface LocalStorageService {
    void upload(String fileKey, InputStream inputStream, String subPath) throws IOException;
    void create(String fileKey, String subPath) throws IOException;
    File download(String fileName, String subPath) throws IOException;
    void delete(String fileName, String subPath) throws IOException;
    void move(String fileNameParamAsal, String fileNameParamTujuan) throws IOException;
    void moveDir(String directoryAsal, String directoryTujuan) throws IOException;
}
