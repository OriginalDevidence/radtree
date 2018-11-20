package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CrearPreguntaServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de crearPregunta.jsp. No hace nada especial, simplemente evita el acceso
     * directo a los ficheros jsp
     * 
     * No recibe parámetros
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	request.getRequestDispatcher("/jsp/gestion/crearPregunta.jsp").forward(request, response);
			
	}

}
