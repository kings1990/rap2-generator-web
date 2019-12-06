package io.github.kings1990.web.util;

import com.alibaba.fastjson.JSONObject;
import io.github.kings1990.web.controller.UploadController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ConfigUtil {
    public static JSONObject getConfig(){
        InputStream inputStream = UploadController.class.getResourceAsStream("/config.json");
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        return JSONObject.parseObject(jsonString, JSONObject.class); 
    }
}


