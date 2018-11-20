package sistinfo.servlets.jsp.carteles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class ListaDeRetosServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de los últimos retos del sistema. Si recibe un parámetro busqueda,
     * solo tiene en cuenta los retos que tengan busqueda en su titulo o cuerpo.
     * Coloca la lista de retos en la request y muestra el contenido según listaDeRetos.jsp
     * 
     * Recibe un parámetro búsqueda (string) que filtra los retos a mostrar
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/listaDeRetos.jsp");
		
		// Barra de búsqueda: string a buscar
		String busqueda = request.getParameter("busqueda");
		RetoDAO retoDAO = new RetoDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		try {
			// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
			List<RetoVO> retos;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				retos = retoDAO.getRetosUltimos(30);
			} else {
				retos = retoDAO.getRetosBySearch(busqueda, 30);
			}
			// Añadir información especial e incluirlo en la request
			retos = comentarioDAO.addNumComentariosToContenido(retos);
			request.setAttribute("retos", retos);
			
			req.forward(request, response);
			
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}
			
	}

}
