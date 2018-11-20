package sistinfo.filters.contenido;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

public class ContenidoIndexFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Cargar los diferentes contenidos a mostrar en el index de la aplicación: 3 noticias, 1 pregunta, 1 reto
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			NoticiaDAO noticiaDAO = new NoticiaDAO();
			PreguntaDAO preguntaDAO = new PreguntaDAO();
			RetoDAO retoDAO = new RetoDAO();
			try {
				
				// Cargar 3 noticias
				List<NoticiaVO> noticias = noticiaDAO.getNoticiasUltimas(3);
				noticias = comentarioDAO.addNumComentariosToContenido(noticias);
				for (NoticiaVO noticia : noticias) {
					String path = "images/noticias/" + noticia.getIdContenido() + ".jpg";
					if (request.getSession().getServletContext().getResource(path) != null) {
						noticia.setUrlImagen(request.getContextPath() + "/" + path);
					} else {
						noticia.setUrlImagen(request.getContextPath() + "/images/noticias/default.jpg");
					}
				}
				request.setAttribute("indexNoticia1", noticias.get(0));
				request.setAttribute("indexNoticia2", noticias.get(1));
				request.setAttribute("indexNoticia3", noticias.get(2));
				
				// Cargar una pregunta aleatoria entre las últimas
				List<PreguntaVO> preguntas = preguntaDAO.getPreguntasUltimas(5);
				int randMaxPreguntas = preguntas.size() > 5 ? 5 : preguntas.size();
				PreguntaVO elegida = preguntas.get(new Random().nextInt(randMaxPreguntas));
				request.setAttribute("pregunta", elegida);
				
				// Cargar un reto aleatorio entre los últimos
				List<RetoVO> retos = retoDAO.getRetosUltimos(5);
				int randMaxRetos = retos.size() > 5 ? 5 : retos.size();
				request.setAttribute("reto", retos.get(new Random().nextInt(randMaxRetos)));

				filterChain.doFilter(servletRequest, servletResponse);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
