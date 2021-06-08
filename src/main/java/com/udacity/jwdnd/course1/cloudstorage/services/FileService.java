package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> getUserFiles(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getUserFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileWithId(fileId);
    }

    public boolean isFileNameAvailable(String filename) {
        return fileMapper.getFileWithFileName(filename) == null;
    }

    public int uploadFile(MultipartFile file, String username) throws IOException {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper
                .insertFile(new File(null, file.getOriginalFilename(),
                file.getContentType(), file.getSize()+"", userId, file.getBytes()));
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

}
