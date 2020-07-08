package com.charity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.charity.service.UserTokenService;

@Component
public class WebServiceCustomSecurityFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(WebServiceCustomSecurityFilter.class);

	List<String> publicUrls = Arrays.asList("/login", "/swagger.json", "/getCouponImage", "getNewProjectTypeImage",
			"getNewProjectCountryImage", "getSponsorshipCountryImage", "getOrphanImage", "refreshApplicationCache",
			"getSponsorshipTypeImage");

	@Autowired
	private UserTokenService userTokenService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// logger.info("######### WebServiceCustomSecurityFilter > " +
		// request.getRequestURI());
		if (isAllowedUrl(request.getRequestURI())) {
			// logger.info("######### WebServiceCustomSecurityFilter > login service >
			// chain.doFilter");
			chain.doFilter(servletRequest, servletResponse);
		} else {
			// logger.info("######### WebServiceCustomSecurityFilter > token check,token: "
			// + request.getHeader("token"));
			if (userTokenService.isValidToken(request.getHeader("token"))) {
				// logger.info("######### WebServiceCustomSecurityFilter > valid token ");
				userTokenService.updateTokenExpirationDate(request.getHeader("token"));
				chain.doFilter(servletRequest, servletResponse);
			} else {
				// logger.info("######### WebServiceCustomSecurityFilter > invalid token ");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
			}
		}

	}

	private boolean isAllowedUrl(String requestUrl) {
		for (String url : publicUrls) {
			if (requestUrl.toLowerCase().contains(url.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
