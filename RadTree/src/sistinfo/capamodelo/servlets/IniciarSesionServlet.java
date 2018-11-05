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
import sistinfo.utils.PBKDF2Hash;

@SuppressWarnings("serial")
public class IniciarSesionServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	/* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Necesitamos ID y clave para poder hacer login
        String id = request.getParameter("identificador");
        String clave = request.getParameter("clave");
 		String claveHash = PBKDF2Hash.hash(clave.toCharArray());
    	
        // Intentar hacer login con los datos anteriores
        if (id == null || id.trim().isEmpty() || claveHash == null || claveHash.trim().isEmpty()) {
			// Algo ha ido mal
			loginError(request, response);
    	} else {
			UsuarioVO usuario;
			try {
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
					// Enviar al perfil y añadir los datos del usuario en la sesión
					response.sendRedirect("perfil.jsp?alias=" + usuario.getAlias());
					request.getSession().setAttribute("usuario", usuario);
				}
			} catch (ErrorInternoException e) {
				response.sendRedirect("errorInterno.html");
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