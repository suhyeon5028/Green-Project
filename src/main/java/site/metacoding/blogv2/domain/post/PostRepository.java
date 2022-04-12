package site.metacoding.blogv2.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post WHERE title like %:keyword%", nativeQuery = true)

    List<Post> mSearch(@Param("keyword") String keyword);

}