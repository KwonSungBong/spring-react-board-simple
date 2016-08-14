package kr.co.board.post.service.impl;

import kr.co.board.domain.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
