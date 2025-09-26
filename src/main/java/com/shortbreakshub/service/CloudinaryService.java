package dev.group2.traveldiary.travel_diary_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
       String savedUrl = cloudinary
               .uploader()
               .upload(file.getBytes(), ObjectUtils.emptyMap())
               .get("secure_url")
               .toString();
       return savedUrl;
    }

    public void deleteImage(String url) throws IOException {
        String filename = url.substring(url.lastIndexOf('/') + 1,url.lastIndexOf("."));
        cloudinary.uploader().destroy(filename, ObjectUtils.emptyMap());
    }
}
