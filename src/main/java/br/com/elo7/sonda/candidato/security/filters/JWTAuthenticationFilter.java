package br.com.elo7.sonda.candidato.security.filters;

import br.com.elo7.sonda.candidato.domains.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static br.com.elo7.sonda.candidato.security.service.TokenAuthenticationService.getAuthentication;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        getService(request);
        Authentication authentication = getAuthentication((HttpServletRequest) request, userService);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private void getService(ServletRequest request) {
        if (userService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(UserService.class);
        }
    }
}