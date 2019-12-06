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
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("/upload")
@Controller
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("/multi")
    @ResponseBody
    public String multiUpload(HttpServletRequest request, String type) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String filePath = ConfigUtil.getConfig().getString("javaDirPath");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                LOGGER.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                LOGGER.error(e.toString(), e);
                return "上传第" + (i++) + "个文件失败";
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

    private void deleteAllFiles(String root) {
        File files[] = new File(root).listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹  
                    deleteAllFiles(f.getAbsolutePath());
                } else {
                    if (f.exists()) { // 判断是否存在  
                        deleteAllFiles(f.getAbsolutePath());
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

}




