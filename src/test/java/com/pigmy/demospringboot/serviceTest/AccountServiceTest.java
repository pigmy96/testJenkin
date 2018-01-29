package com.pigmy.demospringboot.serviceTest;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import com.pigmy.demospringboot.entity.Role;
import com.pigmy.demospringboot.model.AccountInfo;
import com.pigmy.demospringboot.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @InjectMocks
    AccountServiceImpl accountServiceImpl;
    @Mock
    AccountDAO accountDAOMock;

    @Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsLogin_whenNotLogInThenReturnFalse(){
        Assert.assertEquals(false, accountServiceImpl.isLogin());
    }

    @Test
    @WithMockUser
    public void testIsLogin_whenLoggedInThenReturnTrue(){
        Assert.assertEquals(true, accountServiceImpl.isLogin());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetInfoUserLoggedIn(){
        Account expectedAccount = new Account();
        expectedAccount.setUserName("admin");
        expectedAccount.setPassword("admin");
        expectedAccount.setActive(true);
        expectedAccount.setEmail("123@abc.com");
        Role role = new Role();
        role.setID(1);
        role.setName("ADMIN");
        expectedAccount.setUserRole(role);

        doReturn(expectedAccount).when(accountDAOMock).findAccount("admin");

        AccountInfo accountInfo = accountServiceImpl.getInfoUserLoggedIn();

        Assert.assertEquals(expectedAccount.getUserName(), accountInfo.getUsername());
        Assert.assertEquals(expectedAccount.getPassword(), accountInfo.getPass());
        Assert.assertEquals(expectedAccount.getEmail(), accountInfo.getEmail());
        Assert.assertEquals(expectedAccount.getUserRole().getName(), accountInfo.getRole());
    }

    @Test
    public void testRegister_whenSuccessThenReturnAccountInfo(){
        AccountInfo expectedAccount = new AccountInfo();
        expectedAccount.setEmail("123@abc.com");
        expectedAccount.setPassword("1234");
        expectedAccount.setUsername("admin");
        doNothing().when(accountDAOMock).save(expectedAccount);

        Assert.assertEquals(expectedAccount, accountServiceImpl.register(expectedAccount));
    }

    @Test
    public void testRegister_whenSaveErrorThenReturnNull(){
        AccountInfo expectedAccount = new AccountInfo();
        expectedAccount.setEmail("123@abc.com");
        expectedAccount.setPassword("1234");
        expectedAccount.setUsername("admin");
        doThrow(Exception.class).when(accountDAOMock).save(expectedAccount);

        Assert.assertEquals(null, accountServiceImpl.register(expectedAccount));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetUserLoggedIn(){
        Account expectedAccount = new Account();
        expectedAccount.setUserName("admin");
        expectedAccount.setPassword("admin");
        expectedAccount.setActive(true);
        expectedAccount.setEmail("123@abc.com");

        Role role = new Role();
        role.setID(1);
        role.setName("ADMIN");
        expectedAccount.setUserRole(role);

        doReturn(expectedAccount).when(accountDAOMock).findAccount("admin");

        Account account = accountServiceImpl.getUserLoggedIn();

        Assert.assertEquals(expectedAccount.getUserName(), account.getUserName());
        Assert.assertEquals(expectedAccount.getPassword(), account.getPassword());
        Assert.assertEquals(expectedAccount.getEmail(), account.getEmail());
        Assert.assertEquals(expectedAccount.getUserRole().getName(), account.getUserRole().getName());
    }
}
