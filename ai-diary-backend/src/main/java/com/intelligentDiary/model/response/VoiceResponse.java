package com.intelligentDiary.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class VoiceResponse {

    String topic;
    List<String> voice=new ArrayList<>();

}
