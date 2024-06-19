package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;


// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPostById(@PathVariable Long id) {
        //return ResponseEntity.of(postRepository.findById(id));
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id "+ id +" not found"));

    }

    @PostMapping(path = "")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping(path = "/{id}")
    public Post fixPost(@RequestBody Post post, @PathVariable Long id) {
        var ourPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        ourPost.setTitle(post.getTitle());
        ourPost.setBody(post.getBody());
        postRepository.save(ourPost);
        return ourPost;
    }

    @DeleteMapping(path = "/{id}")
    public void purgePost(@PathVariable Long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);

    }
}


// END
