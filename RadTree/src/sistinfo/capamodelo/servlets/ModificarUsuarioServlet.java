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
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.excepciones.UsuarioYaExistenteException;
import sistinfo.utils.CookieManager;
import sistinfo.capadatos.dao.UsuarioDAO;

@SuppressWarnings("serial")
public class ModificarUsuarioServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String alias = CookieManager.getAliasFromCookies(request);
		String claveHash = CookieManager.getClaveHashFromCookies(request);
		if (alias != null && claveHash != null) { // ya se comprueban no-vacios en CookieManager
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				UsuarioVO usuario = usuarioDAO.getUsuarioByLoginAlias(alias, claveHash);
				if (usuario != null) {
					request.setAttribute("usuario", usuario);
				} else {
					response.sendRedirect("inicioSesion.jsp");
				}
			} catch (ErrorInternoException e) {
				response.sendRedirect("errorInterno.html");
			}
		} else {
			response.sendRedirect("inicioSesion.jsp");
		}
	}
	
}
