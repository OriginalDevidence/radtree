package sistinfo.filters.contenido;

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

import sistinfo.capadatos.dao.ContenidoDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

public class ElementoColaValidacionFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Obtiene datos del contenido a visualizar en la cola de validación actualmente
	 * Almacena los datos comunes a los 3 tipos (noticia, pregunta, reto) en el atributo contenido
	 * y los datos especificos a cada tipo en un atributo igual al nombre de su tipo
	 * También guarda información sobre su autor en los atributos alias y autorCompleto
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			Integer elemento = RequestExtractor.getInteger(request, "elemento");
			ContenidoDAO contenidoDAO = new ContenidoDAO();
			try {
				// Obtener ID elemento a mostrar
				List<Long> idsValidacion = contenidoDAO.getContenidosInColaValidacion();
				if (idsValidacion.size() > 0) {
					if (elemento == null || elemento < 1 || elemento > idsValidacion.size()) {
						elemento = 1;
					}
					request.setAttribute("elemento", elemento);
					
					// Datos del contenido y de su usuario creador
					Long id = idsValidacion.get(elemento - 1);
					
					// Intentar extraer una noticia, reto o pregunta de el
					NoticiaDAO noticiaDAO = new NoticiaDAO();
					NoticiaVO noticia = noticiaDAO.getNoticiaById(id);
					Long idAutor = null;
					if (noticia != null) {
						idAutor = noticia.getIdAutor();
						request.setAttribute("contenido", noticia);
						request.setAttribute("noticia", noticia);
					} else {
						RetoDAO retoDAO = new RetoDAO();
						RetoVO reto = retoDAO.getRetoById(id);
						if (reto != null) {
							idAutor = reto.getIdAutor();
							request.setAttribute("contenido", reto);
							request.setAttribute("reto", reto);
						} else {
							PreguntaDAO preguntaDAO = new PreguntaDAO();
							PreguntaVO pregunta = preguntaDAO.getPreguntaById(id);
							if (pregunta != null) {
								idAutor = pregunta.getIdAutor();
								request.setAttribute("contenido", pregunta);
								request.setAttribute("pregunta", pregunta);
								List<RespuestaVO> respuestas = preguntaDAO.getRespuestasByPregunta(id);
								request.setAttribute("respuestas", respuestas);
							}
						}
					}
					
					// Datos del autor
					if (idAutor == null) {
						// El contenido a mostrar no es ni noticia, reto ni pregunta
						response.sendRedirect(request.getContextPath() + "/error-interno");
					} else {
						UsuarioDAO usuarioDAO = new UsuarioDAO();
						UsuarioVO usuario = usuarioDAO.getUsuarioById(idAutor);
						request.setAttribute("alias", usuario.getAlias());
						request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
					}
				}
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
			
			
			filterChain.doFilter(request, response);
		}
	}

}
