package sistinfo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioVO;
import sistinfo.capadatos.dao.UsuarioDAO;

public class RegistroServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response)
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String alias = request.getParameter("alias");
        String nacimiento = request.getParameter("nacimiento");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");

        UsusarioVO usuario = new UsuarioVO(alias, nacimiento, nombre, apellidos, email, clave, reclave);
        try {
            UsuarioDAO facade = new UsuarioDAO();
            facade.insertUsuario(usuario);
        } catch (AliasYaExistenteException | EmailYaExistenteException e) {
            response.sendRedirect("51_noticia.html");
        } catch (Exception e) {
            response.sendRedirect("paginaError.html");
        }
    }
    
}