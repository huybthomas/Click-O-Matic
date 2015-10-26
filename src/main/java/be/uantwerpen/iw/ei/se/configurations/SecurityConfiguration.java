package be.uantwerpen.iw.ei.se.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Thomas on 22/10/2015.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //Permit access to H2 console --Development only (Order of defining is important!)
        http.authorizeRequests().antMatchers("/h2console/**")
                                    .permitAll();

        http.authorizeRequests().antMatchers("/login")
                                    .permitAll()
                                    .anyRequest()
                                    .fullyAuthenticated()
                                    .and()
                                .formLogin()
                                    .loginPage("/login")
                                    .failureUrl("/login?error")
                                    .and()
                                .logout()
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .and()
                                .exceptionHandling()
                                    .accessDeniedPage("/access?accessdenied");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
