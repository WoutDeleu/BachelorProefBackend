package com.example.bachelorproefbackend.authentication;

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

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String COORDINATOR = "ROLE_COORDINATOR";
    public static final String PROMOTOR = "ROLE_PROMOTOR";
    public static final String STUDENT = "ROLE_STUDENT";
    public static final String CONTACT = "ROLE_CONTACT";
    public static final String SUBJECTS = "/subjectManagement/subjects/**";
    public static final String TARGETAUDIENCE = "/subjectManagement/targetAudience/**";
    public static final String TAG = "/subjectManagement/tag/**";
    public static final String USERS = "/userManagement/users/**";




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
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects").authenticated();
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects/byTargetAudience").authenticated();
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects/{subjectId}").authenticated();
        http.authorizeRequests().antMatchers(POST, SUBJECTS).authenticated();
        http.authorizeRequests().antMatchers(GET, "userManagement/users/{id}").authenticated();
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addTag").authenticated();
        http.authorizeRequests().antMatchers(GET, "/userManagement/company/{companyId}/subjects").authenticated();
        http.authorizeRequests().antMatchers(GET, TARGETAUDIENCE).authenticated();
        http.authorizeRequests().antMatchers(GET, TAG).authenticated();
        http.authorizeRequests().antMatchers(POST, TAG).authenticated();


        // Access restricted
        http.authorizeRequests().antMatchers(DELETE, SUBJECTS).hasAnyAuthority(ADMIN, COORDINATOR);
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects/stats").hasAnyAuthority(ADMIN, COORDINATOR);
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}").hasAnyAuthority(ADMIN, COORDINATOR);
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addCompany").hasAnyAuthority(ADMIN, CONTACT);
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/addPromotor").hasAnyAuthority(ADMIN, PROMOTOR, STUDENT, COORDINATOR);
        http.authorizeRequests().antMatchers(PUT, "/subjectManagement/subjects/{subjectId}/approve").hasAnyAuthority(ADMIN, COORDINATOR);
        http.authorizeRequests().antMatchers(GET, "/subjectManagement/subjects/nonApproved").hasAnyAuthority(ADMIN, COORDINATOR);

        http.authorizeRequests().antMatchers("/subjectManagement/campus/**").hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers("/subjectManagement/faculty/**").hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers("/subjectManagement/education/**").hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, TARGETAUDIENCE).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(PUT, TARGETAUDIENCE).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(DELETE, TARGETAUDIENCE).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(DELETE, TAG).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(PUT, TAG).hasAuthority(ADMIN);

        http.authorizeRequests().antMatchers(GET, "/userManagement/users").hasAnyAuthority(ADMIN, COORDINATOR, PROMOTOR);
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/student").hasAnyAuthority(ADMIN, COORDINATOR, PROMOTOR);
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/administrator").hasAnyAuthority(ADMIN, COORDINATOR, PROMOTOR);
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/promotor").hasAnyAuthority(ADMIN, COORDINATOR, PROMOTOR);
        http.authorizeRequests().antMatchers(GET, "/userManagement/users/coordinator").hasAnyAuthority(ADMIN, COORDINATOR, PROMOTOR);
        http.authorizeRequests().antMatchers(DELETE, USERS).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, "/userManagement/users/student/**").hasAnyAuthority(ADMIN, STUDENT);
        http.authorizeRequests().antMatchers(PUT, "/userManagement/users/student/**").hasAnyAuthority(ADMIN, COORDINATOR);
        http.authorizeRequests().antMatchers(PUT, USERS).hasAuthority(ADMIN);
        http.authorizeRequests().antMatchers(POST, USERS).hasAuthority(ADMIN);

        http.authorizeRequests().antMatchers("/userManagement/company/{companyId}/approve").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers("/userManagement/company/**").hasAnyAuthority(ADMIN, CONTACT);

        http.addFilter(customAuthenticationFilter);

    }









}
