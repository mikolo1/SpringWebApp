package mikolo.springWebApp.configure;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebMvcConfig {//implements WebMvcConfigurer{

	@Bean
	public BCryptPasswordEncoder bcp() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MultipartConfigElement configElement() {
		MultipartConfigFactory mFactory = new MultipartConfigFactory();
		DataSize maxFileSize = DataSize.ofMegabytes(3);
		mFactory.setMaxFileSize(maxFileSize);
		mFactory.setMaxRequestSize(maxFileSize);
		return mFactory.createMultipartConfig();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
