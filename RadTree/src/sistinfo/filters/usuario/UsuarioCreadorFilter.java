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

public class UsuarioCreadorFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Comprobar si ya hay un usuario como atributo
			UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
			if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			}
			filterChain.doFilter(request, response);
		}
	}

}
