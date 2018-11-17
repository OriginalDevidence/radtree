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

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			String busqueda = request.getParameter("busqueda");
			RetoDAO retoDAO = new RetoDAO();
			ComentarioDAO comentarioDAO = new ComentarioDAO();
			try {
				List<RetoVO> retos;
				if (busqueda == null || busqueda.trim().isEmpty()) {
					retos = retoDAO.getRetosUltimos(10);
				} else {
					retos = retoDAO.getRetosBySearch(busqueda, 10);
				}
				retos = comentarioDAO.addNumComentariosToContenido(retos);
				request.setAttribute("retos", retos);
			} catch (ErrorInternoException e) {
				e.printStackTrace();
				response.sendRedirect("error-interno");
			}
			filterChain.doFilter(request, response);
		}
	}

}
