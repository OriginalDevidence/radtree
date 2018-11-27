package sistinfo.servlets.jsp.usuarios;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;

@SuppressWarnings("serial")
public class RegistroServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de registro.jsp. No hace nada especial, simplemente evita el acceso
     * directo a los ficheros jsp
     * 
     * No recibe parámetros
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	request.getRequestDispatcher("/jsp/usuarios/registro.jsp").forward(request, response);
    		
	}

}
