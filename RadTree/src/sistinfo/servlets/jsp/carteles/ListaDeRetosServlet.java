package sistinfo.servlets.jsp.carteles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.servlets.jsp.carteles.ListaDeNoticiasServlet.FiltroNoticia;
import sistinfo.util.RequestExtractor;

@SuppressWarnings("serial")
public class ListaDeRetosServlet extends FooterServlet {
	// Número de piezas de contenido mostradas por página
	public static final int CONTENIDO_POR_PAGINA = 10;

	// Filtro de búsqueda para noticias. Si no tiene ninguna de sus componentes
	// activas,
	// activa todas
	public class FiltroReto {
		boolean filtrarTitulo, filtrarCuerpo;

		public FiltroReto(boolean filtrarTitulo, boolean filtrarCuerpo) {
			// Si no se ha marcado nada se interpreta como si se haya marcado todo
			if (filtrarTitulo || filtrarCuerpo) {
				this.filtrarTitulo = filtrarTitulo;
				this.filtrarCuerpo = filtrarCuerpo;
			} else {
				this.filtrarTitulo = true;
				this.filtrarCuerpo = true;
			}
		}

		public boolean getFiltrarTitulo() {
			return filtrarTitulo;
		}

		public boolean getFiltrarCuerpo() {
			return filtrarCuerpo;
		}

	}

	/**
	 * Redirect a doPost de la misma clase
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Cargar el contenido de los últimos retos del sistema. Si recibe un parámetro
	 * busqueda, solo tiene en cuenta los retos que tengan busqueda en su titulo o
	 * cuerpo. Coloca la lista de retos en la request y muestra el contenido según
	 * listaDeRetos.jsp
	 * 
	 * Recibe un parámetro búsqueda (string) que filtra los retos a mostrar
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.doPost(request, response);

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/listaDeRetos.jsp");

		// Barra de búsqueda: string a buscar
		String busqueda = request.getParameter("busqueda");
		// Búsqueda: filtros de atributos
		boolean fTitulo = request.getParameter("filtroTitulo") != null;
		boolean fCuerpo = request.getParameter("filtroCuerpo") != null;
		FiltroReto filtro = new FiltroReto(fTitulo, fCuerpo);

		RetoDAO retoDAO = new RetoDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();

		// Obtener página actual y páginas totales
		Integer noOfContenido;
		Integer page;
		int noOfPages;

		try {
			if (busqueda == null || busqueda.trim().isEmpty()) {
				noOfContenido = retoDAO.getNumRetosUltimos(CONTENIDO_POR_PAGINA);
			} else {
				noOfContenido = retoDAO.getNumRetosBySearch(busqueda, CONTENIDO_POR_PAGINA, filtro);
			}

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

			// Según la barra de busqueda obtener las preguntas con unas caracteristicas u
			// otras
			List<RetoVO> retos;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				retos = retoDAO.getRetosUltimosByPag(CONTENIDO_POR_PAGINA, page);
			} else {
				retos = retoDAO.getRetosBySearchByPag(busqueda, CONTENIDO_POR_PAGINA, filtro, page);
			}

			// Datos sobre la paginación y búsqueda
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("busquedaAnterior", busqueda);
			// Añadir información especial e incluirlo en la request
			retos = comentarioDAO.addNumComentariosToContenido(retos);
			request.setAttribute("retos", retos);

			req.forward(request, response);

		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}

	}

}
