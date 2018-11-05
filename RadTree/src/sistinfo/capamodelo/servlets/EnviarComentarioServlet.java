package sistinfo.capamodelo.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.vo.ComentarioVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.utils.RequestExtractor;

@SuppressWarnings("serial")
public class EnviarComentarioServlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	// Comprobar que el usuario está logueado
    	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
    	if (usuario == null) {
    		response.sendRedirect("errorInterno.html");
    	} else {
    		
    		// Leer la URL de redirect y obtener idContenido
    		Long idContenido = RequestExtractor.getLong(request, "id");
    		String redirect = (String)request.getParameter("redirect");
    		if (redirect == null || redirect.trim().isEmpty() || idContenido == null || idContenido <= 0L) {
    			response.sendRedirect("errorInterno.html");
    		} else {
	    		// Comprobar que el campo comentario no es vacío ni demasiado largo
	        	String cuerpo = (String)request.getParameter("cuerpo");
	        	if (cuerpo != null && !cuerpo.trim().isEmpty() && cuerpo.length() <= 300) {
	        		
        			// Ya podemos insertar el comentario, obtener la fecha actual y si es respuesta de alguien
        			Date fechaActual = new Date(new java.util.Date().getTime());
        			Long respuestaDe = RequestExtractor.getLong(request, "respuestaDe");
            		ComentarioDAO comentarioDAO = new ComentarioDAO();
            		try {
            			// Insertar el comentario
            			ComentarioVO comentario = new ComentarioVO(usuario.getIdUsuario(), idContenido, cuerpo, 0, fechaActual, respuestaDe);
            			comentarioDAO.insertComentario(comentario);
            			
            			// Redirigir a la misma página para limpiar la caja de comentarios y mostrar el nuevo comment
            			response.sendRedirect(redirect + "?id=" + idContenido.toString());
            		} catch (ErrorInternoException e) {
                		response.sendRedirect("errorInterno.html");
            		}
            		
	        	}
    		}
    	}
    	
    }
    
}