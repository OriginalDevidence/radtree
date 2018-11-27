package sistinfo.servlets.jsp.usuarios;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.util.IncludeInRequest;

@SuppressWarnings("serial")
public class PerfilServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido del usuario obtenido por alias o el que ha iniciado sesión y lo muestra según perfil.jsp
     * 
     * Recibe un parámetro alias opcional (String) del usuario a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	if (IncludeInRequest.includeUsuario(request, response)) {
    		request.getRequestDispatcher("/jsp/usuarios/perfil.jsp").forward(request, response);
    	}
	}

}
