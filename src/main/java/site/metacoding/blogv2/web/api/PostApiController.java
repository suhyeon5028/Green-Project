package site.metacoding.blogv2.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.post.PostRepository;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.domain.user.UserRepository;
import site.metacoding.blogv2.web.api.dto.ResponseDto;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/api/post/{id}")
    public ResponseDto<?> detail(@PathVariable Integer id) {

        Optional<User> postOp = userRepository.findById(id);

        if (postOp.isPresent()) {
            return new ResponseDto<>(1, "성공", postOp.get().getPost());
        } else {
            throw new RuntimeException("해당 게시글을 찾을 수 없습니다");
        }
    }

    @GetMapping("/api/search")
    public ResponseDto<?> search(@RequestParam(defaultValue = "") String keyword) {
        List<Post> boards = postRepository.mSearch(keyword);
        return new ResponseDto<>(1, "성공", boards); // MessageConverter 발동 - 자바오브젝트를 JSON으로 변환
    }
}