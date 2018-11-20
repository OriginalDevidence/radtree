package sistinfo.servlets.jsp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class QuienesSomosServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de quienesSomos.jsp. No hace nada especial, simplemente evita el acceso
     * directo a los ficheros jsp
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	request.getRequestDispatcher("/jsp/quienesSomos.jsp").forward(request, response);
    	
	}

}
