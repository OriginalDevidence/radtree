package sistinfo.servlets.usuario;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.TokenDAO;
import sistinfo.capadatos.vo.TokenVO;
import sistinfo.capadatos.vo.UsuarioVO;

@SuppressWarnings("serial")
public class EnviarVerificacionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Envía un email para verificar usuario insertando un token en la base de datos
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null || usuario.getEmailVerificado()) {
    		response.sendRedirect(request.getContextPath() + "/error-interno");
    	} else {
			try{  
				// Borrar el token viejo si existe y crear un token nuevo
	    		TokenDAO tokenDAO = new TokenDAO();
	    		TokenVO tokenViejo = tokenDAO.getTokenByUsuario(usuario.getIdUsuario());
	    		if (tokenViejo != null) {
	    			tokenDAO.deleteToken(tokenViejo);
	    		}
	    		String tokenString = UUID.randomUUID().toString();
	    		if (tokenDAO.insertToken(new TokenVO(tokenString, usuario.getIdUsuario()))) {
	    		
					Properties properties = System.getProperties();  
					properties.setProperty("mail.smtp.host", "localhost");  
					Session session = Session.getDefaultInstance(properties);  
					
					MimeMessage message = new MimeMessage(session);  
					message.setFrom(new InternetAddress("no-reply@radtree.ml"));  
					message.addRecipient(Message.RecipientType.TO,new InternetAddress(usuario.getEmail()));  
					message.setSubject("RadTree - Verificación de correo");  
					message.setText("Gracias por registrarte en RadTree!\n\n"
									+ "Para confirmar tu dirección de correo electrónico, haz click en el siguiente enlace:\n"
									+ "http://radtree.ml/perfil/verificar?token=" + tokenString + "\n\n"
									+ "El equipo de RadTree");  
					
					Transport.send(message);
		    		request.getRequestDispatcher("/perfil").forward(request, response);
	    		} else {
		    		response.sendRedirect(request.getContextPath() + "/error-interno");
	    		}
			}catch (Exception ex) {
	    		response.sendRedirect(request.getContextPath() + "/error-interno");
			}  
    		
    	}
	}

}