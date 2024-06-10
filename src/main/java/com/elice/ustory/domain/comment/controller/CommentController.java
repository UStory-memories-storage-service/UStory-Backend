package com.elice.ustory.domain.comment.controller;

import com.elice.ustory.domain.comment.dto.*;
import com.elice.ustory.domain.comment.entity.Comment;
import com.elice.ustory.domain.comment.service.CommentService;
import com.elice.ustory.global.jwt.JwtAuthorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "comment", description = "Comment API")
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get Comment API", description = "댓글 ID를 통해 불러옴.")
    @GetMapping("/paper/{paperId}/comment/{commentId}")  // 시험용이라 uri 더러운건 무시하셔도 됩니다.
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long paperId,
                                                      @PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getComment(paperId, commentId);
        CommentResponse commentResponse = new CommentResponse(comment.orElseGet(Comment::new));
        return ResponseEntity.ok().body(commentResponse);
    }

    @Operation(summary = "Get Comments API", description = "모든 댓글들을 불러옴")
    @GetMapping("/paper/{paperId}")   // 시험용이라 uri 더러운건 무시하셔도 됩니다.
    public ResponseEntity<List<CommentListResponse>> getComments(@PathVariable Long paperId, @JwtAuthorization Long userId) {
        List<Comment> comments = commentService.getComments(paperId, userId);

        List<CommentListResponse> response = comments.stream()
                .map(comment -> new CommentListResponse(comment, userId))
                .toList();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Post Comment API", description = "댓글을 생성함")
    @PostMapping
    public ResponseEntity<AddCommentResponse> createComment(@RequestParam Long paperId,
                                                            @JwtAuthorization Long userId,
                                                            @RequestBody AddCommentRequest addCommentRequest) {
        Comment comment = commentService.addComment(addCommentRequest, paperId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AddCommentResponse(comment.getId(), paperId, userId, comment.getCreatedAt()));
    }

    @Operation(summary = "Update Comment API", description = "댓글을 수정함")
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId,
                                                               @RequestBody UpdateCommentRequest updateCommentRequest) {
        Comment updatedComment = commentService.updateComment(commentId, updateCommentRequest);
        return ResponseEntity.ok().body(new UpdateCommentResponse(commentId, updatedComment.getContent()));
    }

    @Operation(summary = "Delete Comment API", description = "댓글을 삭제함")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
