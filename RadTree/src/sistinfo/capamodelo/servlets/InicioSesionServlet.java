package sistinfo.capamodelo.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.utils.MD5Hash;

@SuppressWarnings("serial")
public class InicioSesionServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    	String id = request.getParameter("identificador");
    	String clave = request.getParameter("clave");
    	
    	if (id == null || id.trim().isEmpty() || clave == null || clave.trim().isEmpty()) {
			// Algo ha ido mal
			loginError(request, response);
    	} else {

    		byte[] claveHash = MD5Hash.getMD5Hash(clave);
    		UsuarioVO usuario;
    		try {
        		// Intentar acceder
        		UsuarioDAO usuarioDAO = new UsuarioDAO();
        		// Probar login con clave
				usuario = usuarioDAO.getUsuarioByLoginEmail(id, claveHash);
				if (usuario == null) {
					// Probar login con alias
					usuario = usuarioDAO.getUsuarioByLoginAlias(id, claveHash);
				}
				
				if (usuario == null) {
					// Algo ha ido mal
					loginError(request, response);
				} else {
					// Enviar al perfil e incluir el alias en la sesión
	                RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
					request.getSession().setAttribute("alias", usuario.getAlias());
	                request.setAttribute("usuario", usuario);
	                req.include(request, response);
				}
			} catch (ErrorInternoException e) {
				response.sendRedirect("70_errorInterno.html");
			}
    		
    	}
    	
    }
    
    /**
     * Envía al usuario a la página de inicio de sesión indicando que ha sido incorrecto
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void loginError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher req = request.getRequestDispatcher("inicioSesion.jsp");
        request.setAttribute("error", "Identificador o clave inválidos o incorrectos");
        req.include(request, response);
    }
    
}