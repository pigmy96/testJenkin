package com.pigmy.demospringboot.serviceTest;

import com.pigmy.demospringboot.dao.AlbumDAO;
import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.dao.GenreDAO;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.impl.AlbumServiceImpl;
import org.apache.commons.collections.functors.ExceptionClosure;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumServiceTest {

    @InjectMocks
    AlbumServiceImpl albumServiceImpl;
    @Mock
    AlbumDAO albumDAOMock;
    @Mock
    GenreDAO genreDAOMock;
    @Mock
    ArtistDAO artistDAOMock;

    private AlbumInfo expectedAlbum;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Genre genre = new Genre();
        genre.setID(1);
        genre.setName("genre1");
        genre.setDescription("des1");

        Artist artist = new Artist();
        artist.setID(1);
        artist.setName("artist1");

        expectedAlbum = new AlbumInfo(1, genre, artist, "Title", 1.2, "abc.png");
        Mockito.when(albumDAOMock.findAlbumDetail(1)).thenReturn(expectedAlbum);

        List<AlbumInfo> expectedAlbumList = asList(
                new AlbumInfo(1, genre, artist, "Title1", 1.2, "abc.png"),
                new AlbumInfo(2, genre, artist, "Title2", 2, "123.png"),
                new AlbumInfo(3, genre, artist, "Title3", 1, "def.png"),
                new AlbumInfo(4, genre, artist, "Title4", 1, "456.png"));
        Mockito.when(albumDAOMock.getListAlbum()).thenReturn(expectedAlbumList);
        Mockito.when(genreDAOMock.getListAlbumByGenre(1)).thenReturn(expectedAlbumList);
    }

    @Test
    public void testGetAlbumDetail(){

        AlbumInfo albumInfo = albumServiceImpl.getAlbumDetail("1");
        Assert.assertEquals(1, albumInfo.getID());
        Assert.assertEquals("Title", albumInfo.getTitle());
        Assert.assertEquals("abc.png", albumInfo.getImageUrl());
        Assert.assertEquals(1.2, albumInfo.getPrice(), 0);
    }

    @Test
    public void testGetListAlbumByGenre(){

        List<List<AlbumInfo>> listAlbumByGenre = albumServiceImpl.getListAlbumByGenre("1");
        Assert.assertEquals(2, listAlbumByGenre.size());
        Assert.assertEquals(3, listAlbumByGenre.get(0).size());
        Assert.assertEquals(1, listAlbumByGenre.get(1).size());

        Assert.assertEquals(1, listAlbumByGenre.get(0).get(0).getID());
        Assert.assertEquals("Title1", listAlbumByGenre.get(0).get(0).getTitle());
        Assert.assertEquals("abc.png", listAlbumByGenre.get(0).get(0).getImageUrl());
        Assert.assertEquals(1.2, listAlbumByGenre.get(0).get(0).getPrice(), 0);

        Assert.assertEquals(2, listAlbumByGenre.get(0).get(1).getID());
        Assert.assertEquals("Title2", listAlbumByGenre.get(0).get(1).getTitle());
        Assert.assertEquals("123.png", listAlbumByGenre.get(0).get(1).getImageUrl());
        Assert.assertEquals(2, listAlbumByGenre.get(0).get(1).getPrice(), 0);

        Assert.assertEquals(3, listAlbumByGenre.get(0).get(2).getID());
        Assert.assertEquals("Title3", listAlbumByGenre.get(0).get(2).getTitle());
        Assert.assertEquals("def.png", listAlbumByGenre.get(0).get(2).getImageUrl());
        Assert.assertEquals(1, listAlbumByGenre.get(0).get(2).getPrice(), 0);

        Assert.assertEquals(4, listAlbumByGenre.get(1).get(0).getID());
        Assert.assertEquals("Title4", listAlbumByGenre.get(1).get(0).getTitle());
        Assert.assertEquals("456.png", listAlbumByGenre.get(1).get(0).getImageUrl());
        Assert.assertEquals(1, listAlbumByGenre.get(1).get(0).getPrice(), 0);
    }

    @Test
    public void testGetListAlbumForHome(){
        List<List<AlbumInfo>> listAlbumForHome = albumServiceImpl.getListAlbumForHome();
        Assert.assertEquals(2, listAlbumForHome.size());
        Assert.assertEquals(3, listAlbumForHome.get(0).size());
        Assert.assertEquals(1, listAlbumForHome.get(1).size());

        Assert.assertEquals(1, listAlbumForHome.get(0).get(0).getID());
        Assert.assertEquals("Title1", listAlbumForHome.get(0).get(0).getTitle());
        Assert.assertEquals("abc.png", listAlbumForHome.get(0).get(0).getImageUrl());
        Assert.assertEquals(1.2, listAlbumForHome.get(0).get(0).getPrice(), 0);

        Assert.assertEquals(2, listAlbumForHome.get(0).get(1).getID());
        Assert.assertEquals("Title2", listAlbumForHome.get(0).get(1).getTitle());
        Assert.assertEquals("123.png", listAlbumForHome.get(0).get(1).getImageUrl());
        Assert.assertEquals(2, listAlbumForHome.get(0).get(1).getPrice(), 0);

        Assert.assertEquals(3, listAlbumForHome.get(0).get(2).getID());
        Assert.assertEquals("Title3", listAlbumForHome.get(0).get(2).getTitle());
        Assert.assertEquals("def.png", listAlbumForHome.get(0).get(2).getImageUrl());
        Assert.assertEquals(1, listAlbumForHome.get(0).get(2).getPrice(), 0);

        Assert.assertEquals(4, listAlbumForHome.get(1).get(0).getID());
        Assert.assertEquals("Title4", listAlbumForHome.get(1).get(0).getTitle());
        Assert.assertEquals("456.png", listAlbumForHome.get(1).get(0).getImageUrl());
        Assert.assertEquals(1, listAlbumForHome.get(1).get(0).getPrice(), 0);
    }

    @Test
    public void testGetListAlbum(){

        List<AlbumInfo> albumInfo = albumServiceImpl.getListAlbum();
        Assert.assertEquals(4, albumInfo.size());

        Assert.assertEquals(1, albumInfo.get(0).getID());
        Assert.assertEquals("Title1", albumInfo.get(0).getTitle());
        Assert.assertEquals("abc.png", albumInfo.get(0).getImageUrl());
        Assert.assertEquals(1.2, albumInfo.get(0).getPrice(), 0);

        Assert.assertEquals(2, albumInfo.get(1).getID());
        Assert.assertEquals("Title2", albumInfo.get(1).getTitle());
        Assert.assertEquals("123.png", albumInfo.get(1).getImageUrl());
        Assert.assertEquals(2, albumInfo.get(1).getPrice(), 0);

        Assert.assertEquals(3,albumInfo.get(2).getID());
        Assert.assertEquals("Title3", albumInfo.get(2).getTitle());
        Assert.assertEquals("def.png", albumInfo.get(2).getImageUrl());
        Assert.assertEquals(1,albumInfo.get(2).getPrice(), 0);

        Assert.assertEquals(4, albumInfo.get(3).getID());
        Assert.assertEquals("Title4",  albumInfo.get(3).getTitle());
        Assert.assertEquals("456.png",  albumInfo.get(3).getImageUrl());
        Assert.assertEquals(1,  albumInfo.get(3).getPrice(), 0);
    }

    @Test
    public void testDeleteAlbum_whenSuccessThenReturnTrue(){
        doNothing().when(albumDAOMock).delete(expectedAlbum);
        boolean isDeleted = albumServiceImpl.deleteAlbum("1");
        Assert.assertEquals(true, isDeleted);
    }

    @Test
    public void testDeleteAlbum_whenDeleteFailThenReturnFalse(){
        doThrow(Exception.class).when(albumDAOMock).delete(expectedAlbum);
        boolean isDeleted = albumServiceImpl.deleteAlbum("1");
        Assert.assertEquals(false, isDeleted);
    }

    @Test
    public void testGetAlbumLast(){
        Album album = new Album();
        album.setTitle("title");
        album.setPrice(2);
        album.setImageUrl("abc.png");
        album.setGenre(new Genre());
        album.setArtist(new Artist());

        doReturn(album).when(albumDAOMock).getAlbumLast();
        Assert.assertEquals(album, albumServiceImpl.getAlbumLast());
    }

    @Test
    public void testGetGenreNameOfAlbum(){
        Genre genre = new Genre();
        genre.setID(1);
        genre.setName("genre1");
        genre.setDescription("des1");

        AlbumInfo albumInfo = new AlbumInfo(1, genre, new Artist(), "Title", 1.2, "abc.png");

        Assert.assertEquals(genre.getName(), albumServiceImpl.getGenreNameOfAlbum(albumInfo));
    }

    @Test
    public void testGetArtistNameOfAlbum(){
        Artist artist = new Artist();
        artist.setID(1);
        artist.setName("artist1");

        AlbumInfo albumInfo = new AlbumInfo(1, new Genre(), artist, "Title", 1.2, "abc.png");

        Assert.assertEquals(artist.getName(), albumServiceImpl.getArtistNameOfAlbum(albumInfo));
    }

    @Test
    public void testAddAlbum_whenUploadFileErrorThenReturnNull () {
        AlbumInfo albumInfo = new AlbumInfo();
        albumInfo.setGenreID("1");
        albumInfo.setArtistID("1");
        albumInfo.setPriceString("2");
        albumInfo.setTitle("Title1");

        doThrow(Exception.class).when(albumDAOMock).save(albumInfo);

        Assert.assertEquals(null, albumServiceImpl.addAlbum(albumInfo));

    }

    @Test
    public void testAddAlbum() throws IOException {
        AlbumInfo albumInfo = new AlbumInfo();
        albumInfo.setGenreID("1");
        albumInfo.setArtistID("1");
        albumInfo.setPriceString("2");
        albumInfo.setTitle("Title1");

        MultipartFile multipartFile = getMockCommonsMultipartFile("abc.png", "images");
        //MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
       // request.addFile(multipartFile);
        //CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) request.getFile("abc.png");
        CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(new FileItem() {
            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }
            @Override
            public String getContentType() {
                return null;
            }
            @Override
            public String getName() {
                return null;
            }
            @Override
            public boolean isInMemory() {
                return false;
            }
            @Override
            public long getSize() {
                return 0;
            }
            @Override
            public byte[] get() {
                return new byte[0];
            }
            @Override
            public String getString(String s) throws UnsupportedEncodingException {
                return null;
            }
            @Override
            public String getString() {
                return null;
            }
            @Override
            public void write(File file) throws Exception {

            }
            @Override
            public void delete() {

            }
            @Override
            public String getFieldName() {
                return null;
            }
            @Override
            public void setFieldName(String s) {

            }
            @Override
            public boolean isFormField() {
                return false;
            }
            @Override
            public void setFormField(boolean b) {

            }
            @Override
            public OutputStream getOutputStream() throws IOException {
                return null;
            }
            @Override
            public FileItemHeaders getHeaders() {
                return null;
            }
            @Override
            public void setHeaders(FileItemHeaders fileItemHeaders) {

            }
        });
        albumInfo.setFileData(commonsMultipartFile);

        Genre genre = new Genre();
        genre.setID(1);
        genre.setName("genre1");
        genre.setDescription("des1");
        doReturn(genre).when(genreDAOMock).findGenre(1);

        Artist artist = new Artist();
        artist.setID(1);
        artist.setName("artist1");
        doReturn(artist).when(artistDAOMock).findArtist(1);

        doNothing().when(albumDAOMock).save(albumInfo);

        Assert.assertEquals(albumInfo, albumServiceImpl.addAlbum(albumInfo));

    }

    private MultipartFile getMockCommonsMultipartFile(String name, String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        MultipartFile multipartFile = new MockMultipartFile(name, name, "", is);
        return multipartFile;
    }

}
