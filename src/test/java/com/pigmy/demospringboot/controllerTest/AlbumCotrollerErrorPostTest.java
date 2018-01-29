package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.AlbumController;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.ArtistService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumCotrollerErrorPostTest {
    @InjectMocks
    AlbumController albumController;

    @Mock
    AlbumService albumServiceMock;
    @Mock
    GenreService genreServiceMock;
    @Mock
    CartSession cartSessionMock;
    @Mock
    ArtistService artistServiceMock;


    MockHttpServletRequest requestMock;
   @Mock
    AlbumInfoValidator albumInfoValidatorMock;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(albumController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        when(albumInfoValidatorMock.supports(AlbumInfo.class)).thenReturn(true);
        //doNothing().when(albumInfoValidatorMock).validate(any(), any(Errors.class));
    }

    @Test
    public void testAddAlbumPost_HasError() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(albumInfoValidatorMock).validate(anyObject(), any(Errors.class));
        mockMvc.perform(post("/album/manage/add")
                .param("title", "abc")
                .param("priceString", "2")
                .param("genreID","1")
                .param("artistID","2")
                .flashAttr("albumForm", new AlbumInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("addAlbum"));

    }
}
