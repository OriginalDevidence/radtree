package sistinfo.capamodelo.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.utils.CookieManager;
import sistinfo.utils.PBKDF2Hash;
import sistinfo.utils.UsuarioFormatChecker;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class RegistrarUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/*
		 * TODO buscar una forma mejor para hacer esto sin tener que cambiar el encoding
		 * todo el rato
		 */
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> errores = new HashMap<String, String>();
		List<String> erroresArriba = new ArrayList<String>();
		UsuarioVO usuario = extractUsuarioFromHttpRequest(request, errores, erroresArriba);
		if (usuario != null) {
			try {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.insertUsuario(usuario);

				RequestDispatcher req = request.getRequestDispatcher("perfil.jsp");
				CookieManager.addLoginCookiesToResponse(usuario, response);
				response.sendRedirect("perfil.jsp?alias=" + usuario.getAlias());
				request.setAttribute("usuario", usuario);
				req.include(request, response);
			} catch (UsuarioYaExistenteException e) {
				RequestDispatcher req = request.getRequestDispatcher("registro.jsp");
				if (e.isAliasExistente()) {
					errores.put("alias", "Alias ya registrado");
				}
				if (e.isEmailExistente()) {
					errores.put("email", "Email ya existente");
				}
				request.setAttribute("errores", errores);
				request.setAttribute("erroresArriba", erroresArriba);
				req.include(request, response);
			} catch (Exception e) {
				response.sendRedirect("errorInterno.html");
			}
		} else {
			RequestDispatcher req = request.getRequestDispatcher("registro.jsp");
			request.setAttribute("errores", errores);
			request.setAttribute("erroresArriba", erroresArriba);
			req.include(request, response);
		}

	}

	/**
     * Obtiene los datos de un usuario dado un HttpServletRequest y escribe los errores en errors
     * @param request
     * @param errors
     * @return El usuario si se ha extraido correctamente, o null
     */
	public UsuarioVO extractUsuarioFromHttpRequest(HttpServletRequest request, Map<String, String> errors, List<String> errorsArriba) {
    	String alias = request.getParameter("alias");
        String nacimientoString = request.getParameter("nacimiento");
        java.sql.Date nacimiento = null;
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String clave = request.getParameter("clave");
        String reclave = request.getParameter("reclave");
        
        boolean datosCorrectos = true;
        if (alias == null || alias.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("alias", "Campo obligatorio");
        	errorsArriba.add("Un alias debe tener al menos 3 caracteres.");
        } else if (alias.length() < 3) {
        	datosCorrectos = false;
        	errors.put("alias", "Demasiado corto");
        	errorsArriba.add("Un alias debe tener al menos 3 caracteres.");
        } else if (!UsuarioFormatChecker.checkAlias(alias)) {
        	datosCorrectos = false;
        	errors.put("alias", "Formato incorrecto");
        	errorsArriba.add("Un alias solo puede contener letras (a-z), números (0-9) y carácteres especiales (_-). Además, debe empezar por una letra.");
        }
        if (nacimientoString == null || nacimientoString.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nacimiento", "Campo obligatorio");
        } else {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = sdf.parse(nacimientoString);
				java.util.Date dateBefore = sdf.parse("1900-01-01");
				java.util.Date dateAfter = new java.util.Date();
				if (date.before(dateBefore) || date.after(dateAfter)) {
		        	datosCorrectos = false;
		        	Calendar cal = Calendar.getInstance();
		        	cal.setTime(dateAfter);
		        	errors.put("nacimiento", "Valor inválido");
		        	errorsArriba.add("La fecha de nacimiento debe estar comprendida entre el 1/1/1900 y el "
		        		+ cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR));
				} else {
		        	nacimiento = new java.sql.Date(date.getTime());
				}
			} catch (ParseException e) {
				datosCorrectos = false;
	        	errors.put("nacimiento", "Formato incorrecto");
			}
        }
        if (nombre == null || nombre.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("nombre", "Campo obligatorio");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("apellidos", "Campo obligatorio");
        }
        if (email == null || email.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("email", "Campo obligatorio");
        } else if (!UsuarioFormatChecker.checkEmail(email)) {
        	datosCorrectos = false;
        	errors.put("email", "Formato incorrecto");
        	errorsArriba.add("La dirección de email no es válida");
        }
        if (clave == null || clave.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("clave", "Campo obligatorio");
	    } else if (clave.length() < 8) {
	    	datosCorrectos = false;
	    	errors.put("clave", "Demasiado corta");
	    	errorsArriba.add("La clave debe tener al menos 8 caracteres.");
	    }
        if (reclave == null || reclave.trim().isEmpty()) {
        	datosCorrectos = false;
        	errors.put("reclave", "Campo obligatorio");
        } else if (!reclave.equals(clave)) {
        	datosCorrectos = false;
    		errors.put("reclave", "La clave no coincide");
        }
        
        if (datosCorrectos) {
    		String claveHash = PBKDF2Hash.hash(clave.toCharArray());
    		if (claveHash != null) {
    			return new UsuarioVO(alias, nombre, apellidos, nacimiento, email, claveHash, UsuarioVO.TipoUsuario.PARTICIPANTE);
    		}
        }
        return null;
	}

}