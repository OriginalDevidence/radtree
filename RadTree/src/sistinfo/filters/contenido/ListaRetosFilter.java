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
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

public class ListaRetosFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Obtiene los datos de los retos validados del sistema e incluirlo en el atributo retos
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;

			// Barra de búsqueda: string a buscar
			String busqueda = request.getParameter("busqueda");
			RetoDAO retoDAO = new RetoDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			try {
				// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
				List<RetoVO> retos;
				if (busqueda == null || busqueda.trim().isEmpty()) {
					retos = retoDAO.getRetosUltimos(30);
				} else {
					retos = retoDAO.getRetosBySearch(busqueda, 30);
				}
				// Añadir información especial e incluirlo en la request
				retos = comentarioDAO.addNumComentariosToContenido(retos);
				request.setAttribute("retos", retos);
				filterChain.doFilter(request, response);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
