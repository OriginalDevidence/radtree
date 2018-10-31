package sistinfo.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.excepciones.AliasYaExistenteException;
import sistinfo.capadatos.excepciones.EmailYaExistenteException;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String alias = request.getParameter("alias");
        Date nacimiento = new Date(37288713); // TODO
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");
        
        if (clave.equals(reclave)) {
            // TODO hash de la contraseña

            UsuarioVO usuario = new UsuarioVO(alias, nombre, apellidos, nacimiento, email, clave, UsuarioVO.TipoUsuario.PARTICIPANTE);
            try {
                UsuarioDAO facade = new UsuarioDAO();
                facade.insertUsuario(usuario);
            } catch (AliasYaExistenteException | EmailYaExistenteException e) {
                response.sendRedirect("51_noticia.html");
            } catch (Exception e) {
                response.sendRedirect("paginaError.html");
            }
        } else {
            response.sendRedirect("53_noticia.html");
        }
    }
    
}