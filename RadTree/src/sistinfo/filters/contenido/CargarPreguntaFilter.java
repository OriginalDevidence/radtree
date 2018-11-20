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

import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RespuestaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

public class CargarPreguntaFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Carga los datos de un pregunta y lo almacena en el atributo pregunta,
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
					// No sabemos qué pregunta mostrar
					response.sendRedirect(request.getContextPath() + "/preguntas");
				}
			}
			if (idContenido != null && idContenido > 0L) {
				// Atributos para los comentarios
				request.setAttribute("urlContenido", "preguntas");
				request.setAttribute("id", idContenido);
				// Cargar el pregunta con ese ID y el usuario autor
				PreguntaDAO preguntaDAO = new PreguntaDAO();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				try {
					PreguntaVO pregunta = preguntaDAO.getPreguntaById(idContenido);
					if (pregunta == null) {
						// El contenido no existe (o no debería ser mostrado)
			            response.sendRedirect(request.getContextPath() + "/preguntas");
					} else {
						UsuarioVO usuario = usuarioDAO.getUsuarioById(pregunta.getIdAutor());
						if (usuario == null) {
				            response.sendRedirect(request.getContextPath() + "/error-interno");
						} else {
							request.setAttribute("pregunta", pregunta);
							request.setAttribute("autorAlias", usuario.getAlias());
							request.setAttribute("autorCompleto", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
						}
						// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
						List<RespuestaVO> respuestas;
						respuestas = preguntaDAO.getRespuestasByPregunta(pregunta.getIdContenido());
						
						request.setAttribute("respuestas", respuestas);
					}
					filterChain.doFilter(request, response);
				} catch (ErrorInternoException e) {
		            response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
