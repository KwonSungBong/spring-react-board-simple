package kr.co.board.reply.service;

import kr.co.board.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ksb on 2016-08-07.
 */
public interface ReplyService {
    List<ReplyDto.Summary> findSummaryList();

    Page<ReplyDto.Summary> findSummaryList(Pageable pageable);

    ReplyDto.Detail findDetailOne(Long idx);

    int create(ReplyDto.Create reply);

    int update(ReplyDto.Update reply);

    int remove(Long idx);
}
