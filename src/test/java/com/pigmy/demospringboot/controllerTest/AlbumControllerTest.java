package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.AlbumController;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.ArtistService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumControllerTest {

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
    @Mock
    AlbumInfoValidator albumInfoValidatorMock;

    MockHttpServletRequest requestMock;
    MockHttpServletResponse response;
    @Autowired
    AlbumInfoValidator albumInfoValidator;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(albumController)
                .build();
        when(albumInfoValidatorMock.supports(AlbumInfo.class)).thenReturn(true);
    }

    @Test
    public void testListAlbumByGenre_SizeAlbumListIsPositive() throws Exception {
        List<AlbumInfo> expectedAlbum1 = asList(new AlbumInfo());
        List<List<AlbumInfo>> expectedAlbum = asList(expectedAlbum1);
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getListAlbumByGenre("1");

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        Assert.assertNotEquals(0, expectedAlbum.size());

        String genreName = "";
        Mockito.doReturn(genreName).when(albumServiceMock).getGenreNameOfAlbum(expectedAlbum.get(0).get(0));
        mockMvc.perform(get("/album/genre?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumList", expectedAlbum))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(model().attribute("genreName", genreName))
                .andExpect(view().name("index"));
    }

    @Test
    public void testListAlbumByGenre_SizeAlbumListIs0() throws Exception {
        List<List<AlbumInfo>> expectedAlbum = new ArrayList<List<AlbumInfo>>();
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getListAlbumByGenre("1");

        System.out.println("album size: " + expectedAlbum.size());
        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        //Assert.assertEquals(0, expectedAlbum.size());

        String genreName = "";
        //Mockito.doReturn(genreName).when(albumServiceMock).getGenreNameOfAlbum(expectedAlbum.get(0).get(0));
        mockMvc.perform(get("/album/genre?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumList", expectedAlbum))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(model().attribute("genreName", "No Album"))
                .andExpect(view().name("index"));
    }

    @Test
    public void testAlbumDetail() throws Exception {
        AlbumInfo expectedAlbum = new AlbumInfo(1, new Genre(), new Artist(), "ABC", 1.2, "abc.png");
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getAlbumDetail("386");

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        String genreName = "";
        Mockito.doReturn(genreName).when(albumServiceMock).getGenreNameOfAlbum(expectedAlbum);

        String artistName = "";
        Mockito.doReturn(artistName).when(albumServiceMock).getArtistNameOfAlbum(expectedAlbum);
        System.out.println("genre: " + expectedAlbum);
        mockMvc.perform(get("/album/detail?id=386"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumDetail", hasProperty("title", is(expectedAlbum.getTitle()))))
                .andExpect(model().attribute("albumDetail", hasProperty("price", is(expectedAlbum.getPrice()))))
                .andExpect(model().attribute("albumDetail", hasProperty("genre", is(expectedAlbum.getGenre()))))
                .andExpect(model().attribute("albumDetail", hasProperty("artist", is(expectedAlbum.getArtist()))))
                .andExpect(model().attribute("albumDetail", hasProperty("imageUrl", is(expectedAlbum.getImageUrl()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(model().attribute("genreAlbum", genreName))
                .andExpect(model().attribute("artistAlbum", artistName))
                .andExpect(view().name("albumDetail"));
    }

    @Test
    public void testListAlbumManage() throws Exception {
        List<AlbumInfo> expectedAlbum = asList(new AlbumInfo(1, new Genre(), new Artist(), "ABC", 1.2, "abc.png"),
                new AlbumInfo(2, new Genre(), new Artist(), "DEF", 2, "def.png"));
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getListAlbum();

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/album/manage"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumList", hasSize(2)))
                .andExpect(model().attribute("albumList", hasItem(
                        allOf(
                                hasProperty("title", is(expectedAlbum.get(0).getTitle())),
                                hasProperty("price", is(expectedAlbum.get(0).getPrice())),
                                hasProperty("genre", is(expectedAlbum.get(0).getGenre())),
                                hasProperty("artist", is(expectedAlbum.get(0).getArtist())),
                                hasProperty("imageUrl", is(expectedAlbum.get(0).getImageUrl()))
                        )
                )))
                .andExpect(model().attribute("albumList", hasItem(
                        allOf(
                                hasProperty("title", is(expectedAlbum.get(1).getTitle())),
                                hasProperty("price", is(expectedAlbum.get(1).getPrice())),
                                hasProperty("genre", is(expectedAlbum.get(1).getGenre())),
                                hasProperty("artist", is(expectedAlbum.get(1).getArtist())),
                                hasProperty("imageUrl", is(expectedAlbum.get(1).getImageUrl()))
                        )
                )))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("manageAlbum"));
    }

    @Test
    public void testAddAlbumGet() throws Exception {
        AlbumInfo expectedAlbum = new AlbumInfo();


        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<ArtistInfo> expectedArtist = asList(new ArtistInfo());
        Mockito.doReturn(expectedArtist).when(artistServiceMock).getListArtist();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/album/manage/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumForm", hasProperty("title", is(expectedAlbum.getTitle()))))
                .andExpect(model().attribute("albumForm", hasProperty("price", is(expectedAlbum.getPrice()))))
                .andExpect(model().attribute("albumForm", hasProperty("genre", is(expectedAlbum.getGenre()))))
                .andExpect(model().attribute("albumForm", hasProperty("artist", is(expectedAlbum.getArtist()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("artistList", expectedArtist))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("addAlbum"));
    }

   @Test
    public void testAddAlbumPost() throws Exception {
       AlbumInfo expectedAlbum = new AlbumInfo();
       expectedAlbum.setTitle("ABC");
       expectedAlbum.setPriceString("2");
       expectedAlbum.setGenreID("1");
       expectedAlbum.setArtistID("2");
       when(albumServiceMock.addAlbum(isA(AlbumInfo.class))).thenReturn(expectedAlbum);

       Album album = new Album();
       album.setID(1);
       album.setImageUrl(expectedAlbum.getImageUrl());
       album.setArtist(expectedAlbum.getArtist());
       album.setGenre(expectedAlbum.getGenre());
       album.setPrice(expectedAlbum.getPrice());
       album.setTitle(expectedAlbum.getTitle());
       when(albumServiceMock.getAlbumLast()).thenReturn(album);
       mockMvc.perform(post("/album/manage/add")
               .param("title", expectedAlbum.getTitle())
               .param("priceString", expectedAlbum.getPriceString())
               .param("genreID",expectedAlbum.getGenreID())
               .param("artistID",expectedAlbum.getArtistID())
               .flashAttr("albumForm", new AlbumInfo()))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(redirectedUrl("/album/detail?id=" + album.getID()));
       verify(albumServiceMock, times(1)).addAlbum(isA(AlbumInfo.class));
       verify(albumServiceMock, times(1)).getAlbumLast();
   }

    @Test
    public void testAddAlbumPost_whenSaveAlbumError() throws Exception {

        when(albumServiceMock.addAlbum(isA(AlbumInfo.class))).thenReturn(null);

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<ArtistInfo> expectedArtist = asList(new ArtistInfo());
        Mockito.doReturn(expectedArtist).when(artistServiceMock).getListArtist();

        mockMvc.perform(post("/album/manage/add"))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("artistList", expectedArtist))
                .andExpect(view().name("addAlbum"));
    }

    @Test
    public void testAddAlbumPost_HasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(albumInfoValidatorMock).validate(anyObject(), any(Errors.class));
        mockMvc.perform(post("/album/manage/add")
                .param("title", "")
                .param("priceString", "2")
                .param("genreID","1")
                .param("artistID","2")
                .flashAttr("albumForm", new AlbumInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("addAlbum"));

    }

    @Test
    public void testEditAlbumGet() throws Exception {
        AlbumInfo expectedAlbum = new AlbumInfo(1, new Genre(), new Artist(), "ABC", 1.2, "abc.png");
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getAlbumDetail("1");

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<ArtistInfo> expectedArtist = asList(new ArtistInfo());
        Mockito.doReturn(expectedArtist).when(artistServiceMock).getListArtist();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/album/manage/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumForm", hasProperty("title", is(expectedAlbum.getTitle()))))
                .andExpect(model().attribute("albumForm", hasProperty("price", is(expectedAlbum.getPrice()))))
                .andExpect(model().attribute("albumForm", hasProperty("genre", is(expectedAlbum.getGenre()))))
                .andExpect(model().attribute("albumForm", hasProperty("artist", is(expectedAlbum.getArtist()))))
                .andExpect(model().attribute("albumForm", hasProperty("imageUrl", is(expectedAlbum.getImageUrl()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("artistList", expectedArtist))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(model().attribute("albumID", "1"))
                .andExpect(view().name("editAlbum"));
    }

    @Test
    public void testEditAlbumPost() throws Exception {
        AlbumInfo expectedAlbum = new AlbumInfo();
        expectedAlbum.setTitle("ABC");
        expectedAlbum.setPriceString("2");
        expectedAlbum.setGenreID("1");
        expectedAlbum.setArtistID("2");
        when(albumServiceMock.editAlbum(isA(AlbumInfo.class), eq("1"))).thenReturn(expectedAlbum);
       mockMvc.perform(post("/album/manage/edit?id=1")
                .param("title",expectedAlbum.getTitle())
                .param("priceString", expectedAlbum.getPriceString())
                .param("genreID",expectedAlbum.getGenreID())
                .param("artistID",expectedAlbum.getArtistID())
                .flashAttr("albumForm", new AlbumInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/album/detail?id=1"));

        verify(albumServiceMock, times(1)).editAlbum(isA(AlbumInfo.class), eq("1"));
        verifyNoMoreInteractions(albumServiceMock);

    }

    @Test
    public void testEditAlbumPost_whenEditError() throws Exception {

        doThrow(Exception.class).when(albumServiceMock).editAlbum(isA(AlbumInfo.class), eq("1"));
        mockMvc.perform(post("/album/manage/edit?id=1"))
                .andExpect(view().name("editAlbum"));

        verify(albumServiceMock, times(1)).editAlbum(isA(AlbumInfo.class), eq("1"));
        verifyNoMoreInteractions(albumServiceMock);

    }

    @Test
    public void testEditAlbumPost_whenAlbumInfoNotChange() throws Exception {
        AlbumInfo expectedAlbum = new AlbumInfo();
        expectedAlbum.setTitle("ABC");
        expectedAlbum.setPriceString("2");
        expectedAlbum.setGenreID("1");
        expectedAlbum.setArtistID("2");
        when(albumServiceMock.editAlbum(isA(AlbumInfo.class), eq("1"))).thenReturn(null);

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        List<ArtistInfo> expectedArtist = asList(new ArtistInfo());
        Mockito.doReturn(expectedArtist).when(artistServiceMock).getListArtist();

        mockMvc.perform(post("/album/manage/edit?id=1")
                .param("title",expectedAlbum.getTitle())
                .param("priceString", expectedAlbum.getPriceString())
                .param("genreID",expectedAlbum.getGenreID())
                .param("artistID",expectedAlbum.getArtistID())
                .flashAttr("albumForm", new AlbumInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("artistList", expectedArtist))
                .andExpect(model().attribute("albumID", "1"))
                .andExpect(model().attribute("errorMessage", "The album information has not been changed"))
                .andExpect(view().name("editAlbum"));

        verify(albumServiceMock, times(1)).editAlbum(isA(AlbumInfo.class), eq("1"));
        verifyNoMoreInteractions(albumServiceMock);

    }

    @Test
    public void testEditAlbumPost_HasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(albumInfoValidatorMock).validate(anyObject(), any(Errors.class));

        mockMvc.perform(post("/album/manage/edit?id=1")
                .param("title", "")
                .param("priceString", "2")
                .param("genreID","1")
                .param("artistID","2")
                .flashAttr("albumForm", new AlbumInfo()))
                .andExpect(view().name("editAlbum"));

        verifyZeroInteractions(albumServiceMock);
    }

    @Test
    public void testDisplayImage() throws Exception {
        Mockito.doNothing().when(albumServiceMock).displayImage(isA(HttpServletResponse.class),eq("1"));

        mockMvc.perform(get("/album/albumImage?id=1"))
                .andExpect(status().isOk());
        verify(albumServiceMock, times(1)).displayImage(isA(HttpServletResponse.class),eq("1"));
        verifyNoMoreInteractions(albumServiceMock);
    }

    @Test
    public void testDeleteAlbum() throws Exception {
        when(albumServiceMock.deleteAlbum("1")).thenReturn(true);
        mockMvc.perform(get("/album/manage/delete?id=1"))
                .andExpect(redirectedUrl("/album/manage"));
        verify(albumServiceMock, times(1)).deleteAlbum("1");
    }

    @Test
    public void testDeleteAlbum_whenDeleteAlbumError() throws Exception {
        when(albumServiceMock.deleteAlbum("1")).thenReturn(false);
        mockMvc.perform(get("/album/manage/delete?id=1"))
                .andExpect(view().name("manageAlbum"));
        verify(albumServiceMock, times(1)).deleteAlbum("1");
    }

}
