package com.nhom29.Service.Impl;

import com.cloudinary.Cloudinary;
import com.nhom29.Service.Inter.CloudinaryInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryImpl implements CloudinaryInter {
    private final Cloudinary cloudinary;


    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url").toString();
    }
}