package com.didkovskiy.thingstodo.repositories;

import com.didkovskiy.thingstodo.domains.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileRepository {

    @Value("${files.upload.path}")
    private String uploadPath;

    public void saveNewFile(MultipartFile file, Message message){
        if(file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            message.setFilename(resultFileName);
        }
    }
}
