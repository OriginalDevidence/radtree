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

import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

public class CargarNoticiaFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Carga los datos de un reto y lo almacena en el atributo reto,
	 * además almacena información sobre su autor en los atributos autorAlias y autorCompleto.
	 * También guarda información sobre el redirect y el id de contenido (necesario para los comentarios)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
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
					response.sendRedirect(request.getContextPath() + "/noticias");
				}
			}
			if (idContenido != null && idContenido > 0L) {
				// Atributos para los comentarios
				request.setAttribute("redirect", "noticias/ver");
				request.setAttribute("id", idContenido);
				// Cargar el reto con ese ID y el usuario autor
				NoticiaDAO noticiaDAO = new NoticiaDAO();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				try {
					NoticiaVO noticia = noticiaDAO.getNoticiaById(idContenido);
					if (noticia == null) {
						// El contenido no existe (o no debería ser mostrado)
			            response.sendRedirect(request.getContextPath() + "/noticias");
					} else {
						UsuarioVO usuario = usuarioDAO.getUsuarioById(noticia.getIdAutor());
						if (usuario == null) {
				            response.sendRedirect(request.getContextPath() + "/error-interno");
						} else {
							request.setAttribute("noticia", noticia);
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