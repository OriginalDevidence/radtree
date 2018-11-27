package sistinfo.servlets.jsp;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public abstract class FooterServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Incluye el contenido del footer en la request actual (2 noticias últimas y 2 populares)
     * Método hecho para ser ejecutado en sus subclases
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	
    	NoticiaDAO noticiaDAO = new NoticiaDAO();
		try {
			// Noticias últimas
			List<NoticiaVO> noticiasUltimas = noticiaDAO.getNoticiasUltimas(2);
			if (noticiasUltimas.size() > 0) {
				request.setAttribute("footerUltima1", noticiasUltimas.get(0));
				if (noticiasUltimas.size() > 1) {
					request.setAttribute("footerUltima2", noticiasUltimas.get(1));
				}
			}
			// Noticias populares
			List<NoticiaVO> noticiasPopulares = noticiaDAO.getNoticiasPopulares(2);
			if (noticiasPopulares.size() > 0) {
				request.setAttribute("footerPopular1", noticiasPopulares.get(0));
				if (noticiasPopulares.size() > 1) {
					request.setAttribute("footerPopular2", noticiasPopulares.get(1));
				}
			}
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
			return; // Ha ocurrido un error al cargar las noticias del footer y no hace falta hacer más
		}
		
	}

}
