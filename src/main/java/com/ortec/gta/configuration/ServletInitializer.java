package com.ortec.gta.configuration;

import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.database.model.dto.RoleDTO;
import com.ortec.gta.service.UserRoleService;
import com.ortec.gta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Application Initializer, it avoids dirty xml configurations.
 * Enjoy this full java configuration.
 */
public class ServletInitializer extends AbstractDispatcherServletInitializer {

	/**
	 * Defining the application context. It will check this class package and then
	 * load all classes annotated with @Configuration
	 */
	@Override
	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.scan(ClassUtils.getPackageName(getClass()));
		return context;
	}

	/**
	 * Define the base of all mappings
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		return null;
	}

	/**
	 * Setup the filter. Header tokens and roles will then be functional.
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
		DelegatingFilterProxy other = new DelegatingFilterProxy("corsFilter");
		DelegatingFilterProxy roles = new DelegatingFilterProxy("roleFilter");

		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter("corsFilter", other).addMappingForUrlPatterns(null, false, "/*");
		servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
		servletContext.addFilter("roleFilter", roles).addMappingForUrlPatterns(null, false, "/*");
		super.onStartup(servletContext);
	}

	@Component("roleFilter")
	protected static class RoleFilter implements Filter {
		@Autowired
		UserRoleService roleService;

		/**
		 * On each request, we check if the user's database roles matches with the session roles.
		 * If the user is down or up grade, we force him to re-logging by removing his token.
		 */
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			roleService.refreshRoles();
			chain.doFilter(request, response);
		}

		@Override
		public void init(FilterConfig filterConfig) {}

		@Override
		public void destroy() {}
	}

	/**
	 * A CorsFilter to enable all method types from everywhere (such as localhost)
	 */
	@Component("corsFilter")
	@Order(-1)
	protected static class CorsFilter implements Filter {

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			HttpServletResponse response = (HttpServletResponse) res;
			HttpServletRequest request = (HttpServletRequest) req;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD, PUT, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

			if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
				response.setStatus(HttpServletResponse.SC_OK);
			else
				chain.doFilter(req, res);
		}

		@Override
		public void init(FilterConfig filterConfig) {}

		@Override
		public void destroy() {}
	}
}
