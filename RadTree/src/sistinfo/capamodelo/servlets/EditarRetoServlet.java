package sistinfo.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sistinfo.capadatos.vo.RetoVO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.excepciones.AliasYaExistenteException;
import sistinfo.capadatos.excepciones.EmailYaExistenteException;

@SuppressWarnings("serial")
public class RegistroServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

      /* TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding todo el rato */
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> errores = new HashMap<String, String>();
      	RetoVO reto = extractRetoFromHttpRequest(request, errores);

        if (reto != null) {
	        	try {
	        		RetoDAO retoDAO = new RetoDAO();
	                RetoDAO.updateReto(reto);

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
     */
  public RetoVO extractRetoFromHttpRequest(HttpServletRequest request, Map<String, String> errors) {

        //Como obtener la idAutor mirar DUDA
        long idAutor = new long(1);
        Date fechaRealizacion = null; // TODO

        Estado estado = new Estado(PENDIENTE);
        long numVisitas = new long(0);
        String titulo = request.getParameter("titulo");
        String cuerpo = request.getParameter("cuerpo");

        boolean datosCorrectos = true;
        if (titulo == null || titulo.trim().isEmpty()) {
          datosCorrectos = false;
          errors.put("titulo", "Titulo inválido");
        }
        if (cuerpo == null || cuerpo.trim().isEmpty()) {
          datosCorrectos = false;
          errors.put("cuerpo", "Cuerpo del reto inválido");
        }

        if (datosCorrectos) {
          if (clave.equals(reclave)) {
            String claveHash = PBKDF2Hash.hash(clave.toCharArray());
            if (claveHash != null) {
              return new RetoVO(idAutor, numVisitas, fechaRealizacion, estado, titulo, cuerpo);
            }
          } else {
            errors.put("reclave", "La clave no coincide");
          }
        }
        return null;
  }
}
