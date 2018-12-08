package sistinfo.servlets.jsp.carteles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.servlets.jsp.FooterServlet;
import sistinfo.util.RequestExtractor;

@SuppressWarnings("serial")
public class ListaDeNoticiasServlet extends FooterServlet {
	
	// Número de piezas de contenido mostradas por página
	public static final int CONTENIDO_POR_PAGINA = 10;
	
	// Filtro de búsqueda para noticias. Si no tiene ninguna de sus componentes activas,
	// activa todas
	public class FiltroNoticia {
		boolean filtrarTitulo, filtrarCuerpo, filtrarUrl;
		
		public FiltroNoticia(boolean filtrarTitulo, boolean filtrarCuerpo, boolean filtrarUrl) {
			// Si no se ha marcado nada se interpreta como si se haya marcado todo
			if (filtrarTitulo || filtrarCuerpo || filtrarUrl) {
				this.filtrarTitulo = filtrarTitulo;
				this.filtrarCuerpo = filtrarCuerpo;
				this.filtrarUrl = filtrarUrl;
			} else {
				this.filtrarTitulo = true;
				this.filtrarCuerpo = true;
				this.filtrarUrl = true;
			}
		}
		
		public boolean getFiltrarTitulo() {
			return filtrarTitulo;
		}
		public boolean getFiltrarCuerpo() {
			return filtrarCuerpo;
		}
		public boolean getFiltrarUrl() {
			return filtrarUrl;
		}
	}

	/**
	 * Redirect a doPost de la misma clase
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Cargar el contenido de las últimas noticias del sistema. Si recibe un
	 * parámetro busqueda o filtro, solo tiene en cuenta las noticias que tengan busqueda en
	 * su titulo, cuerpo o url según el filtro. Coloca la lista de noticias en la request y muestra
	 * el contenido según listaDeNoticias.jsp
	 * 
	 * Recibe un parámetro búsqueda (string) que filtra las noticias a mostrar
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/listaDeNoticias.jsp");

		NoticiaDAO noticiaDAO = new NoticiaDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		
		// Búsqueda: string de búsqueda
		String busqueda = request.getParameter("busqueda");
		// Búsqueda: filtros de atributos
		boolean fTitulo = request.getParameter("filtroTitulo") != null;
		boolean fCuerpo = request.getParameter("filtroCuerpo") != null;
		boolean fUrl = request.getParameter("filtroUrl") != null;
		FiltroNoticia filtro = new FiltroNoticia(fTitulo, fCuerpo, fUrl);

		// Obtener página actual y páginas totales
		Integer page = RequestExtractor.getInteger(request, "currentPage");
		Integer noOfContenido;
		try {
			if (busqueda == null || busqueda.trim().isEmpty()) {
				noOfContenido = noticiaDAO.getNumNoticiasUltimas();
			} else {
				noOfContenido = noticiaDAO.getNumNoticiasBySearch(filtro, busqueda);
			}
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
			return; // no es la mejor opción pero es lo que hay
		}
		int noOfPages = (int)Math.ceil(noOfContenido.doubleValue() / CONTENIDO_POR_PAGINA);

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


		try {
			
			// Según la barra de busqueda obtener las preguntas
			// con unas caracteristicas u otras
			List<NoticiaVO> noticias;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				noticias = noticiaDAO.getNoticiasUltimasByPag(CONTENIDO_POR_PAGINA, page);
			} else {
				noticias = noticiaDAO.getNoticiasBySearchAndPag(CONTENIDO_POR_PAGINA, page, filtro, busqueda);
			}

			// URL imagen
			for (NoticiaVO noticia : noticias) {
				String path = "images/noticias/" + noticia.getIdContenido() + ".jpg";
				if (request.getSession().getServletContext().getResource(path) != null) {
					noticia.setUrlImagen(request.getContextPath() + "/" + path);
				} else {
					noticia.setUrlImagen(request.getContextPath() + "/images/noticias/default.jpg");
				}
			}

			// Incluir en la request
			// Datos sobre la paginación y búsqueda
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("busquedaAnterior", busqueda);

			// Añadir número de comentarios a noticia e incluirlo
			noticias = comentarioDAO.addNumComentariosToContenido(noticias);
			request.setAttribute("noticias", noticias);

			req.forward(request, response);

		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}

	}

}
