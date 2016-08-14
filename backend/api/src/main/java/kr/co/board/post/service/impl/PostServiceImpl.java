package kr.co.board.post.service.impl;

import kr.co.board.domain.Image;
import kr.co.board.domain.ImageGroup;
import kr.co.board.domain.Post;
import kr.co.board.dto.ImageGroupDto;
import kr.co.board.dto.PostDto;
import kr.co.board.image.service.ImageService;
import kr.co.board.image.service.impl.ImageGroupRepository;
import kr.co.board.image.service.impl.ImageRepository;
import kr.co.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageGroupRepository imageGroupRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<PostDto.Summary> findSummaryList() {
        return StreamSupport.stream(postRepository.findAll(new Sort(Sort.Direction.DESC, "idx")).spliterator(), false)
                .map(post -> modelMapper.map(post, PostDto.Summary.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto.Summary> findSummaryList(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        List<PostDto.Summary> content = page.getContent().stream().map(Post -> modelMapper.map(Post, PostDto.Summary.class)).collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public PostDto.Detail findDetailOne(Long idx) {
        return modelMapper.map(postRepository.findOne(idx), PostDto.Detail.class);
    }

    @Override
    public int create(PostDto.Create post) {
        Post result = modelMapper.map(post, Post.class);

        if(!ObjectUtils.isEmpty(result.getImageGroup()) && result.getImageGroup().getImageList().size() > 0){
            ImageGroup imageGroup = imageGroupRepository.save(new ImageGroup());
            List<Image> imageList = imageRepository.findAll(Arrays.stream(result.getImageGroup().getImageList().stream().mapToLong(Image::getImageIdx).toArray()).boxed().collect(Collectors.toList()));
            imageList.stream().forEach(image -> image.setImageGroup(imageGroup));
            imageGroup.setImageList(imageList);
            imageRepository.save(imageList);
            result.setImageGroup(imageGroup);
        } else {
            result.setImageGroup(null);
        }

        postRepository.save(result);

        log.debug("추가데이터 : {}", result.getSubject());

        if(!ObjectUtils.isEmpty(result)){
            return 1;
        }

        return 0;
    }

    @Override
    public int update(PostDto.Update post) {
        Post result = modelMapper.map(post, Post.class);

        if(!ObjectUtils.isEmpty(result.getImageGroup()) && result.getImageGroup().getImageList().size() > 0){
            ImageGroup imageGroup;
            if(result.getImageGroup().getImageGroupIdx() != 0){
                imageGroup = imageGroupRepository.findOne(result.getImageGroup().getImageGroupIdx());
            }else {
                imageGroup = imageGroupRepository.save(new ImageGroup());
            }

            List<Image> imageList = imageRepository.findAll(Arrays.stream(result.getImageGroup().getImageList().stream().mapToLong(Image::getImageIdx).toArray()).boxed().collect(Collectors.toList()));
            imageList.stream().forEach(image -> image.setImageGroup(imageGroup));
            imageGroup.setImageList(imageList);
            imageRepository.save(imageList);
            result.setImageGroup(imageGroup);
        } else {
            result.setImageGroup(null);
        }

        postRepository.save(result);

        log.debug("수정데이터 : {}", result.getSubject());

        if(!ObjectUtils.isEmpty(result)){
            return 1;
        }

        return 0;
    }

    @Transactional
    @Modifying
    @Override
    public int create(PostDto.Create post, Collection<MultipartFile> fileList) {
        final Post result = modelMapper.map(post, Post.class);

        if( !ObjectUtils.isEmpty(fileList) && fileList.size() > 0 ) {
            ImageGroup imageGroup = imageService.upload(fileList);
            result.setImageGroup(imageGroup);
        }

        if(ObjectUtils.isEmpty(postRepository.save(result))){
            return 0;
        }
        return 1;
    }

    @Transactional
    @Modifying
    @Override
    public int update(PostDto.Update post, Collection<MultipartFile> fileList) {
        final Post result = modelMapper.map(post, Post.class);

        ImageGroup imageGroup;
        if(!ObjectUtils.isEmpty(fileList) && fileList.size() > 0){
            imageGroup = imageService.upload(fileList, modelMapper.map(result.getImageGroup(), ImageGroupDto.Update.class));
        }else {
            imageGroup = imageService.updateEnabledAuto(result.getImageGroup());
        }
        result.setImageGroup(imageGroup);

        if(ObjectUtils.isEmpty(postRepository.save(result))){
            return 0;
        }

        return 1;
    }

    @Override
    public int remove(Long idx) {
        if(postRepository.exists(idx)){
            postRepository.delete(idx);
            return 1;
        }

        return 0;
    }
}
