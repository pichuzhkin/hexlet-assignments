package exercise.controller;

import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {


    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment with id "+ id +" not found"));

    }

    @PostMapping(path = "")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping(path = "/{id}")
    public Comment fixComment(@RequestBody Comment comment, @PathVariable Long id) {
        var ourComment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        ourComment.setBody(comment.getBody());
        ourComment.setPostId(comment.getPostId());
        commentRepository.save(ourComment);
        return ourComment;
    }

    @DeleteMapping(path = "/{id}")
    public void purgeComment(@PathVariable Long id) {
        commentRepository.deleteById(id);

    }
}
// END
