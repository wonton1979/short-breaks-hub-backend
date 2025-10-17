package com.shortbreakshub.service;


import com.shortbreakshub.dto.CommentRes;
import com.shortbreakshub.model.Comment;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.model.User;
import com.shortbreakshub.respository.CommentRepository;
import com.shortbreakshub.respository.ItineraryRepository;
import com.shortbreakshub.respository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final ItineraryRepository itineraryRepo;

    public CommentService(CommentRepository c, UserRepository u, ItineraryRepository i) {
        this.commentRepo = c;
        this.userRepo = u;
        this.itineraryRepo = i;
    }

    @Transactional
    public void addOrUpdate(Long userId, Long itineraryId, String body, Integer rating) {
        Optional<Comment> existing = commentRepo.findByUserIdAndItineraryId(userId, itineraryId);

        Comment c = existing.orElseGet(Comment::new);
        c.setUser(userRepo.getReferenceById(userId));
        c.setItinerary(itineraryRepo.getReferenceById(itineraryId));
        c.setBody(body);
        c.setRating(rating);
        commentRepo.save(c);
    }

    @Transactional(readOnly = true)
    public Page<CommentRes> getCommentList(Long itineraryId, Pageable pageable) {
        return commentRepo.findByItineraryIdOrderByCreatedAtDesc(itineraryId, pageable)
                .map(CommentRes::toRes);
    }

    @Transactional
    public void deleteComment(Long itineraryId, Long userId) {
        long commentId;
        Optional<Comment> existing = commentRepo.findByUserIdAndItineraryId(userId, itineraryId);
        if (existing.isPresent()) {
            commentId = existing.get().getId();
            commentRepo.deleteById(commentId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public Map<String,Object> hasUserCommented(Long itineraryId, Long userId) {
        var existing = commentRepo.findByUserIdAndItineraryId(userId,itineraryId);
        if (existing.isPresent()) {
            return Map.of("hasUserCommented",true,"comment",existing.get().getBody(),
                    "rating",(existing.get().getRating() == null) ? "No Rating" : existing.get().getRating());
        }
        return Map.of("hasUserCommented",false);
    }
}

