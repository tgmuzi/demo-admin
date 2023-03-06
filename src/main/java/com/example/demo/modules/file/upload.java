package com.example.demo.modules.file;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("${adminPath}/uploadFile")
public class upload {

    /**
     * 1.文件保存在服务器，url地址保存在数据库
     * 上传成功之后返回成功保存的url地址
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, String title, MultipartFile file) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("title", title);
        resultMap.put("fileName", file.getName()); // 文件名
        resultMap.put("originalFilename", file.getOriginalFilename()); // 原始名称
        resultMap.put("content-type", file.getContentType()); // 文件类型
        resultMap.put("fileSize", file.getSize() / 1024 + "K"); // 文件大小
        try {
            // 保存文件
            String etc = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String serverPath = request.getScheme() + "://" + request.getServerName()
                    + ":9999" + request.getContextPath() + "/";
            String fileName = UUID.randomUUID() + "." + etc;
            resultMap.put("success", true); // 文件地址(服务器访问地址)
            // 文件保存再真实路径下
            File saveFile = new File("G://file/upload/" + fileName);
            if (!saveFile.getParentFile().exists()) { // 目录不存在，创建目录
                saveFile.mkdirs();
            }
            file.transferTo(saveFile); // 保存上传文件
            resultMap.put("filePath", serverPath + fileName); // 文件地址(服务器访问地址)
        } catch (IOException e) {
            System.err.println("error-path: /upload/file, message: " + e.getMessage());
            resultMap.put("success", false); // 文件地址(服务器访问地址)
        }
        return resultMap;

    }
}