package com.korea.basic1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    PostRepository postRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @RequestMapping("/")
    public String main(Model model) {
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList", postList);
        model.addAttribute("targetPost",postList.get(0));
        return "main";
    }

    @PostMapping("/write")
    public String write() {
        Post post = new Post();
        post.setTitle("newTitle");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model,@PathVariable Long id){
        Post post = postRepository.findById(id).get();
        model.addAttribute("targetPost",post);
        model.addAttribute("postList",postRepository.findAll());
        return "main";
    }
    @PostMapping("/update")
    public String update(Long id,String title,String content){
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return "redirect:/detail/" +id;
    }
}

