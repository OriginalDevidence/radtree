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

import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

public class NoticiasFooterFilter implements Filter {

	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Obtener 2 noticias últimas y 2 populares e incluirlas en la request
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
		
		if (servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			
			NoticiaDAO noticiaDAO = new NoticiaDAO();
			try {
				// Noticias últimas
				List<NoticiaVO> noticiasUltimas = noticiaDAO.getNoticiasUltimas(2);
				if (noticiasUltimas.size() > 0) {
					request.setAttribute("ultima1", noticiasUltimas.get(0));
					if (noticiasUltimas.size() > 1) {
						request.setAttribute("ultima2", noticiasUltimas.get(1));
					}
				}
				// Noticias populares
				List<NoticiaVO> noticiasPopulares = noticiaDAO.getNoticiasPopulares(2);
				if (noticiasPopulares.size() > 0) {
					request.setAttribute("popular1", noticiasPopulares.get(0));
					if (noticiasPopulares.size() > 1) {
						request.setAttribute("popular2", noticiasPopulares.get(1));
					}
				}
				filterChain.doFilter(request, response);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
			}
			
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
