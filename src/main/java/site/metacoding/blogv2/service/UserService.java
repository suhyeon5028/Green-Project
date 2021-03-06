package site.metacoding.blogv2.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.domain.user.UserRepository;
import site.metacoding.blogv2.web.Dto.UpdateDto;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(User user) {

        userRepository.save(user);
    }

    public User 로그인(User user) {
        return userRepository.mLogin(user.getUsername(), user.getPassword());
    }

    public User 회원정보(Integer id) {
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            return userOp.get();
        } else {
            throw new RuntimeException("아이디를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void 회원수정(Integer id, UpdateDto updateDto) {
        // UPDATE user SET password = ?, email = ?, addr = ? WHERE id = ?
        Optional<User> userOp = userRepository.findById(id); // 영속화 (디비 row를 영속성 컨텍스에 옮김)

        if (userOp.isPresent()) {
            // 영속화된 오브젝트 수정
            User userEntity = userOp.get();
            userEntity.setBlogname(updateDto.getBlogname());
            userEntity.setPassword(updateDto.getPassword());
            userEntity.setEmail(updateDto.getEmail());
            userEntity.setBlogtitle(updateDto.getBlogtitle());
            userEntity.setImgurl(updateDto.getImgurl());
        } else {
            throw new RuntimeException("아이디를 찾을 수 없습니다.");
        }
    } // 트랜잭션이 걸려있으면 @Service 종료시에 변경감지해서 디비에 update함 = 더티체킹

}
