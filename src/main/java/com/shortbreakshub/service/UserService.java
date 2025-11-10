package com.shortbreakshub.service;
import com.shortbreakshub.dto.MeResponse;
import com.shortbreakshub.dto.UpdateAvatarReq;
import com.shortbreakshub.dto.UpdateMeReq;
import com.shortbreakshub.model.User;
import com.shortbreakshub.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
    private final HttpServletRequest request;
    private final CloudinaryService cloudinaryService;
    private final EmailService emailService;
    private final EmailVerificationService token;

    @Value("${app.public-base-url}")
    private String publicBaseUrl;

    public UserService(UserRepository repo, HttpServletRequest request, CloudinaryService cloudinaryService,
                       EmailService emailService, EmailVerificationService token) {
        this.repo = repo;
        this.request = request;
        this.cloudinaryService = cloudinaryService;
        this.emailService = emailService;
        this.token = token;
    }

    public User register(String email, String password, String displayName, String location, String bio, Integer adults, Integer children) {
        if (repo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
        }
        User u = new User(email, enc.encode(password), displayName,location, bio, adults, children);
        repo.save(u);
        String confirmLink = publicBaseUrl + "/verify-email?token=" + token.createToken(u).getToken();
        emailService.sendConfirmationEmail(u.getEmail(),u.getDisplayName(),confirmLink);
        return u;
    }

    @Transactional
    public MeResponse updateOwnProfileById(Long userId, UpdateMeReq req) {
        var user = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (req.location() != null)  user.setLocation(req.location());
        if (req.bio() != null)       user.setBio(req.bio());
        if (req.adults() != null)    user.setAdults(req.adults());
        if (req.children() != null)  user.setChildren(req.children());
        if (req.displayName() != null)  user.setDisplayName(req.displayName());
        repo.save(user);
        return MeResponse.from(user);
    }

    @Transactional
    public MeResponse updateOwnAvatarById(Long userId, UpdateAvatarReq req) throws IOException {
        var user = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if ((!req.avatarUrl().isBlank()) && cloudinaryService.isCloudinaryFileExists(req.avatarUrl()))  {
            if (!(user.getAvatarUrl() == null)){
                cloudinaryService.deleteImage(user.getAvatarUrl());
            }
            user.setAvatarUrl(req.avatarUrl());
        }
        repo.save(user);
        return MeResponse.from(user);
    }
}
