package kr.co.board.post.service;

import kr.co.board.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
public interface PostService {
    List<PostDto.Summary> findSummaryList();

    Page<PostDto.Summary> findSummaryList(Pageable pageable);

    PostDto.Detail findDetailOne(Long idx);

    int create(PostDto.Create post);

    int update(PostDto.Update post);

    int create(PostDto.Create post, Collection<MultipartFile> fileList);

    int update(PostDto.Update post, Collection<MultipartFile> fileList);

    int remove(Long idx);
}
