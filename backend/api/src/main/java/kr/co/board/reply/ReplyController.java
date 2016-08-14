package kr.co.board.reply;

import kr.co.board.dto.ReplyDto;
import kr.co.board.reply.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyServiceImpl;

    @GetMapping("/findAll")
    public List<ReplyDto.Summary> findSummaryList(){
        return replyServiceImpl.findSummaryList();
    }

    @GetMapping
    public Page<ReplyDto.Summary> findSummaryList(@PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){
        return replyServiceImpl.findSummaryList(pageable);
    }

    @GetMapping("/{idx}")
    public ReplyDto.Detail findDetailOne(@PathVariable("idx") long idx){
        return replyServiceImpl.findDetailOne(idx);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody ReplyDto.Create reply){
        return replyServiceImpl.create(reply);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int update(@RequestBody ReplyDto.Update reply){
        return replyServiceImpl.update(reply);
    }

    @DeleteMapping("/{idx}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int remove(@PathVariable("idx") long idx){
        return replyServiceImpl.remove(idx);
    }

}
