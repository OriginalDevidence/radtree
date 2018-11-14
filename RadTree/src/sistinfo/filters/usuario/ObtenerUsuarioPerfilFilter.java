package sistinfo.filters.usuario;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;

public class ObtenerUsuarioPerfilFilter implements Filter {

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
			if (request.getAttribute("usuario") == null) {
				
				// Si no, buscar el usuario pasado como parámetro
				String alias = (String)request.getParameter("alias");
				if (alias != null && !alias.trim().isEmpty()) {
					// Mostrar el usuario pasado por la request
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					try {
						UsuarioVO usuario = usuarioDAO.getUsuarioByAlias(alias);
						if (usuario == null) {
				            response.sendRedirect("error");
						} else {
							System.out.println("cargado usuario");
							RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
							request.setAttribute("usuario", usuario);
							req.include(request, response);
						}
					} catch (ErrorInternoException e) {
			            response.sendRedirect("error");
					}
					
				// Si no hay alias en parámetro y hay un usuario logueado, mostrar ese usuario
				} else if (request.getSession().getAttribute("usuario") != null) {
					// No ha pasado ningun parámetro por request, mostrar su perfil por defecto
					UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
					request.setAttribute("usuario", usuario);
					response.sendRedirect("perfil?alias=" + usuario.getAlias());

				// No sabemos qué usuario mostrar
				} else {
			        response.sendRedirect("error");
				}
			}
			filterChain.doFilter(request, response);
		}
	}

}
