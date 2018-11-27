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

@SuppressWarnings("serial")
public class ListaDeNoticiasServlet extends FooterServlet {
	public static final int CONTENIDO_POR_PAGINA = 1; // Cambiar el número de piezas de contenido mostradas por
														// página(recomendado 30).

	/**
	 * Redirect a doPost de la misma clase
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * Cargar el contenido de las últimas noticias del sistema. Si recibe un
	 * parámetro busqueda, solo tiene en cuenta las noticias que tengan busqueda en
	 * su titulo, cuerpo o url. Coloca la lista de noticias en la request y muestra
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

		int page = 1;
		int noOfContenido = 0;
		
		// Barra de búsqueda: string a buscar y filtrar por preguntas no contestadas o no
		String busqueda = request.getParameter("busqueda");
		// Si no hay una nueva búsqueda nos quedamos con la anterior.
		if (busqueda == null) {
			busqueda = request.getParameter("busquedaAnterior");
		}

		// Obtener página actual.
		if (request.getParameter("currentPage") != null)
			page = Integer.parseInt(request.getParameter("currentPage"));

		// GestionBotones
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
				page = Integer.parseInt(request.getParameter("noOfPages"));
				break;
			case "primeraPagina":
				page = 1;
				break;
			}
		} else {
			button = request.getParameter("irPagina");
			if (button != null) {
				try {
					page = Integer.parseInt(request.getParameter("irPagina"));

				} catch (NumberFormatException nfe) {
					response.sendRedirect(request.getContextPath() + "/error-interno");
					return;
				}

			}
		}

		try {
			// Según la barra de busqueda obtener las preguntas con unas caracteristicas u
			// otras
			List<NoticiaVO> noticias;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				noticias = noticiaDAO.getNoticiasUltimasByPag(CONTENIDO_POR_PAGINA, page);
				noOfContenido = noticiaDAO.getNumNoticiasUltimas();
			} else {
				noticias = noticiaDAO.getPagNoticiasBySearch(CONTENIDO_POR_PAGINA, page, null, busqueda);
				noOfContenido = noticiaDAO.getNumNoticiasBySearch(null, busqueda);
			}
			// Añadir información especial e incluirlo en la request

			int noOfPages = (int) Math.ceil(noOfContenido / CONTENIDO_POR_PAGINA);

			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);

			// URL imagen
			for (NoticiaVO noticia : noticias) {
				String path = "images/noticias/" + noticia.getIdContenido() + ".jpg";
				if (request.getSession().getServletContext().getResource(path) != null) {
					noticia.setUrlImagen(request.getContextPath() + "/" + path);
				} else {
					noticia.setUrlImagen(request.getContextPath() + "/images/noticias/default.jpg");
				}
			}

			// Num comentarios
			noticias = comentarioDAO.addNumComentariosToContenido(noticias);
			request.setAttribute("noticias", noticias);
			request.setAttribute("busquedaAnterior", busqueda);

			req.forward(request, response);

		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}

	}

}
