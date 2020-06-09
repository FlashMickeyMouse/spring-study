package xin.kingsman.org;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xin.kingsman.org.bean.User;

@Configuration
public class AppConfig {

	@Bean
	public User user(){
		return new User();
	}
}
