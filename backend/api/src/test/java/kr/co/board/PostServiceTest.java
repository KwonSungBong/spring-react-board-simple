package kr.co.board;

import kr.co.board.domain.Post;
import kr.co.board.dto.PostDto;
import kr.co.board.post.service.PostService;
import kr.co.board.post.service.impl.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostServiceTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postServiceImpl;

    private Post post;

    @Before
    public void setUp(){
        post = new Post();

        post.setSubject("testSubject");
        post.setContent("testContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContenttestContent");

        추가하기();
    }

    public Post lastOne(){
        List<Post> postList = (List<Post>) postRepository.findAll();

        if(postList.size() > 0){
            return postList.get(postList.size() - 1);
        }
        return null;
    }

    public String getExceptionMessageByTransactionSystemException(TransactionSystemException tse){
        String exceptionMessage = "";
        Throwable t = tse.getCause();
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause();
        }
        if (t instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException)t;

            Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();

            for (ConstraintViolation<?> cv : cvs) {
                exceptionMessage = cv.getMessage();
            }
        }
        return exceptionMessage;
    }

    public String getExceptionMessageByConstraintViolationException(ConstraintViolationException cve){
        String exceptionMessage = "";

        Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();

        for (ConstraintViolation<?> cv : cvs) {
            exceptionMessage = cv.getMessage();
        }

        return exceptionMessage;
    }

    @Test
    public void 목록_가져오기(){
        List<PostDto.Summary> PostList = postServiceImpl.findSummaryList();
        Assert.assertNotNull(PostList);

        PostList
            .stream()
            .forEach(
                Post -> {
                    boolean isSuccess = Post.getSummaryContent() != null &&
                            Post.getSummaryContent().length() <= PostDto.Summary.CONTENT_SUMMARY_END_INDEX;
                    Assert.assertTrue(isSuccess);
                }
            );
    }

    @Test
    public void 한개가져오기(){
        Post lastPost= lastOne();

        PostDto.Detail post = postServiceImpl.findDetailOne(lastPost.getIdx());

        Assert.assertTrue(lastPost.getIdx() == post.getIdx());
    }

    @Test
    public void 추가하기(){
        int result = postServiceImpl.create(modelMapper.map(post, PostDto.Create.class));

        Assert.assertSame(1,result);
    }

    @Test
    public void 추가하기2(){
        boolean isSuccess = false;
        try {
            post.setSubject(null);
            postServiceImpl.create(modelMapper.map(post, PostDto.Create.class));
        } catch (TransactionSystemException tse) {
            if(Post.REQUIRED_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        } catch (ConstraintViolationException cve) {
            if(Post.REQUIRED_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 추가하기3(){
        boolean isSuccess = false;
        try {
            post.setSubject("");
            postServiceImpl.create(modelMapper.map(post, PostDto.Create.class));
        } catch (TransactionSystemException tse) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        } catch (ConstraintViolationException cve) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 추가하기4(){
        boolean isSuccess = false;
        try {
            post.setSubject("     ");
            postServiceImpl.create(modelMapper.map(post, PostDto.Create.class));
        } catch (TransactionSystemException tse) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        } catch (ConstraintViolationException cve) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 수정하기(){
        Post lastPost = lastOne();

        lastPost.setSubject("testtest");

        int result = postServiceImpl.update(modelMapper.map(lastPost, PostDto.Update.class));

        Assert.assertSame(result, 1);
        Assert.assertSame(lastPost.getSubject(), "testtest");
    }

    @Test
    public void 수정하기2(){
        Post lastPost = lastOne();

        lastPost.setSubject(null);

        boolean isSuccess = false;
        try {
            postServiceImpl.update(modelMapper.map(lastPost, PostDto.Update.class));
        } catch (TransactionSystemException tse) {
            if(Post.REQUIRED_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        } catch (ConstraintViolationException cve) {
            if(Post.REQUIRED_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 수정하기3(){
        Post lastPost = lastOne();

        lastPost.setSubject("");

        boolean isSuccess = false;
        try {
            postServiceImpl.update(modelMapper.map(lastPost, PostDto.Update.class));
        } catch (TransactionSystemException tse) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        }  catch (ConstraintViolationException cve) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 수정하기4(){
        Post lastPost = lastOne();

        lastPost.setSubject("     ");

        boolean isSuccess = false;
        try {
            postServiceImpl.update(modelMapper.map(lastPost, PostDto.Update.class));
        } catch (TransactionSystemException tse) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByTransactionSystemException(tse))){
                isSuccess = true;
            }
        }  catch (ConstraintViolationException cve) {
            if(Post.SUBJECT_VALID_MESSAGE.equals(getExceptionMessageByConstraintViolationException(cve))){
                isSuccess = true;
            }
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void 삭제하기(){
        Post lastPost = lastOne();

        int result = postServiceImpl.remove(lastPost.getIdx());

        Assert.assertSame(result, 1);
    }

    @Test
    public void 삭제하기2(){
        long t = -10000;

        int result = postServiceImpl.remove(t);

        Assert.assertSame(result, 0);
    }
}
