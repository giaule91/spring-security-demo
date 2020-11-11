package com.tina.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class UploadService {

    @Value("${upload.location}")
    private String fileLocation;

    public String uploadFile(MultipartFile file) throws IOException {

        String fileExtension = getFileExtension(file);
        String filename = generateFileName(file);
        File targetFile = getTargetFile(fileExtension, filename);
        file.transferTo(targetFile);
        return filename;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File getTargetFile(String fileExtn, String fileName) {
        File targetFile = new File(this.fileLocation + fileName + fileExtn);
        return targetFile;
    }

    private String getFileExtension(MultipartFile inFile) {
        String fileExtention = inFile.getOriginalFilename().substring(inFile.getOriginalFilename().lastIndexOf('.'));
        return fileExtention;
    }

    /**
     * Check MultipartFile is a image file.
     * @return boolean
     */
    public boolean isImageFile(MultipartFile file){
        boolean isImage = false;
        if(file != null && !file.isEmpty()){
            String mimetype= file.getContentType();
            String type = mimetype.split("/")[0];
            if(type.equals("image")) isImage = true;
        }
        return isImage;
    }
}
