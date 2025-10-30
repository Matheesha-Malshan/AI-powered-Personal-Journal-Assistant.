package com.intelligentDiary.service.file.fileService;

import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.response.ImageResponse;
import com.intelligentDiary.model.response.VoiceResponse;
import com.intelligentDiary.service.file.VoiceFileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class VoiceFileServiceImpl implements VoiceFileService {

    @Override
    public String saveVoice(VoiceEntry voiceEntry) {
        String path = "./././././saveVoice/"+voiceEntry.getUserId();

        File directory=new File(path);

        if (directory.exists()){

            Path recordPath= Paths.get(path+"/"+voiceEntry.getId());
            try {


                Files.write(recordPath,voiceEntry.getAudioRecord().getBytes());

                return recordPath.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Path paths=Files.createDirectory(Path.of(path));
                Path recordPath=Paths.get(paths+"/"+voiceEntry.getId());

                Files.write(recordPath,voiceEntry.getAudioRecord().getBytes());

                return recordPath.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public VoiceResponse searchAudio(List<String> pathList, VoiceResponse response){
        for(String paths:pathList){
            Path path=Paths.get(paths);

            try {
                byte[] content=Files.readAllBytes(path);
                response.getVoice().add(Base64.getEncoder().encodeToString(content));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return response;
    }
}
