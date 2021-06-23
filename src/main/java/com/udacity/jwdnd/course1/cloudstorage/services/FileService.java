package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Implements the file service create, read or delete files
 */
@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    /**
     * Gathers a list of all files of a given user
     * @param username The username of the user to get the files for
     * @return a list of all files for the given user
     */
    public List<File> getUserFiles(String username) {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getUserFiles(userId);
    }

    /**
     * Gets file by its id
     * @param fileId the id of the file
     * @return the requested file
     */
    public File getFile(Integer fileId) {
        return fileMapper.getFileWithId(fileId);
    }

    /**
     * Checks if the filename is available to be assigned to a new file
     * @param filename the filename to be checked
     * @return a boolean indicating if the filename is available
     */
    public boolean isFileNameAvailable(String filename) {
        return fileMapper.getFileWithFileName(filename) == null;
    }

    /**
     * Uploads a file for the given user
     * @param file The file to upload
     * @param username The username for the user to upload the file for
     * @return id of the new file
     */
    public int uploadFile(MultipartFile file, String username) throws IOException {
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper
                .insertFile(new File(null, file.getOriginalFilename(),
                file.getContentType(), file.getSize()+"", userId, file.getBytes()));
    }

    /**
     * Deletes a given file by id
     * @param fileId the id of the file to delete
     * @return the number of deleted rows
     */
    public int deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

}
