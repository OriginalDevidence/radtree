package sistinfo.servlets.jsp.carteles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class ListaDeNoticiasServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de las últimas noticias del sistema. Si recibe un parámetro busqueda,
     * solo tiene en cuenta las noticias que tengan busqueda en su titulo, cuerpo o url.
     * Coloca la lista de noticias en la request y muestra el contenido según listaDeNoticias.jsp
     * 
     * Recibe un parámetro búsqueda (string) que filtra las noticias a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/listaDeNoticias.jsp");
			
		// Barra de búsqueda: string a buscar y filtrar por preguntas no contestadas o no
		String busqueda = request.getParameter("busqueda");
		NoticiaDAO noticiaDAO = new NoticiaDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		try {
			// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
			List<NoticiaVO> noticias;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				noticias = noticiaDAO.getNoticiasUltimas(30);
			} else {
				noticias = noticiaDAO.getNoticiasBySearch(busqueda, 30);
			}
			// Añadir información especial e incluirlo en la request
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
			
			req.forward(request, response);
			
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}
			
	}

}
