package com.example.BachelorProefBackend.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration //recognised by Spring as settings
@EnableWebSecurity
@RequiredArgsConstructor //Automatically provides constructor for userDetailService
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Where Spring has to look for users
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter((authenticationManagerBean()));
        customAuthenticationFilter.setFilterProcessesUrl("/authentication/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.cors().and(); //Reference to bean in main
        http.addFilterBefore(new CustomAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);


        // Everyone has access
        http.authorizeRequests().antMatchers("/authentication/login/**").permitAll(); //login
        http.authorizeRequests().antMatchers("/authentication/token/refresh/**").permitAll(); //refresh
        http.authorizeRequests().antMatchers(POST, "/userManagement/users").permitAll(); //register

        http.authorizeRequests().antMatchers("/authentication/isRole/{role}").authenticated();
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects/**").authenticated();
        http.authorizeRequests().antMatchers(POST, "/subjectManagement/subjects/**").authenticated();
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addTag").authenticated();
        http.authorizeRequests().antMatchers(GET, "/userManagement/company/{companyId}/subjects").authenticated();
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/targetAudience/**").authenticated();
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/tag/**").authenticated();
        http.authorizeRequests().antMatchers(POST, "/subjectManagement/tag/**").authenticated();


        // Access restricted
        http.authorizeRequests().antMatchers(DELETE, "/subjectManagement/subjects/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR");
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR");
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addCompany").hasAnyAuthority("ROLE_ADMIN", "ROLE_CONTACT");
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addPromotor").hasAnyAuthority("ROLE_ADMIN", "ROLE_PROMOTOR", "ROLE_STUDENT", "ROLE_COORDINATOR");

        http.authorizeRequests().antMatchers("/subjectManagement/campus/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/subjectManagement/faculty/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/subjectManagement/education/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/subjectManagement/targetAudience/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/targetAudience/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/subjectManagement/targetAudience/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/subjectManagement/tag/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/tag/**").hasAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers(GET, "/userManagement/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR", "ROLE_PROMOTOR");
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/student").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR", "ROLE_PROMOTOR");
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/administrator").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR", "ROLE_PROMOTOR");
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/promotor").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR", "ROLE_PROMOTOR");
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/coordinator").hasAnyAuthority("ROLE_ADMIN", "ROLE_COORDINATOR", "ROLE_PROMOTOR");
        http.authorizeRequests().antMatchers(DELETE, "/userManagement/users/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/userManagement/users/student/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_STUDENT");
        http.authorizeRequests().antMatchers(PUT, "/userManagement/users/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/userManagement/users/**").hasAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers("/userManagement/company/{companyId}/approve").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/userManagement/company/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CONTACT");

        http.addFilter(customAuthenticationFilter);

    }









}
