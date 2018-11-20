package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class GestionContenidoServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * TODO
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeNumInValidacion(request, response)
    			&& IncludeInRequest.includeMiContenido(request, response)) {
        	request.getRequestDispatcher("/jsp/gestion/gestionContenido.jsp").forward(request, response);
    	}
			
	}

}
