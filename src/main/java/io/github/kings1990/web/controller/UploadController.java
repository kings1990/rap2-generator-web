package io.github.kings1990.web.controller;

import io.github.kings1990.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("/upload")
@Controller
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static Pattern PATTERN_PACKAGE = Pattern.compile("^package(.*);");
    
    @PostMapping("/multi")
    @ResponseBody
    public String multiUpload(HttpServletRequest request) throws Exception{
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String filePath = ConfigUtil.getConfig().getString("javaDirPath");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i+1) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                PrintWriter printWriter = new PrintWriter(filePath + fileName);
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    //去除包名
                    if(!PATTERN_PACKAGE.matcher(s).matches()){
                        printWriter.println(s);
                    }
                }
                bufferedReader.close();
                printWriter.close();
                LOGGER.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                throw new Exception("上传失败");
            }
        }
        return "上传成功";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String type, String name) throws IOException {
        String filePath = ConfigUtil.getConfig().getString("javaDirPath");
        File f = new File(filePath + name);
        f.delete();
        return "1";
    }

}




