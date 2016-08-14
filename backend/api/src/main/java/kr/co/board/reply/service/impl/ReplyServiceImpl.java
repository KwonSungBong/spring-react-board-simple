package kr.co.board.reply.service.impl;

import kr.co.board.domain.Reply;
import kr.co.board.dto.ReplyDto;
import kr.co.board.reply.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by ksb on 2016-08-07.
 */
@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public List<ReplyDto.Summary> findSummaryList() {
        return StreamSupport.stream(replyRepository.findAll(new Sort(Sort.Direction.DESC, "idx")).spliterator(), false)
                .map(reply -> modelMapper.map(reply, ReplyDto.Summary.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReplyDto.Summary> findSummaryList(Pageable pageable) {
        Page<Reply> page = replyRepository.findAll(pageable);
        List<ReplyDto.Summary> content = page.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDto.Summary.class)).collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public ReplyDto.Detail findDetailOne(Long idx) {
        return modelMapper.map(replyRepository.findOne(idx), ReplyDto.Detail.class);
    }

    @Override
    public int create(ReplyDto.Create reply) {
        Reply result = replyRepository.save(modelMapper.map(reply,Reply.class));

        if(!ObjectUtils.isEmpty(result)){
            return 1;
        }

        return 0;
    }

    @Override
    public int update(ReplyDto.Update reply) {
        Reply result = replyRepository.save(modelMapper.map(reply,Reply.class));

        if(!ObjectUtils.isEmpty(reply)){
            return 1;
        }

        return 0;

    }

    @Override
    public int remove(Long idx) {
        if(replyRepository.exists(idx)){
            replyRepository.delete(idx);
            return 1;
        }

        return 0;
    }
}
