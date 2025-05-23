package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    FileMetadata uploadFile(MultipartFile file);

    InputStream getFileByFileName(String fileName);
}
