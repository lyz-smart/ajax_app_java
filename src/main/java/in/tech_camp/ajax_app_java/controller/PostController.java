package in.tech_camp.ajax_app_java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.http.ResponseEntity;
import in.tech_camp.ajax_app_java.entity.PostEntity;
import in.tech_camp.ajax_app_java.form.PostForm;
import in.tech_camp.ajax_app_java.repository.PostRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("/")
  public String showList(Model model) {
    var postList = postRepository.findAll();
    model.addAttribute("postList", postList);
    model.addAttribute("postForm", new PostForm());
    return "posts/index";
  }

  // @GetMapping("/postForm")
  // public String showPostForm(@ModelAttribute("postForm") PostForm form){
  //     return "posts/postForm";
  // }

  @PostMapping("/posts")
  // ResponseEntity: spring boot 中的 http response 类
  public ResponseEntity<PostEntity> savePost(@ModelAttribute("postForm") PostForm form){
    PostEntity post = new PostEntity();
    post.setContent(form.getContent());
    postRepository.insert(post);
    // 因为插入数据的时候，post 的 id 和创建时间都是数据库给的，response 中无法获取到这条推文在数据库中的信息
    // 所以插入之后，提取 刚刚插入的推文在数据库中的信息，放进 response 之中，比较科学
    PostEntity resultPost = postRepository.findById(post.getId());
    return ResponseEntity.ok(resultPost);
    // ok: 请求正常的时候的调用方法
  }
  
}
