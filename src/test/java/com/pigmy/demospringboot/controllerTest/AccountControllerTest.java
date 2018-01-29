package com.pigmy.demospringboot.controllerTest;

import com.pigmy.demospringboot.controller.AccountController;
import com.pigmy.demospringboot.controller.AlbumController;
import com.pigmy.demospringboot.model.AccountInfo;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.ArtistInfo;
import com.pigmy.demospringboot.model.GenreInfo;
import com.pigmy.demospringboot.service.AccountService;
import com.pigmy.demospringboot.service.AlbumService;
import com.pigmy.demospringboot.service.ArtistService;
import com.pigmy.demospringboot.service.GenreService;
import com.pigmy.demospringboot.session.CartSession;
import com.pigmy.demospringboot.validator.AlbumInfoValidator;
import com.pigmy.demospringboot.validator.RegisterValidator;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {
    @InjectMocks
    AccountController accountController;

    @Mock
    AlbumService albumServiceMock;
    @Mock
    GenreService genreServiceMock;
    @Mock
    CartSession cartSessionMock;
    @Mock
    ArtistService artistServiceMock;
    @Mock
    AccountService accountServiceMock;
    @Mock
    RegisterValidator registerValidatorMock;

    MockHttpServletRequest requestMock;
    @Autowired
    AlbumInfoValidator albumInfoValidator;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(accountController)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        when(registerValidatorMock.supports(AccountInfo.class)).thenReturn(true);
    }

    @Test
    public void testAccessDenied403() throws Exception {
        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/403"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("accessDenied"));
    }

    @Test
    public void getListAlbumForHome() throws Exception {
        List<AlbumInfo> expectedAlbum1 = asList(new AlbumInfo());
        List<List<AlbumInfo>> expectedAlbum = asList(expectedAlbum1);
        Mockito.doReturn(expectedAlbum).when(albumServiceMock).getListAlbumForHome();

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("albumList", expectedAlbum))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("index"));
    }

    @Test
    public void testLoginGet_LoggedIn() throws Exception {
        when(accountServiceMock.isLogin()).thenReturn(true);
        mockMvc.perform(get("/login"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginGet_NotLoggedIn() throws Exception {
        when(accountServiceMock.isLogin()).thenReturn(false);

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("login"));

    }

    @Test
    public void testAccountInfo_CheckedOut() throws Exception {
        when(cartSessionMock.getCheckOut(Mockito.anyObject())).thenReturn(true);

        mockMvc.perform(get("/accountInfo"))
                .andExpect(redirectedUrl("/cart/checkout"));
    }

    @Test
    public void testAccountInfo_NotCheckedOut() throws Exception {
        when(cartSessionMock.getCheckOut(Mockito.anyObject())).thenReturn(false);

        AccountInfo accountInfo = new AccountInfo("admin", "admin", true, "ADMIN", "admin@gmail.com");
        doReturn(accountInfo).when(accountServiceMock).getInfoUserLoggedIn();

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/accountInfo"))
                .andExpect(model().attribute("accountInfo", hasProperty("username", is(accountInfo.getUsername()))))
                .andExpect(model().attribute("accountInfo", hasProperty("pass", is(accountInfo.getPass()))))
                .andExpect(model().attribute("accountInfo", hasProperty("email", is(accountInfo.getEmail()))))
                .andExpect(model().attribute("accountInfo", hasProperty("role", is(accountInfo.getRole()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("accountInfo"));
    }

    @Test
    public void testRegisterGet_LoggedIn() throws Exception {
        when(accountServiceMock.isLogin()).thenReturn(true);
        mockMvc.perform(get("/register"))
                .andExpect(redirectedUrl("/"));

    }

    @Test
    public void testRegisterGet_NotLoggedIn() throws Exception {
        when(accountServiceMock.isLogin()).thenReturn(false);
        AccountInfo expectedAccount = new AccountInfo();

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        int expectedTotal = 0;
        Mockito.doReturn(expectedTotal).when(cartSessionMock).getTotalQuantity(requestMock);

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("accountForm", hasProperty("username", is(expectedAccount.getUsername()))))
                .andExpect(model().attribute("accountForm", hasProperty("password", is(expectedAccount.getPassword()))))
                .andExpect(model().attribute("accountForm", hasProperty("email", is(expectedAccount.getEmail()))))
                .andExpect(model().attribute("accountForm", hasProperty("confirmPassword", is(expectedAccount.getConfirmPassword()))))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(model().attribute("totalQuantity", expectedTotal))
                .andExpect(view().name("register"));

    }

    @Test
    public void testRegisterPost() throws Exception {
        AccountInfo expectedAccount = new AccountInfo();
        expectedAccount.setEmail("123@abc.com");
        expectedAccount.setUsername("abc");
        expectedAccount.setPassword("1234");
        expectedAccount.setConfirmPassword("1234");
        when(accountServiceMock.register(isA(AccountInfo.class))).thenReturn(expectedAccount);
        mockMvc.perform(post("/register")
                .param("email", expectedAccount.getEmail())
                .param("username", expectedAccount.getUsername())
                .param("password",expectedAccount.getPassword())
                .param("confirmPassword",expectedAccount.getConfirmPassword())
                .flashAttr("accountForm", new AccountInfo()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(redirectedUrl("/login"));
        verify(accountServiceMock, times(1)).register(isA(AccountInfo.class));
        verifyNoMoreInteractions(accountServiceMock);
    }

    @Test
    public void testRegisterPost_SaveAccountError() throws Exception {
        when(accountServiceMock.register(isA(AccountInfo.class))).thenReturn(null);

        List<GenreInfo> expectedGenre = asList(new GenreInfo());
        Mockito.doReturn(expectedGenre).when(genreServiceMock).getListGenre();

        mockMvc.perform(post("/register"))
                .andExpect(model().attribute("genreList", expectedGenre))
                .andExpect(view().name("register"));
        verify(accountServiceMock, times(1)).register(isA(AccountInfo.class));
        verifyNoMoreInteractions(accountServiceMock);
    }

    @Test
    public void testRegisterPost_HasErrorForm() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Errors errors = (Errors) invocationOnMock.getArguments()[1];
                errors.reject("forcing some error");
                return null;
            }
        }).when(registerValidatorMock).validate(anyObject(), any(Errors.class));

        AccountInfo expectedAccount = new AccountInfo();
        expectedAccount.setEmail("123@abc.com");
        expectedAccount.setUsername("abc");
        expectedAccount.setPassword("1234");
        expectedAccount.setConfirmPassword("1234");

        mockMvc.perform(post("/register")
                .param("email", expectedAccount.getEmail())
                .param("username", expectedAccount.getUsername())
                .param("password",expectedAccount.getPassword())
                .param("confirmPassword",expectedAccount.getConfirmPassword())
                .flashAttr("accountForm", new AccountInfo()))
                .andExpect(view().name("register"));
    }

}
