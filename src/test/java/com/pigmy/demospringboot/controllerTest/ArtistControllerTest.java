package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.ArtistController;
import com.pigmy.demospringboot.controller.GenreController;
import com.pigmy.demospringboot.model.*;
import com.pigmy.demospringboot.service.ArtistService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.ArtistInfoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistControllerTest {
    @InjectMocks
    ArtistController artistController;

    @Mock
    GenreService genreServiceMock;
    @Mock
    ArtistService artistServiceMock;
    @Mock
    CartSession cartSessionMock;
    @Mock
    ArtistInfoValidator artistInfoValidatorMock;

    MockHttpServletRequest requestMock;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(artistController)
                .build();
        when(artistInfoValidatorMock.supports(ArtistInfo.class)).thenReturn(true);
    }

    @Test
    public void testAddArtistGet() throws Exception {
        ArtistInfo expectedArtist = new ArtistInfo();


        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/artist/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("artistForm", hasProperty("name", is(expectedArtist.getName()))))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("addArtist"));
    }

    @Test
    public void testAddArtistPost() throws Exception {
        ArtistInfo artistInfo = new ArtistInfo();
        artistInfo.setName("ABC");
        when(artistServiceMock.addArtist(Matchers.isA(ArtistInfo.class))).thenReturn(artistInfo);

        mockMvc.perform(post("/artist/add")
                .param("name", "ABC")
                .flashAttr("artistForm", new ArtistInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testAddArtistPost_whenSaveArtistError() throws Exception {
        when(artistServiceMock.addArtist(Matchers.isA(ArtistInfo.class))).thenReturn(null);

        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        mockMvc.perform(post("/artist/add")
                .param("name", "ABC")
                .flashAttr("artistForm", new ArtistInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(view().name("addArtist"));
    }

    @Test
    public void testAddArtistPost_whenHasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(artistInfoValidatorMock).validate(anyObject(), any(Errors.class));

        mockMvc.perform(post("/artist/add")
                .param("name", "ABC")
                .flashAttr("artistForm", new ArtistInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("addArtist"));
    }

}
