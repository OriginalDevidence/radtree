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

public class ListarMiContenidoFilter implements Filter {

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
			
			// Comprobar que hay un usuario y obtener su ID, no hace falta comprobar su tipo ya que se hace en otro filter
			UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
			if (usuario == null) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			} else {
				Long idUsuario = usuario.getIdUsuario();
			
				// Ver qué tipo de contenido cargar
				String contentType = request.getParameter("tipoContenido");
				if (contentType == null || (!contentType.equals("noticia") && !contentType.equals("pregunta") && !contentType.equals("reto"))) {
					// Default
					contentType = "noticia";
				}
				request.setAttribute("tipoContenido", contentType);
				
				// Cargar el contenido de ese tipo y colocarlo en la request
				if (contentType.equals("noticia")) {
					request.setAttribute("contenidoPathNameView", "noticias");
					request.setAttribute("contenidoPathNameEdit", "noticia");
					NoticiaDAO noticiaDAO = new NoticiaDAO();
					try {
						List<NoticiaVO> noticias = noticiaDAO.getNoticiasByAutor(idUsuario);
						request.setAttribute("noticias", noticias);
					} catch (ErrorInternoException e) {
						response.sendRedirect(request.getContextPath() + "/error-interno");
					}
				} else if (contentType.equals("pregunta")) {
					request.setAttribute("contenidoPathNameView", "preguntas");
					request.setAttribute("contenidoPathNameEdit", "pregunta");
					PreguntaDAO preguntaDAO = new PreguntaDAO();
					try {
						List<PreguntaVO> preguntas = preguntaDAO.getPreguntasByAutor(idUsuario);
						request.setAttribute("preguntas", preguntas);
					} catch (ErrorInternoException e) {
						response.sendRedirect(request.getContextPath() + "/error-interno");
					}
				} else if (contentType.equals("reto")) {
					request.setAttribute("contenidoPathNameView", "retos");
					request.setAttribute("contenidoPathNameEdit", "reto");
					RetoDAO retoDAO = new RetoDAO();
					try {
						List<RetoVO> retos = retoDAO.getRetosByAutor(idUsuario);
						request.setAttribute("retos", retos);
					} catch (ErrorInternoException e) {
						response.sendRedirect(request.getContextPath() + "/error-interno");
					}
				}
			}
			
			filterChain.doFilter(request, response);
		}
	}

}
