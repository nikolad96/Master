package com.example.naucnacentrala.controller;

import com.example.naucnacentrala.dto.UserInfoDTO;
import com.example.naucnacentrala.model.Korisnik;
import com.example.naucnacentrala.model.Role;
import com.example.naucnacentrala.model.UserTokenState;
import com.example.naucnacentrala.security.CustomUserDetailsService;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.security.auth.JwtAuthenticationRequest;
import com.example.naucnacentrala.service.KorisnikService;
import com.example.naucnacentrala.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    public TokenUtils tokenUtils;

    @Autowired
    public AuthenticationManager manager;

    @Autowired
    public CustomUserDetailsService userDetailsService;

    @Autowired
    public KorisnikService korisnikService;

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> loginUser(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response, Device device, HttpServletRequest hr){
//
//        if(!inputValid(authenticationRequest.getUsername())) {
//            return new ResponseEntity<>(new UserTokenState("error",0), HttpStatus.NOT_FOUND);
//        }
        System.out.println("usao u metodu auth/login");
        final Authentication authentication = manager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
//                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//
//        for (GrantedAuthority authority : authorities) {
//            System.out.println("Authority: " + authority.getAuthority());
//        }

        Korisnik user =  (Korisnik) authentication.getPrincipal();
        // VRATI DRUGI STATUS KOD
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt = tokenUtils.generateToken(user.getUsername(), device);
        int expiresIn = 3600;

        return ResponseEntity.ok(new UserTokenState(jwt,expiresIn));
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> getUser(HttpServletRequest request){
        System.out.println("usao u metodu auth/getUser");
        String username = Utils.getUsernameFromRequest(request, tokenUtils);
//        System.out.println("username u getUser: " + username);
        UserInfoDTO ui = new UserInfoDTO();
        if(username != "" && username != null) {
            Korisnik u = (Korisnik) korisnikService.findOneByUsername(username);
//
            for(Role role: u.getRoles()){
                if(role.getName().equals("ROLE_ADMIN")){
                    ui.setRole("ADMIN");
                    break;
                }else if(role.getName().equals("ROLE_UREDNIK")){
                    ui.setRole("UREDNIK");
                    break;
                }else if(role.getName().equals("ROLE_RECENZENT")){
                    ui.setRole("RECENZENT");
                    break;
                }
            }
            ui.setUsername(u.getUsername());
            return new ResponseEntity<UserInfoDTO>(ui, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
