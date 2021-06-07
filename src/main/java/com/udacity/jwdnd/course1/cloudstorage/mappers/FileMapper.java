package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFileWithFileName(String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES (fileName, contentType, fileSize, userId, fileData)")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);
}
