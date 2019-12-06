package io.github.kings1990.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.kings1990.rap2.generator.config.ParseWebConfig;
import io.github.kings1990.rap2.generator.config.Summary;
import io.github.kings1990.rap2.generator.core.Rap2WebGenerator;
import io.github.kings1990.web.model.ParseResponse;
import io.github.kings1990.web.util.ConfigUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequestMapping("/data")
@Controller
public class DataController {
    private static Pattern PATTERN_INTERFACE_URL = Pattern.compile("(.*)itf=(\\d+)(.*)");
    
    @GetMapping(value = "/parse")
    @ResponseBody
    public ParseResponse parse(ParseWebConfig parseWebConfig) throws Exception{
        ParseResponse result= new ParseResponse();

        JSONArray requestArray = new JSONArray();
        JSONArray responseArray = new JSONArray();
        
        InputStream inputStream = DataController.class.getResourceAsStream("/config.json");
        String jsonString = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JSONObject configJsonObject = JSONObject.parseObject(jsonString, JSONObject.class);
        String responseConfigJson = configJsonObject.getString("responseTemplate");
        parseWebConfig.setResponseConfigJson(responseConfigJson);
        parseWebConfig.setJavaDirPath(ConfigUtil.getConfig().getString("javaDirPath"));
        Rap2WebGenerator rap2Generator = new Rap2WebGenerator();
        rap2Generator.setParseWebConfig(parseWebConfig);
        JSONArray parse = rap2Generator.parse();
        for (Object o : parse) {
            JSONObject oo = (JSONObject) o;
            if("-1".equals(oo.getString("parentId"))){
                oo.put("parentId",0);
            }
            if("request".equals(oo.getString("scope"))){
                requestArray.add(oo);
            } else {
                responseArray.add(oo);
            }
        }
        result.setRequestArray(requestArray);
        result.setResponseArray(responseArray);
        return result;
    }
    
    @PostMapping(value = "/generator",produces= "text/plain;charset=utf-8")
    @ResponseBody
    public String generator(DataModel dataModel) throws Exception{
        String interfaceUrl = dataModel.getInterfaceUrl();
        Matcher matcher = PATTERN_INTERFACE_URL.matcher(interfaceUrl);
        if(matcher.matches()){
            JSONArray all = new JSONArray();
            Integer interfaceId = Integer.parseInt(matcher.group(2));

            ParseWebConfig parseWebConfig = new ParseWebConfig();
            BeanUtils.copyProperties(dataModel,parseWebConfig);
            String delosUrl = ConfigUtil.getConfig().getString("delosUrl");
            parseWebConfig.setDelosUrl(delosUrl);
            parseWebConfig.setInterfaceId(interfaceId);
            
            List<Rap2Data> requestData = dataModel.getRequestData();
            if(requestData != null && !requestData.isEmpty()){
                requestData.forEach(q->{
                    q.setInterfaceId(interfaceId);
                    q.setMemory("false");
                    q.setPos(Summary.RequestParamsType.QUERY_PARAMS.equals(parseWebConfig.getRequestParamsType()) ? 2 : 3);
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(q);
                    all.add(jsonObject);
                });
            }
            List<Rap2Data> responseData = dataModel.getResponseData();
            if(responseData != null && !responseData.isEmpty()){
                responseData.forEach(q->{
                    q.setInterfaceId(interfaceId);
                    q.setMemory("false");
                    q.setPos(3);
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(q);
                    all.add(jsonObject);
                });
            }
            Rap2WebGenerator rap2Generator = new Rap2WebGenerator();
            rap2Generator.setParseWebConfig(parseWebConfig);
            String result = rap2Generator.generator(all);
            return result;
        }
        return "链接问题";
    }

    
}




