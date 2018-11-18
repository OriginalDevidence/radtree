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

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;

public class ListaPreguntasFilter implements Filter {

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
			
			String busqueda = request.getParameter("busqueda");
			String noContestadasString = request.getParameter("noContestadas");
			boolean noContestadas = noContestadasString != null && request.getSession().getAttribute("usuario") != null && noContestadasString.equals("on");
			Long idUsuario = ((UsuarioVO)request.getSession().getAttribute("usuario")).getIdUsuario();
			PreguntaDAO preguntaDAO = new PreguntaDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			try {
				List<PreguntaVO> preguntas;
				if (busqueda == null || busqueda.trim().isEmpty()) {
					if (noContestadas) {
						preguntas = preguntaDAO.getPreguntasUltimasContestadas(false, idUsuario, 10);
					} else {
						preguntas = preguntaDAO.getPreguntasUltimas(10);
					}
				} else {
					if (noContestadas) {
						preguntas = preguntaDAO.getPreguntasBySearchContestadas(busqueda, false, idUsuario, 10);
					} else {
						preguntas = preguntaDAO.getPreguntasBySearch(busqueda, 10);
					}
				}
				preguntas = comentarioDAO.addNumComentariosToContenido(preguntas);
				preguntas = preguntaDAO.addVecesContestadaToPregunta(preguntas);
				request.setAttribute("preguntas", preguntas);
			} catch (ErrorInternoException e) {
				e.printStackTrace();
				response.sendRedirect("error-interno");
			}
			filterChain.doFilter(request, response);
		}
	}

}
