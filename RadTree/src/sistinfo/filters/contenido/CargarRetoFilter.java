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

import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.ContenidoVO.Estado;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

public class CargarRetoFilter implements Filter {

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
			
			// Encontrar un ID de contenido
			Long idContenido = RequestExtractor.getLong(request, "id");
			if (idContenido == null || idContenido <= 0L) {
				// Buscar en atributos (despues de postear comentario) en lugar de parametros
				idContenido = (Long)request.getAttribute("id");
				if (idContenido == null || idContenido <= 0L) {
					// No sabemos qué reto mostrar
					response.sendRedirect(request.getContextPath() + "/retos");
				}
			}
			if (idContenido != null && idContenido > 0L) {
				// Atributos para los comentarios
				request.setAttribute("redirect", "retos/ver");
				request.setAttribute("id", idContenido);
				// Cargar el reto con ese ID y el usuario autor
				RetoDAO retoDAO = new RetoDAO();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				try {
					RetoVO reto = retoDAO.getRetoById(idContenido);
					if (reto == null || reto.getEstado() != Estado.VALIDADO) {
						// El contenido no existe (o no debería ser mostrado)
			            response.sendRedirect(request.getContextPath() + "/retos");
					} else {
						UsuarioVO usuario = usuarioDAO.getUsuarioById(reto.getIdAutor());
						if (usuario == null) {
				            response.sendRedirect(request.getContextPath() + "/error-interno");
						} else {
							request.setAttribute("reto", reto);
							request.setAttribute("autorAlias", usuario.getAlias());
							request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
						}
					}
				} catch (ErrorInternoException e) {
		            response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			}
			filterChain.doFilter(request, response);
		}
	}

}