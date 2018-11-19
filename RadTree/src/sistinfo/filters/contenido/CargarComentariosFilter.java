package sistinfo.filters.contenido;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.vo.ComentarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.ProfilePictureManager;
import sistinfo.util.RequestExtractor;

public class CargarComentariosFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Cargar los comentarios correspondientes a un contenido con un ID
	 * Coloca en la request los comentarios en el atributo commentarios (en una lista ordenada por fecha realización y respuestas)
	 * y también un mapa que indica dónde esta alojada la foto de perfil de cada ID usuario en el atributo profileImages
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			// Obtener los comentarios para el contenido que se está viendo
			// Si no se obtienen no se redirige a error, simplemente no se muestran
			Long idContenidoIncluido = RequestExtractor.getLong(request, "id");
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			if (idContenidoIncluido != null && idContenidoIncluido > 0) {
				// Obtener los comentarios del contenido y añadirlos a la request
				try {
					List<ComentarioVO> comentarios = comentarioDAO.getComentariosFromContenido(idContenidoIncluido);
					request.setAttribute("comentarios", comentarios);
					// Crear un mapa idUsuario - path a su foto de perfil
					Map<Long, String> profileImages = new HashMap<Long, String>();
					for (ComentarioVO c : comentarios) {
						if (!profileImages.containsKey(c.getIdAutor())){
							String path = ProfilePictureManager.getPathForId(c.getIdAutor());
							if (new File(path).isFile()) {
								profileImages.put(c.getIdAutor(), path);
							} else {
								profileImages.put(c.getIdAutor(), ProfilePictureManager.getDefaultPath());
							}
						}
					}
					request.setAttribute("profileImages", profileImages);
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
			}
			filterChain.doFilter(request, response);
		}
	}

}
