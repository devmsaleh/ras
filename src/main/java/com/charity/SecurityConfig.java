package com.charity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.DelegatingRequestMatcherHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.charity.enums.RoleTypeEnum;

@Configuration
@EnableWebSecurity(debug = false)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		applySecurity(http);
		http.csrf().disable();

		// cache resources
		http.headers().addHeaderWriter(new DelegatingRequestMatcherHeaderWriter(
				new AntPathRequestMatcher("/javax.faces.resource/**"), new HeaderWriter() {

					@Override
					public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
						response.addHeader("Cache-Control", "private, max-age=86400");
					}
				})).defaultsDisabled();
	}

	private void applySecurity(HttpSecurity http) throws Exception {
		// removed "/faces/*"
		http.authorizeRequests()
				.antMatchers("/", "/404.xhtml", "/error.xhtml", "/resources/**", "/saas/**", "/WEB-INF/**",
						"/template/**", "/faces/fonts/*", "/javax.faces.resource/**", "/login.xhtml",
						"/accessDenied.xhtml", "/login", "/error", "/api/**", "/v2/api-docs", "/configuration/ui",
						"/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
				.permitAll().antMatchers("/coupons.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_COUPONS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/delegates.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_DELEGATES.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/employees.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_EMPLOYEES.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/createReceipt.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_CREATE_MANUAL_RECEIPT.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/settings.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_SETTINGS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/lookupSettings.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_SETTINGS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/manageAccountants.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_SETTINGS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/manageBranches.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_MANAGE_SETTINGS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/collection.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_COLLECT_MONEY.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/financeDeposit.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_DEPOSIT_FINANCE.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/delegatesIncomeTotal.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_REPORT_DELEGATES_INCOME.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.antMatchers("/benefactorsReport.xhtml")
				.hasAnyAuthority(RoleTypeEnum.ROLE_REPORT_BENEFACTORS.getName(), RoleTypeEnum.ROLE_ADMIN.getName())
				.anyRequest().fullyAuthenticated().and().formLogin().loginPage("/login").loginProcessingUrl("/login")
				.permitAll().successHandler(successHandler).failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout")
				.deleteCookies("remember-me").logoutSuccessUrl("/").permitAll().and().exceptionHandling()
				.accessDeniedPage("/accessDenied.xhtml").and().rememberMe();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

}