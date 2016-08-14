package kr.co.board.post;

import kr.co.board.dto.PostDto;
import kr.co.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postServiceImpl;

    @GetMapping("/findAll")
    public List<PostDto.Summary> findSummaryList(){
        return postServiceImpl.findSummaryList();
    }

    @GetMapping
    public Page<PostDto.Summary> findSummaryList(@PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){
        return postServiceImpl.findSummaryList(pageable);
    }

    @GetMapping("/{idx}")
    public PostDto.Detail findDetailOne(@PathVariable("idx") long idx){
        return postServiceImpl.findDetailOne(idx);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody PostDto.Create post){
        return postServiceImpl.create(post);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody PostDto.Create post, @RequestPart("file") Collection<MultipartFile> fileList){
        return postServiceImpl.create(post, fileList);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int update(@RequestBody PostDto.Update post){
        return postServiceImpl.update(post);
    }

    @PatchMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int update(@RequestBody PostDto.Update post, @RequestPart("file") Collection<MultipartFile> fileList){
        return postServiceImpl.update(post, fileList);
    }

    @DeleteMapping("/{idx}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int remove(@PathVariable("idx") long idx){
        return postServiceImpl.remove(idx);
    }

}
