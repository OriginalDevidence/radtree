package sistinfo.filters.usuario;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;

public class RedirectPerfilUsuarioLogueadoFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Redirige a la página de su perfil a los usuarios que hayan iniciado sesión
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
			if (usuario != null) {
				// Forward a la página de perfil con el usuario
				response.sendRedirect(request.getContextPath() + "/perfil");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}

	}

}
