package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
    }

    public List<File> getUserFiles(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getUserFiles(userId);
    }

    public boolean isFileNameAvailable(String filename) {
        return fileMapper.getFileWithFileName(filename) == null;
    }

    public int uploadFile(MultipartFile file, String username) throws IOException, SQLException {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper
                .insertFile(new File(null, file.getOriginalFilename(),
                file.getContentType(), file.getSize()+"", userId, new SerialBlob(file.getBytes())));
    }

}
