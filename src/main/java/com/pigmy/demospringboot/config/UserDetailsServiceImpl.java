package com.pigmy.demospringboot.config;

import java.util.ArrayList;
import java.util.List;

import com.pigmy.demospringboot.dao.AccountDAO;
import com.pigmy.demospringboot.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AccountDAO accountDAO;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDAO.findAccount(username);
        System.out.println("Account= " + account);
 
        if (account == null) {
            System.out.println("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " //
                    + username + " was not found in the database");
        }
 
        // EMPLOYEE,CUSTOMER,..
        String role = account.getUserRole().getName();
        System.out.println("user role: "+ role);
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
 
        // ROLE_CUSTOMER, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
 
        grantList.add(authority);
 
        boolean enabled = account.isActive();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
 
        UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
                account.getPassword(), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, grantList);
 
        return userDetails;
    }
 
}