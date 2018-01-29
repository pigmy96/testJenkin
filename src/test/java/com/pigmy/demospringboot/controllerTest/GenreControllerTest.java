package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.AlbumController;
import com.pigmy.demospringboot.controller.GenreController;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.ArtistService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;
import com.pigmy.demospringboot.validator.GenreInfoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
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
public class GenreControllerTest {
    @InjectMocks
    GenreController genreController;

    @Mock
    GenreService genreServiceMock;
    @Mock
    CartSession cartSessionMock;
    @Mock
    GenreInfoValidator genreInfoValidatorMock;

    MockHttpServletRequest requestMock;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(genreController)
                .build();
        when(genreInfoValidatorMock.supports(GenreInfo.class)).thenReturn(true);
    }

    @Test
    public void testAddGenreGet() throws Exception {
        GenreInfo expectedGenre = new GenreInfo();


        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/genre/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genreForm", hasProperty("name", is(expectedGenre.getName()))))
                .andExpect(model().attribute("genreForm", hasProperty("description", is(expectedGenre.getDescription()))))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("addGenre"));
    }

    @Test
    public void testAddGenrePost() throws Exception {
        GenreInfo genreInfo = new GenreInfo();
        genreInfo.setName("ABC");
        genreInfo.setDescription("this is description");
        when(genreServiceMock.addGenre(Matchers.isA(GenreInfo.class))).thenReturn(genreInfo);
        mockMvc.perform(post("/genre/add")
                .param("name", "ABC")
                .param("description", "this is description")
                .flashAttr("genreForm", new GenreInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    public void testAddGenrePost_whenSaveGenreError() throws Exception {
        when(genreServiceMock.addGenre(Matchers.isA(GenreInfo.class))).thenReturn(null);

        List<GenreInfo> expectedGenreList = asList(new GenreInfo());
        Mockito.doReturn(expectedGenreList).when(genreServiceMock).getListGenre();

        mockMvc.perform(post("/genre/add"))
                .andExpect(model().attribute("genreList", expectedGenreList))
                .andExpect(view().name("addGenre"));
    }

    @Test
    public void testAddGenrePost_whenHasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(genreInfoValidatorMock).validate(anyObject(), any(Errors.class));

        mockMvc.perform(post("/genre/add")
                .param("name", "ABC")
                .param("description", "this is description")
                .flashAttr("genreForm", new GenreInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("addGenre"));

    }
}
