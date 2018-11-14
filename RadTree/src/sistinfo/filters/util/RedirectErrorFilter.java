package sistinfo.filters.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class RedirectErrorFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletResponse instanceof HttpServletResponse) {
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			response.sendRedirect("error");
			filterChain.doFilter(servletRequest, response);
		}
	}

}
