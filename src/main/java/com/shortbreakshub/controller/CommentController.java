package com.shortbreakshub.controller;

import com.shortbreakshub.dto.CommentRes;
import com.shortbreakshub.dto.CreateCommentReq;
import com.shortbreakshub.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/itineraries/{itineraryId}/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> addNewComment(@PathVariable Long itineraryId,
                                                            @Valid @RequestBody CreateCommentReq req,
                                                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        commentService.addOrUpdate(userId, itineraryId, req.body(), req.rating());
        return ResponseEntity.ok(Map.of("response", "Comment has been added successfully!"));
    }

    @GetMapping
    public ResponseEntity<Page<CommentRes>> getCommentList(@PathVariable Long itineraryId,
                                 @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
                                 Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentList(itineraryId, pageable));
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long itineraryId,HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        commentService.deleteComment(itineraryId, userId);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> checkComment(@PathVariable Long itineraryId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("authUserId");
        if (userId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(commentService.hasUserCommented(itineraryId, userId));
    }
}

