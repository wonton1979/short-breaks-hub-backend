package com.shortbreakshub.controller;

import com.shortbreakshub.dto.MeResponse;
import com.shortbreakshub.dto.UpdateAvatarReq;
import com.shortbreakshub.dto.UpdateMeReq;
import com.shortbreakshub.model.User;
import com.shortbreakshub.service.CloudinaryService;
import com.shortbreakshub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public UserController(UserService userService, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    public record RegisterReq(
            @Email @NotBlank String email,
            @NotBlank String password,
            @NotBlank String displayName,
            String location,
            String bio,
            Integer adults,
            Integer children,
            String avatarUrl
    ) {}

    public record RegisterRes(Long id, String email, String displayName) {}

    @PostMapping("/register")
    public RegisterRes register(@Valid @RequestBody RegisterReq req) {
        User u = userService.register(req.email(), req.password(), req.displayName(), req.location(), req.bio(), req.adults(), req.children());
        return new RegisterRes(u.getId(), u.getEmail(), u.getDisplayName());
    }

    @PostMapping(path = "/me/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> photoUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String avatarUrl = cloudinaryService.uploadImage(file);
        return ResponseEntity.ok(avatarUrl);
    }

    @PutMapping("/me")
    public ResponseEntity<MeResponse> updateMe(
            @Valid @RequestBody UpdateMeReq req,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.updateOwnProfileById(userId, req));
    }

    @PutMapping("/me/photo")
    public ResponseEntity<MeResponse> updateMePhoto(
            @Valid @RequestBody UpdateAvatarReq req,
            HttpServletRequest request
    ) throws IOException {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.updateOwnAvatarById(userId, req));
    }

}

