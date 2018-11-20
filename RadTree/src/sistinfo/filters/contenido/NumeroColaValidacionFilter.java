package sistinfo.filters.contenido;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.excepciones.ErrorInternoException;

public class NumeroColaValidacionFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Coloca en la request el número de elementos que hay en la cola de validación, en un atributo "numInValidacion"
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			ContenidoDAO contenidoDAO = new ContenidoDAO();
			try {
				int numColaValidacion = contenidoDAO.getNumContenidosInColaValidacion();
				request.setAttribute("numInValidacion", numColaValidacion);
				filterChain.doFilter(request, response);
			} catch (ErrorInternoException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
