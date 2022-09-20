package com.globallogic.creditcardpayment.servicesImpl;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.JwtRequest;
import com.globallogic.creditcardpayment.entity.JwtResponse;
import com.globallogic.creditcardpayment.entity.Users;
import com.globallogic.creditcardpayment.repositories.UserDao;
import com.globallogic.creditcardpayment.util.JwtUtil;



@Service
public class JwtServiceImpl implements UserDetailsService {
     
	 @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private UserDao userDao;
	    
	    @Autowired
	    private AuthenticationManager authenticationManager;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		 Users user = userDao.findById(username).get();

	        if (user != null) {
	            return new org.springframework.security.core.userdetails.User(
	                    user.getUserName(),
	                    user.getUserPassword(),
	                    getAuthority(user)
	            );
	        } else {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	    }
	private Set getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        });
        return authorities;
    }


	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		// TODO Auto-generated method stub
		 String userName = jwtRequest.getUserName();
	        String userPassword = jwtRequest.getUserPassword();
	        authenticate(userName, userPassword);

	        UserDetails userDetails = loadUserByUsername(userName);
	        String newGeneratedToken = jwtUtil.generateToken(userDetails);

	        Users user = userDao.findById(userName).get();
	        return new JwtResponse(user, newGeneratedToken);
	}

	private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

