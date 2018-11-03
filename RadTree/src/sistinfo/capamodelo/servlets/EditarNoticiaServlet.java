/* TODO: juntar editar noticia con crear noticia?

package sistinfo.capamodelo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.dao.NoticiaDAO;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8"); // TODO
        response.setCharacterEncoding("UTF-8");

        Map<String, String> errores = new HashMap<String, String>();
      	NoticiaVO noticia = extractNoticiaFromHttpRequest(request, errores);

        if (noticia != null) {
	        	try {
	        		NoticiaDAO noticiaDAO = new NoticiaDAO();
	                NoticiaDAO.updateNoticia(noticia);

	                response.sendRedirect("perfil.jsp"); // DUDA

	            } catch (Exception e) {
	                response.sendRedirect("errorInterno.html");
	            }
	        } else {
	            request.setAttribute("errores", errores);
	        }
    }

    /**
     * Obtiene los datos de un usuario dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param errors
     * @return El usuario si se ha extraido correctamente, o null
     *//*
  public NoticiaVO extractNoticiaFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {

        //Como obtener la idAutor mirar DUDA
        long idAutor = new long(1);
        Date fechaRealizacion = null; // TODO

        Estado estado = new Estado(PENDIENTE);
        long numVisitas = new long(0);
        String titulo = request.getParameter("titulo");
        String cuerpo = request.getParameter("cuerpo");
        String url = request.getParameter("url");

        boolean datosCorrectos = true;
        if (titulo == null || titulo.trim().isEmpty()) {
          datosCorrectos = false;
          errors.put("titulo", "Titulo inválido");
        }
        if (cuerpo == null || cuerpo.trim().isEmpty()) {
          datosCorrectos = false;
          errors.put("cuerpo", "Cuerpo noticia inválido");
        }

        if (url == null || alias.url().isEmpty() || !validarURLFormato(url)) {
           datosCorrectos = false;
           errors.put("url", "Campo obligatorio");
           errorsArriba.add("Una url debe ser: http://www.example.com ");
         } else if (!validarURLFormato(url)) {
           datosCorrectos = false;
           errors.put("url", "Formato incorrecto");
           errorsArriba.add("Una url debe de poser el formato: http://www.example.com ");
         }

        if (datosCorrectos) {
          if (clave.equals(reclave)) {
            String claveHash = PBKDF2Hash.hash(clave.toCharArray());
            if (claveHash != null) {
              return new NoticiaVO(idAutor, numVisitas, fechaRealizacion, estado, titulo, cuerpo, url);
            }
          } else {
            errors.put("reclave", "La clave no coincide");
          }
        }
        return null;
  }

  private boolean validarURLFormato(String url) {
    return url.matches("^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$");
  }
}
*/