package sistinfo.servlets.jsp;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.ClasificacionVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class ClasificacionServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de la clasificación: una lista de elementos de tipo ClasificacionVO
     * ordenadas por posición que contienen información sobre el alias, preguntas contestadas y puntuación
     * de los mejores usuarios del sistema, y mostrarlo por clasificacion.jsp
     * 
     * No recibe parámetros
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/clasificacion.jsp");
		
		// Obtener datos clasificación
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			List<ClasificacionVO> clasificacion = usuarioDAO.getClasificacion(10);
			request.setAttribute("clasificacion", clasificacion);
			
			req.forward(request, response);
			
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}
	}

}
