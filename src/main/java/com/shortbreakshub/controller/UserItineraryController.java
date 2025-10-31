package com.shortbreakshub.controller;

import com.shortbreakshub.service.CloudinaryService;
import com.shortbreakshub.service.UserItineraryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user-itineraries")
public class UserItineraryController {

    private final UserItineraryService userItineraryService;
    private final CloudinaryService cloudinaryService;

    public UserItineraryController(UserItineraryService userItineraryService, CloudinaryService cloudinaryService) {
        this.userItineraryService = userItineraryService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(path = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> itineraryPhotoUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("testing");
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String itineraryPhotoUrl = cloudinaryService.uploadImage(file);
        return ResponseEntity.ok(itineraryPhotoUrl);
    }
}
