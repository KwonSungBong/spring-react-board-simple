package kr.co.board.reply.service.impl;

import kr.co.board.domain.Reply;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ksb on 2016-08-07.
 */
@Repository
public interface ReplyRepository extends PagingAndSortingRepository<Reply, Long> {
}
