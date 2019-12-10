package io.github.kings1990.web.job;

import io.github.kings1990.web.util.ConfigUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Jobs {

    @Scheduled(cron="0 0/2 * * * ?")
    public void deleteJob(){
        String filePath = ConfigUtil.getConfig().getString("javaDirPath");
        deleteAllFiles(filePath);
    }

    private void deleteAllFiles(String root) {
        File files[] = new File(root).listFiles();
        if (files != null) {
            for (File f : files) {
                // 判断是否为文件夹
                if (f.isDirectory()) {
                    deleteAllFiles(f.getAbsolutePath());
                } else {
                    // 判断是否存在
                    if (f.exists()) {
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
