package com.bloggy.blog_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/all")
    public List<Blog> get_all_blogs() {
        return blogRepository.findAll();
    }

    @PostMapping
    public Blog create_blog(Blog blog) {
        blogRepository.save(blog);
        return blog;
    }

    @GetMapping("/{id}")
    public Blog get_blog(@PathVariable("id") String id) {
        Optional<Blog> blog_optional = blogRepository.findById(id);
        if (blog_optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No blog for id: %s", id));
        }
        return blog_optional.get();
    }

    @DeleteMapping("/{id}")
    public void delete_blog(@PathVariable("id") String id) {
        blogRepository.deleteById(id);
    }

    @PostMapping("/{blog_id}/post")
    public Post create_post(@PathVariable("blog_id") String blog_id, Post post) {
        Optional<Blog> blog_optional = blogRepository.findById(blog_id);

        if (blog_optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No blog for id: %s", blog_id));
        }
        Blog blog = blog_optional.get();
        post.setBlog(blog);
        postRepository.save(post);
        return post;
    }

    @GetMapping("/{blog_id}/post/{post_id}")
    public Post get_post(@PathVariable("blog_id") String blog_id, @PathVariable("post_id") String post_id) {
        Optional<Post> post_optional = postRepository.findById(post_id);
        if (post_optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No post for id: %s", post_id));
        }
        return post_optional.get();
    }
}
