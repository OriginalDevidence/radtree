package sistinfo.servlets.jsp;

import java.io.IOException;

import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sistinfo.capadatos.dao.ComentarioDAO;
import sistinfo.capadatos.dao.NoticiaDAO;
import sistinfo.capadatos.dao.PreguntaDAO;
import sistinfo.capadatos.dao.RetoDAO;
import sistinfo.capadatos.vo.NoticiaVO;
import sistinfo.capadatos.vo.PreguntaVO;
import sistinfo.capadatos.vo.RetoVO;
import sistinfo.excepciones.ErrorInternoException;

@SuppressWarnings("serial")
public class IndexServlet extends HttpServlet {

    /**
     * Redirect a doPost de la misma clase
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * Cargar el contenido del index: 3 noticias, 1 pregunta, 1 reto
     * en los atributos indexNoticia{1-3}, indexPregunta, indexReto
     * y mostrarlo según index.jsp
     * 
     * No recibe parámetros
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	RequestDispatcher req = request.getRequestDispatcher("/jsp/index.jsp");
    	
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		NoticiaDAO noticiaDAO = new NoticiaDAO();
		PreguntaDAO preguntaDAO = new PreguntaDAO();
		RetoDAO retoDAO = new RetoDAO();
		try {
			
			// Cargar 3 noticias
			List<NoticiaVO> noticias = noticiaDAO.getNoticiasUltimas(3);
			noticias = comentarioDAO.addNumComentariosToContenido(noticias);
			for (NoticiaVO noticia : noticias) {
				String path = "images/noticias/" + noticia.getIdContenido() + ".jpg";
				if (request.getSession().getServletContext().getResource(path) != null) {
					noticia.setUrlImagen(request.getContextPath() + "/" + path);
				} else {
					noticia.setUrlImagen(request.getContextPath() + "/images/noticias/default.jpg");
				}
			}
			request.setAttribute("indexNoticia1", noticias.get(0));
			request.setAttribute("indexNoticia2", noticias.get(1));
			request.setAttribute("indexNoticia3", noticias.get(2));
			
			// Cargar una pregunta aleatoria entre las últimas
			List<PreguntaVO> preguntas = preguntaDAO.getPreguntasUltimas(5);
			int randMaxPreguntas = preguntas.size() > 5 ? 5 : preguntas.size();
			PreguntaVO elegida = preguntas.get(new Random().nextInt(randMaxPreguntas));
			request.setAttribute("indexPregunta", elegida);
			
			// Cargar un reto aleatorio entre los últimos
			List<RetoVO> retos = retoDAO.getRetosUltimos(5);
			int randMaxRetos = retos.size() > 5 ? 5 : retos.size();
			request.setAttribute("indexReto", retos.get(new Random().nextInt(randMaxRetos)));

			req.forward(request, response);
			
		} catch (ErrorInternoException e) {
			response.sendRedirect(request.getContextPath() + "/error-interno");
		}
		
	}

}
