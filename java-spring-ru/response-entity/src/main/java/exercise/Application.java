package exercise;

import java.net.URI;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> ret = posts;
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size())).body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getCertainPost(@PathVariable String id) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
            return ResponseEntity.of(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post)
    {
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updSmth(@PathVariable String id, @RequestBody Post incoming) {
        var post = posts.stream().filter(p -> p.getId().equals(incoming.getId())).findFirst();
        if (post.isPresent())
        {
            var p = post.get();
            p.setId(incoming.getId());
            p.setBody(incoming.getBody());
            p.setTitle(incoming.getTitle());
            return ResponseEntity.ok().body(p);
        } else return ResponseEntity.status(204).build();



    }

    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
