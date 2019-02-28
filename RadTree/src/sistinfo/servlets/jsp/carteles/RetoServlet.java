package sistinfo.servlets.jsp.carteles;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.util.IncludeInRequest;
import sistinfo.servlets.jsp.util.IncrementarVisitas;

@SuppressWarnings("serial")
public class RetoServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido del reto con id recibido por parámetro (id),
     * almacena su información en un atributo reto (RetoVO) y lo muestra
     * según reto.jsp
     * 
     * Recibe un parámetro id (Long) que indica el ID del contenido a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeReto(request, response)
    			&& IncludeInRequest.includeComentarios(request, response)
    			&& IncrementarVisitas.incrementarVisitasContenido(request, response)) {
    		request.getRequestDispatcher("/jsp/carteles/reto.jsp").forward(request, response);
    	}
	}

}
