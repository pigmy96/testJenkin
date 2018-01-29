package com.pigmy.demospringboot.serviceTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.service.impl.ArtistServiceImpl;
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
public class ArtistServiceTest {
    @InjectMocks
    ArtistServiceImpl artistServiceImpl;
    @Mock
    ArtistDAO artistDAOMock;

    private ArtistInfo expectedArtist;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        expectedArtist = new ArtistInfo(1,"artist");
    }

    @Test
    public void testGetListArtist(){
        List<ArtistInfo> expectedArtistList = asList(
                new ArtistInfo(1, "artist1"),
                new ArtistInfo(2, "artist2"));
        Mockito.when(artistDAOMock.getListArtist()).thenReturn(expectedArtistList);

        List<ArtistInfo> artistList = artistServiceImpl.getListArtist();
        Assert.assertEquals(2, artistList.size());

        Assert.assertEquals(1, artistList.get(0).getID());
        Assert.assertEquals("artist1", artistList.get(0).getName());

        Assert.assertEquals(2, artistList.get(1).getID());
        Assert.assertEquals("artist2", artistList.get(1).getName());
    }

    @Test
    public void testAddArtist(){
        doNothing().when(artistDAOMock).save(expectedArtist);
        ArtistInfo artistInfo = artistServiceImpl.addArtist(expectedArtist);

        Assert.assertEquals(expectedArtist.getID(), artistInfo.getID());
        Assert.assertEquals(expectedArtist.getName(), artistInfo.getName());
    }

    @Test
    public void testAddArtist_SaveErrorThenReturnNull(){
        doThrow(Exception.class).when(artistDAOMock).save(expectedArtist);
        ArtistInfo artistInfo = artistServiceImpl.addArtist(expectedArtist);

        Assert.assertEquals(null, artistInfo);
    }
}
