package com.pigmy.demospringboot.serviceTest;

import com.pigmy.demospringboot.dao.GenreDAO;
import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.impl.GenreServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceTest {
    @InjectMocks
    GenreServiceImpl genreServiceImpl;
    @Mock
    GenreDAO genreDAOMock;

    private GenreInfo expectedGenre;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        expectedGenre = new GenreInfo(1,"genre", "description");
    }

    @Test
    public void testGetListGenre(){
        List<GenreInfo> expectedGenreList = asList(
                new GenreInfo(1, "genre1", "description1"),
                new GenreInfo(2, "genre2", "description2"));
        Mockito.when(genreDAOMock.getListGenre()).thenReturn(expectedGenreList);

        List<GenreInfo> genreList = genreServiceImpl.getListGenre();
        Assert.assertEquals(2, genreList.size());

        Assert.assertEquals(1, genreList.get(0).getID());
        Assert.assertEquals("genre1", genreList.get(0).getName());
        Assert.assertEquals("description1", genreList.get(0).getDescription());

        Assert.assertEquals(2, genreList.get(1).getID());
        Assert.assertEquals("genre2", genreList.get(1).getName());
        Assert.assertEquals("description2", genreList.get(1).getDescription());
    }

    @Test
    public void testAddGenre(){
        doNothing().when(genreDAOMock).save(expectedGenre);
        GenreInfo genreInfo = genreServiceImpl.addGenre(expectedGenre);

        Assert.assertEquals(expectedGenre.getID(), genreInfo.getID());
        Assert.assertEquals(expectedGenre.getName(), genreInfo.getName());
        Assert.assertEquals(expectedGenre.getDescription(), genreInfo.getDescription());
    }

    @Test
    public void testAdGenre_whenSaveErrorThenReturnNull(){
        doThrow(Exception.class).when(genreDAOMock).save(expectedGenre);
        GenreInfo genreInfo = genreServiceImpl.addGenre(expectedGenre);

        Assert.assertEquals(null, genreInfo);
    }
}
