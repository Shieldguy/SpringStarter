/**
 * 
 */
package org.springstarter.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springstarter.auth.SvcUserDetailsService;

/**
 * <p>
 * Customized security configuration class for web service. 
 * </p>
 * @FileName    : UserAuthenticationConfig.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Configuration
public class ServiceSecurityConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSecurityConfig.class);
	
	/**
	 * <p>
	 * In this class, set the user service for authentication and authorization.
	 * And set the access control, csrf, login and logout information.
	 * </p>
	 */
	@EnableWebSecurity
	protected static class SecurityConfig extends WebSecurityConfigurerAdapter {
		
		DataSource dataSource;
		private SvcUserDetailsService svcUserDetailsService;
		
		/**
		 * @param dataSource the dataSource to set
		 */
		@Autowired
		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
LOGGER.info("[DEBUG] dataSource ({}) was assigned at SecurityConfig", dataSource);
		}

		/**
		 * @param svcUserDetailsService the svcUserDetailsService to set
		 */
		@Autowired
		public void setSvcUserDetailsService(SvcUserDetailsService svcUserDetailsService) {
			this.svcUserDetailsService = svcUserDetailsService;
LOGGER.info("[DEBUG] svcUserDetailsService ({}) was assigned at SecurityConfig", svcUserDetailsService);
		}

		/**
		 * <p>
		 * Set the user service
		 * </p>
		 * @param auth
		 * @throws Exception
		 */
	 	@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(svcUserDetailsService);
		}
		
	 	/**
	 	 * Set the access control, csrf, login path info. and login info.
	 	 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {	
			http
				.authorizeRequests()                                                            
					.antMatchers("/", "/login", "/signin**", "/welcome", "/confirm/**", "/chkacnt", "/chkid", "/img/**",
							"/signup**", "/signup", "/resources/**", "/error/**")
							.permitAll()
					.antMatchers(HttpMethod.POST, "/user").permitAll()
					.anyRequest().authenticated()
					.and()
			  	.csrf()		
			  		.and()
			  	.formLogin()	// in this project, we create customized login page
			  		.loginPage("/signin")
			  		.defaultSuccessUrl("/")
			  		.failureUrl("/signin?authentication_error=true")
			  		.permitAll()
			  		.and()
			  	.logout()	
	            	.logoutSuccessUrl("/")
					.permitAll();
				//.httpBasic(); 
		}
	}

}
