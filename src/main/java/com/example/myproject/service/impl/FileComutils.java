package com.example.myproject.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class FileComutils {

    /**
     * magen
     * 上传文件
     *
     * @param file     接受页面传过来的流储存进去
     *                 path  文件储存地址
     *                 uuid  不重复id
     *                 fileName  UUID+文件名+文件后缀（.jpg）
     *                 dir  将文件储存进去，地址是 path，文件名称是 fileName
     *                 transferTo  MultipartFile自带的解析方法
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("upload");
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + file.getOriginalFilename();
        File dir = new File(path, fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(dir);
        return fileName;
    }
}