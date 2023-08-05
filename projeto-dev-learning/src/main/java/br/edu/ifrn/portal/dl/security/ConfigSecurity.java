package br.edu.ifrn.portal.dl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.edu.ifrn.portal.dl.services.GerenciadorService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private GerenciadorService gerenciadorService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf() 
			.disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.GET, "/error").permitAll() 
			.antMatchers("/admin").hasAnyRole("ESCRITOR", "ADMIN", "ADMIN_MASTER")
			.antMatchers("/admin/disciplinas/**").hasAnyRole("ADMIN", "ADMIN_MASTER")
			.anyRequest().authenticated()
			.and().formLogin().permitAll().defaultSuccessUrl("/admin")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(gerenciadorService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override //Ignora algum recurso
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
}
