package sistinfo.servlets.jsp.carteles;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class NoticiaServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de la noticia con id recibido por parámetro (id),
     * almacena su información en un atributo noticia (NoticiaVO) y lo muestra
     * según noticia.jsp
     * 
     * Recibe un parámetro id (Long) que indica el ID del contenido a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/noticia.jsp");
    
    	if (IncludeInRequest.includeNoticia(request, response) && IncludeInRequest.includeComentarios(request, response)) {
			req.forward(request, response);
		}
			
	}

}
