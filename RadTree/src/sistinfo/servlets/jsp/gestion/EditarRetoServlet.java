package sistinfo.servlets.jsp.gestion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class EditarRetoServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de editarReto.jsp incluyendo en sus atributos el reto (RetoVO)
     * 
     * Recibe como parámetro el ID del reto a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeReto(request, response)) {
        	request.getRequestDispatcher("/jsp/gestion/editarReto.jsp").forward(request, response);
    	}
	}

}
