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
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;

public class BusquedaUsuariosFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Devuelve una lista de usuarios dada una búsqueda
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Comprobar si ya hay un usuario como atributo
			String busqueda = request.getParameter("busqueda");
			if (busqueda != null && !busqueda.trim().isEmpty()) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				try {
					List<UsuarioVO> usuarios = usuarioDAO.getUsuariosBySearch(busqueda, 30);
					request.setAttribute("usuarios", usuarios);
					filterChain.doFilter(request, response);
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
