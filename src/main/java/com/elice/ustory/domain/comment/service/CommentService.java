package com.elice.ustory.domain.comment.service;

import com.elice.ustory.domain.comment.dto.CommentDto;
import com.elice.ustory.domain.comment.entity.Comment;
import com.elice.ustory.domain.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getComment(Long id) {
        return commentRepository.findById(id);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(optionalComment.isPresent()){
            return optionalComment.get().update(commentDto.getContent());
        }else{
            throw new RuntimeException("해당 Id에 대한 댓글을 찾을 수 없습니다.");
        }
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
