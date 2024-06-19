package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import exercise.model.Post;
import exercise.model.Comment;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private PostDTO mapPostDTO(Post post, List<Comment> comments) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setBody(post.getBody());
        dto.setTitle(post.getTitle());
        dto.setComments(comments.stream().map(this::mapCommentDTO).toList());

        return dto;
    }

    private CommentDTO mapCommentDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }


    @GetMapping(path =  "/posts")
    public List<PostDTO> getAllPosts() {
        var allPosts = postRepository.findAll();
        return allPosts
                .stream()
                .map( post ->
                        mapPostDTO(post,commentRepository.findByPostId(post.getId()))
                ).toList();
    }

    @GetMapping(path =  "/posts/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id "+id+" not found"));
        return mapPostDTO(post,commentRepository.findByPostId(post.getId()));
    }
}
// END
