package in.anand.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class APPConfig {
	
@Bean	
public BCryptPasswordEncoder encode()
{
	return new BCryptPasswordEncoder();
	
}
	

}
