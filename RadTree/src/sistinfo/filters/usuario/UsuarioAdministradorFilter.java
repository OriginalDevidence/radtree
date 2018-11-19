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
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;

public class UsuarioAdministradorFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Redirige a la página de inicio de sesión a los usuarios no logueados o que no sean administradores
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Comprobar si ya hay un usuario como atributo
			UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
			if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			}
			filterChain.doFilter(request, response);
		}
	}

}
