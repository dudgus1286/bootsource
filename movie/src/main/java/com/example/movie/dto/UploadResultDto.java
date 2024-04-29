package com.example.movie.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

// Serializable : 객체 상태로 입출력
@Data
@AllArgsConstructor
public class UploadResultDto implements Serializable {
    // 폴더, uuid, 실제 파일명 정보
    private String folderPath;

    private String uuid;

    private String fileName;
    //

    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fullPath;
    }

    public String getThumbImageURL() {
        String thumbFullPath = "";

        try {
            thumbFullPath = URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return thumbFullPath;
    }
}
