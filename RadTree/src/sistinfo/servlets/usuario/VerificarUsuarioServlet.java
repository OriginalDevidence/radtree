package sistinfo.servlets.usuario;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.TokenDAO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.TokenVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;

@SuppressWarnings("serial")
public class VerificarUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Verificar email y si es cuenta Unizar convertirlo en usuario creador también
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	String tokenString = request.getParameter("token");
    	if (usuario == null || usuario.getEmailVerificado()) {
    		response.sendRedirect(request.getContextPath() + "/error-interno");
    	} else {
			try{  
				// Ver si existe un token como el pasado por parametro y actualizar emailVerificado de usuario a true
	    		TokenDAO tokenDAO = new TokenDAO();
	    		TokenVO token = tokenDAO.getTokenByToken(tokenString);
	    		if (token != null && token.getIdUsuario() == usuario.getIdUsuario()) {
	    			// Verificar email y si es cuenta Unizar convertirlo en usuario creador también
	    			usuario.setEmailVerificado(true);
	    			if (usuario.getEmail().endsWith("@unizar.es")) {
	    				usuario.setTipoUsuario(TipoUsuario.CREADOR);
	    			}
	    			// Actualizar los datos del usuario
	    			UsuarioDAO usuarioDAO = new UsuarioDAO();
	    			if (usuarioDAO.updateUsuario(usuario, false, false)) {
	    	    		request.getRequestDispatcher("/perfil").forward(request, response);
	    			} else {
	    	    		response.sendRedirect(request.getContextPath() + "/error-interno");
	    			}
	    		}
			}catch (Exception ex) {
	    		response.sendRedirect(request.getContextPath() + "/error-interno");
			}  
    		
    	}
	}

}