package br.com.cellenti.site.web.util;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class OptionsFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if ("OPTIONS".equals(request.getMethod())) {
                        this.configCorsResponse(response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
        
        public void configCorsResponse(HttpServletResponse response){
                response.setStatus(200);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.addHeader("Access-Control-Allow-Headers", "If-Modified-Since, Cache-Control, Pragma, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
                response.addHeader("Access-Control-Max-Age", "3600");
        }
}