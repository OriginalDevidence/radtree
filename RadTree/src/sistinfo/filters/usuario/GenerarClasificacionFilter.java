package sistinfo.filters.usuario;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.ClasificacionVO;
import sistinfo.excepciones.ErrorInternoException;

public class GenerarClasificacionFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Obtiene los datos de los 10 primeros usuarios de la clasificación y los incluye en la request, en el atributo clasificacion
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Obtener datos clasificación
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				List<ClasificacionVO> clasificacion = usuarioDAO.getClasificacion(10);
				request.setAttribute("clasificacion", clasificacion);
				filterChain.doFilter(request, response);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
