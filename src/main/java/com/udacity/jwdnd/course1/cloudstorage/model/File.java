package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.type.BlobByteObjectArrayTypeHandler;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private BlobByteObjectArrayTypeHandler fileData;

}
