package kr.co.board;

import junit.framework.Assert;
import kr.co.board.domain.Post;
import kr.co.board.dto.PostDto;
import kr.co.board.post.PostController;
import kr.co.board.post.service.impl.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostControllerTest {

    @Autowired
    private ModelMapper modelMapper;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostController postController;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    private Post post;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        post = new Post();
        post.setSubject("TTTT");
        post.setContent("TTTTTTTT");
    }

    public Post lastOne(){
        List<Post> PostList = (List<Post>) postRepository.findAll();

        if(PostList.size() > 0){
            return PostList.get(PostList.size() - 1);
        }
        return null;
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Test
    public void 목록가지고오기() throws Exception {
        추가하기();

        this.mockMvc.perform(
                get("/post/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].idx", is(notNullValue())))
                .andExpect(jsonPath("$[0].subject", is(notNullValue())))
                .andExpect(jsonPath("$[0].content", is(notNullValue())))
                .andExpect(jsonPath("$[0].summaryContent", is(notNullValue())))
                .andExpect(status().isOk());
    }

    @Test
    public void 페이지목록가지고오기() throws Exception {
        추가하기();

        this.mockMvc.perform(
                get("/post")
                        .contentType(MediaType.ALL.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", is(notNullValue())))
//                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(status().isOk());
    }

    @Test
    public void 한개가져오기() throws Exception {
        추가하기();
        Post lastPost = lastOne();

        this.mockMvc.perform(
                get("/post/{idx}", lastPost.getIdx())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.idx", is(notNullValue())))
                .andExpect(jsonPath("$.idx", instanceOf(Integer.class)))
                .andExpect(jsonPath("$.subject", is(notNullValue())))
                .andExpect(jsonPath("$.subject", instanceOf(String.class)))
                .andExpect(jsonPath("$.subject", is(lastPost.getSubject())))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.content", instanceOf(String.class)))
                .andExpect(jsonPath("$.content", is(lastPost.getContent())))
                .andExpect(status().isOk());
    }

    @Test
    public void 한개가져오기2() throws Exception {
        추가하기();
        Post lastPost = lastOne();

        this.mockMvc.perform(
                get("/post/{idx}", lastPost.getIdx())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.idx", is(notNullValue())))
                .andExpect(jsonPath("$.idx", instanceOf(Integer.class)))
                .andExpect(jsonPath("$.subject", is(notNullValue())))
                .andExpect(jsonPath("$.subject", instanceOf(String.class)))
                .andExpect(jsonPath("$.subject", is(lastPost.getSubject())))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.content", instanceOf(String.class)))
                .andExpect(jsonPath("$.content", is(lastPost.getContent())))
                .andExpect(status().isOk());
    }

    @Test
    public void 추가하기() throws Exception {
        PostDto.Create createPost = modelMapper.map(post, PostDto.Create.class);

        this.mockMvc.perform(
                post("/post")
                    .content(this.json(createPost))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", is(1)))
                .andExpect(status().isCreated());
    }

    @Test
    public void 수정하기() throws Exception {
        추가하기();
        PostDto.Update lastPost = modelMapper.map(lastOne(), PostDto.Update.class);

        this.mockMvc.perform(
                patch("/post")
                    .content(this.json(lastPost))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", is(1)))
                .andExpect(status().isNoContent());
    }


    @Test
    public void 삭제하기() throws Exception {
        추가하기();
        Post lastPost = lastOne();

        this.mockMvc.perform(
                delete("/post/" + this.json(lastPost.getIdx()))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", is(1)))
                .andExpect(status().isNoContent());
    }

}
