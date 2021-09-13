package in.anand.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(datasource)
		.usersByUsernameQuery("select user_name,user_password,user_enabled from user where user_name= ?")
		.authoritiesByUsernameQuery("select user_name,user_role from user where user_name= ?").passwordEncoder(encode);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
								http.authorizeRequests().antMatchers("/home").permitAll()
								.antMatchers("/welcome").authenticated()
								.antMatchers("/admin").hasAuthority("ADMIN")
								.antMatchers("/employee").hasAuthority("EMPLOYEE")
								.antMatchers("/manager").hasAuthority("MANAGER")
								.antMatchers("/common").hasAnyAuthority("EMPLOYEE","ADMIN")
								
								.anyRequest().authenticated()
								
								.and()
								.formLogin()
								.defaultSuccessUrl("/welcome",true)
								
								.and()
								.logout()
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								
								.and()
								.exceptionHandling()
								.accessDeniedPage("/accessDenied");

								
	}
}
