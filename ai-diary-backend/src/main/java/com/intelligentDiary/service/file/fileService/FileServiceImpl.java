package com.intelligentDiary.service.file.fileService;

import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.model.response.ImageResponse;
import com.intelligentDiary.service.file.FileService;
import lombok.RequiredArgsConstructor;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public String saveImage(ImageEntry imageEntry){


        String path = "./././././saveFile/"+imageEntry.getUserId();

        File directory=new File(path);

        if (directory.exists()){
            Path imagePath=Paths.get(path+"/"+imageEntry.getId());
            try {

                Files.write(imagePath,imageEntry.getImage().getBytes());

                return imagePath.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Path paths=Files.createDirectory(Path.of(path));
                Path imagePath=Paths.get(paths+"/"+imageEntry.getId());

                Files.write(imagePath,imageEntry.getImage().getBytes());

                return imagePath.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public ImageResponse searchImage(List<String> pathList, ImageResponse response){

        for(String paths:pathList){
            Path path=Paths.get(paths);

            try {
                byte[] content=Files.readAllBytes(path);
                response.getImage().add(Base64.getEncoder().encodeToString(content));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return response;

    }



}
