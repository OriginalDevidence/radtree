package sistinfo.servlets.jsp.carteles;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.UsuarioVO;
import sistinfo.excepciones.ErrorInternoException;
import sistinfo.servlets.jsp.FooterServlet;

@SuppressWarnings("serial")
public class ListaDePreguntasServlet extends FooterServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido de las últimas preguntas del sistema. Si recibe un parámetro busqueda,
     * solo tiene en cuenta las preguntas que tengan busqueda en su enunciado.
     * Coloca la lista de preguntas en la request y muestra el contenido según listaDePreguntas.jsp
     * 
     * Recibe un parámetro búsqueda (string) que filtra las preguntas a mostrar
     * y otro noContestadas el cual, si hay un usuario logueado, filtra las preguntas para
     * solamente mostrar las preguntas no contestadas por el usuario logueado actualmente
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	super.doPost(request, response);
    	
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/carteles/listaDePreguntas.jsp");
    	
		// Barra de búsqueda: string a buscar y filtrar por preguntas no contestadas o no
		String busqueda = request.getParameter("busqueda");
		String noContestadasString = request.getParameter("noContestadas");
		boolean noContestadas = noContestadasString != null && request.getSession().getAttribute("usuario") != null && noContestadasString.equals("on");
		Long idUsuario = null;
		if (noContestadas) {
			idUsuario = ((UsuarioVO)request.getSession().getAttribute("usuario")).getIdUsuario();
		}
		PreguntaDAO preguntaDAO = new PreguntaDAO();
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		try {
			// Según la barra de busqueda obtener las preguntas con unas caracteristicas u otras
			List<PreguntaVO> preguntas;
			if (busqueda == null || busqueda.trim().isEmpty()) {
				if (noContestadas) {
					preguntas = preguntaDAO.getPreguntasUltimasContestadas(false, idUsuario, 30);
				} else {
					preguntas = preguntaDAO.getPreguntasUltimas(30);
				}
			} else {
				if (noContestadas) {
					preguntas = preguntaDAO.getPreguntasBySearchContestadas(busqueda, false, idUsuario, 30);
				} else {
					preguntas = preguntaDAO.getPreguntasBySearch(busqueda, 30);
				}
			}
			// Añadir información especial e incluirlo en la request
			preguntas = comentarioDAO.addNumComentariosToContenido(preguntas);
			preguntas = preguntaDAO.addVecesContestadaToPregunta(preguntas);
			request.setAttribute("preguntas", preguntas);
			
			req.forward(request, response);
			
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}
			
	}

}
