package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class ColaValidacionServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de la cola de validación, recibe el número de contenido a mostrar
     * (si es inváldio muestra el primero) y lo muestra según colaValidacion.jsp
     * 
     * Recibe 1 parámetro número (index) del contenido a mostrar en la cola de validación
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeElementoColaValidacion(request, response)
    			&& IncludeInRequest.includeNumInValidacion(request, response)) {
        	request.getRequestDispatcher("/jsp/gestion/colaValidacion.jsp").forward(request, response);
    	}
			
	}

}
