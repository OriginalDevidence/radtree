package sistinfo.servlets.jsp;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.UsuarioDAO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.util.RequestExtractor;

@SuppressWarnings("serial")
public class UsuariosServlet extends FooterServlet {
	// Número de piezas de contenido mostradas por página
	public static final int CONTENIDO_POR_PAGINA = 10;

	// Filtro de búsqueda para usuarios. Si no tiene ninguna de sus componentes
	// activas,
	// activa todas
	public class FiltroUsuario {
		boolean filtrarAlias, filtrarNombre, filtrarApellidos, filtrarCorreo;

		public FiltroUsuario(boolean filtrarAlias, boolean filtrarNombre, boolean filtrarApellidos,
				boolean filtrarCorreo) {
			// Si no se ha marcado nada se interpreta como si se haya marcado todo
			if (filtrarAlias || filtrarNombre || filtrarApellidos || filtrarCorreo) {
				this.filtrarAlias = filtrarAlias;
				this.filtrarNombre = filtrarNombre;
				this.filtrarApellidos = filtrarApellidos;
				this.filtrarCorreo = filtrarCorreo;
			} else {
				this.filtrarAlias = true;
				this.filtrarNombre = true;
				this.filtrarApellidos = true;
				this.filtrarCorreo = true;
			}
		}

		public boolean getFiltrarAlias() {
			return filtrarAlias;
		}

		public boolean getFiltrarNombre() {
			return filtrarNombre;
		}

		public boolean getFiltrarApellidos() {
			return filtrarApellidos;
		}

		public boolean getFiltrarCorreo() {
			return filtrarCorreo;
		}

	}

	/**
	 * Redirect a doPost de la misma clase
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Cargar el contenido de la lista de usuarios: coloca en la request una lista
	 * de UsuarioVO correspondiente a los usuarios encontrados por el parámetro
	 * búsqueda (si no existe este parámetro, no muestra ningún usuario)
	 * 
	 * Recibe un parámetro búsqueda (string) que filtra a los usuarios para
	 * mostarlos (si no existe, no muestra nada)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.doPost(request, response);

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher req = request.getRequestDispatcher("/jsp/usuarios.jsp");

		// Comprobar si ya hay un usuario como atributo
		String busqueda = request.getParameter("busqueda");
		// Búsqueda: filtros de atributos
		boolean fAlias = request.getParameter("filtroAlias") != null;
		boolean fNombre = request.getParameter("filtroNombre") != null;
		boolean fApellidos = request.getParameter("filtroApellidos") != null;
		boolean fCorreo = request.getParameter("filtroCorreo") != null;
		FiltroUsuario filtro = new FiltroUsuario(fAlias, fNombre, fApellidos, fCorreo);

		// Obtener página actual y páginas totales
		Integer noOfContenido;
		Integer page;
		int noOfPages;

		if (busqueda != null && !busqueda.trim().isEmpty()) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				noOfContenido = usuarioDAO.getNumUsuariosBySearch(busqueda, CONTENIDO_POR_PAGINA, filtro);

				noOfPages = (int) Math.ceil(noOfContenido.doubleValue() / CONTENIDO_POR_PAGINA);

				page = RequestExtractor.getInteger(request, "currentPage");

				// Gestión del número de página
				if (page == null) {
					// Primera visita a la página - ir a pág 1
					page = 1;
				} else {
					// Botón pulsado (siguiente/anterior página)
					String button = request.getParameter("button");
					if (button != null) {
						switch (button) {
						case "siguientePagina":
							page += 1;
							break;
						case "anteriorPagina":
							page -= 1;
							break;
						case "ultimaPagina":
							page = noOfPages;
							break;
						case "primeraPagina":
							page = 1;
							break;
						}
					} else {
						button = request.getParameter("irPagina");
						Integer irPagina = RequestExtractor.getInteger(request, "irPagina");
						if (irPagina != null) {
							page = irPagina;
						}
					}
				}

				List<UsuarioVO> usuarios = usuarioDAO.getUsuariosBySearchByPag(busqueda, CONTENIDO_POR_PAGINA, page, filtro);
				request.setAttribute("usuarios", usuarios);
				
				// Datos sobre la paginación y búsqueda
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("currentPage", page);
				request.setAttribute("busquedaAnterior", busqueda);
				
				req.forward(request, response);
			} catch (ErrorInternoException e) {
				response.sendRedirect(request.getContextPath() + "/error-interno");
				return;
			}
		} else {
			request.setAttribute("noOfPages", 1);
			request.setAttribute("currentPage", 1);
			req.forward(request, response);
		}

		

	}
}
