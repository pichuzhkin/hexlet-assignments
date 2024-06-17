package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;


// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<Post>> getPostsOfUser(@PathVariable int id) {
        var usersPosts = posts.stream().filter(p -> p.getUserId() == id).toList();
        return ResponseEntity.ok().body(usersPosts);
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Post> makePost(@PathVariable int userId, @RequestBody Post post) {
        post.setUserId(userId);
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }
}
// END
