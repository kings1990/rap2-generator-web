package io.github.kings1990.web.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class ParseResponse {
    private JSONArray requestArray;
    private JSONArray responseArray;
}


