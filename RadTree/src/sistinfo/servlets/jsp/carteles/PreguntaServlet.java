package sistinfo.servlets.jsp.carteles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class PreguntaServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de la pregunta con id recibido por parámetro (id),
     * almacena su información en un atributo pregunta (PreguntaVO), respuestas (List de RespuestaVO)
     * y lo muestra según pregunta.jsp. También almacena información sobre si el usuario
     * ha respondido a la pregunta y qué contestaciones ha dado.
     * 
     * Recibe un parámetro id (Long) que indica el ID del contenido a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includePregunta(request, response) && IncludeInRequest.includeComentarios(request, response)) {
    		request.getRequestDispatcher("/jsp/carteles/pregunta.jsp").forward(request, response);
    	}
	}

}
