package sistinfo.servlets.jsp.usuarios;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.util.CookieManager;

@SuppressWarnings("serial")
public class InicioSesionServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de inicioSesion.jsp. No hace nada especial, simplemente evita el acceso
     * directo a los ficheros jsp
     * 
     * No recibe parámetros
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/usuarios/inicioSesion.jsp");
    	
    	String identificadorCookies = CookieManager.getIdFromCookies(request);
    	if (identificadorCookies != null && request.getParameter("identificador") == null) {
    		request.setAttribute("identificadorCookies", identificadorCookies);
    	}
    	
    	req.forward(request, response);
    		
	}

}
