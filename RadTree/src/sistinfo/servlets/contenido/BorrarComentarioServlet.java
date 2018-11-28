package sistinfo.servlets.contenido;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.vo.UsuarioVO.TipoUsuario;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.util.RequestExtractor;

@SuppressWarnings("serial")
public class BorrarComentarioServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Borrar un comentario y todas sus respuestas asociadas, y redirigir al usuario al contenido que estaba viendo
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Comprobar usuario administrador
        UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
        	response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
        } else {
        	// Borrar el comentario especificado en idComentario
            Long idContenido = RequestExtractor.getLong(request, "id");
            Long idComentario = RequestExtractor.getLong(request, "idComentario");
            
            if (idContenido != null && idComentario != null) {
            	ComentarioDAO comentarioDAO = new ComentarioDAO();
            	try {
					comentarioDAO.deleteComentario(idComentario);
					RequestDispatcher req = request.getRequestDispatcher("ver");
        			request.setAttribute("id", idContenido);
        			req.forward(request, response);
				} catch (ErrorInternoException e) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
				}
            } else {
				response.sendRedirect(request.getContextPath() + "/error-interno");
            }
        }
        
        
    }

}
