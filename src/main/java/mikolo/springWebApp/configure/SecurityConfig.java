package mikolo.springWebApp.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)		//włączenie adnotacji @Secured
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private BCryptPasswordEncoder bcp;
	
	private DataSource ds;
	
	public SecurityConfig(BCryptPasswordEncoder bcp, DataSource ds) {
		this.bcp = bcp;
		this.ds = ds;
	}
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	protected void configure(AuthenticationManagerBuilder amb) throws Exception {
		amb
		.jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.dataSource(ds)
		.passwordEncoder(bcp);
	}
	
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/adduser").permitAll()
		.antMatchers("/activationlink/**").permitAll()//udostępnienie niezalogowanemu użytkownikowi wyświetlenie strony przy potwierdzaniu rejestracji
//		.antMatchers("/admin").hasAuthority("ROLE_ADMIN")  //teraz rolę tego zabezpieczenia przejmuje adnotacja @Secured w kontrolerze
		.anyRequest().authenticated()
		.and().csrf().disable()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/").usernameParameter("email")	//określa unikalny element identyfikujący oraz na jaką strone przenieść po zalogowaniu
		.passwordParameter("password")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.and().exceptionHandling().accessDeniedPage("/denied");
	}
	
	public void configure(WebSecurity ws) throws Exception{
		ws.ignoring()
		.antMatchers("/resources/**", "/statics/**", "/css/**", "/js/**", "/images/**", "/incl/**");
	}

}
