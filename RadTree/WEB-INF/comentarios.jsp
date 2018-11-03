<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sistinfo.capadatos.dao.ComentarioDAO" %>
<%@ page import="sistinfo.capadatos.vo.ComentarioVO" %>
<%@ page import="sistinfo.utils.ProfilePictureManager" %>
<%--
	Obtiene los comentarios para el contenido con id pasado por parametro y los almacena en la request (comentarios)
	También hace un mapa con las fotos de perfil que tiene cada usuario y lo almacena en la request (profileImages)
--%>
<%
	Long idContenido = new Long((String)request.getParameter("id"));
	if (idContenido != null && idContenido > 0) {
		// Obtener los comentarios del contenido y añadirlos a la request
		ComentarioDAO comentarioDAO = new ComentarioDAO();
		ArrayList<ComentarioVO> comentarios = comentarioDAO.getComentariosFromContenido(idContenido);
		request.setAttribute("comentarios", comentarios);
		Map<Long, String> profileImages = new HashMap<Long, String>();
		for (ComentarioVO c : comentarios) {
			String path = ProfilePictureManager.getPathForId(c.getIdAutor());
			if (new File(path).isFile()) {
				profileImages.put(c.getIdAutor(), path);
			} else {
				profileImages.put(c.getIdAutor(), ProfilePictureManager.getDefaultPath());
			}
		}
		request.setAttribute("profileImages", profileImages);
	}
%>
<!DOCTYPE html>
<!-- COMENTARIOS -->
<hr class="mtb-50 mtb-sm-20">
<div class="container">
	<div class="row">
		<div class="col-12">
			<h3 class="p-title mb-40"><b>Comentarios</b></h3>

			<c:if test="${empty requestScope.comentarios}">
				<p class="mb-25">Parece que no hay ningún comentario todavía. <i>¡Se el primero en comentar!</i></p>
			</c:if>
			<c:if test="${not empty requestScope.comentarios}">
				<c:forEach items="${requestScope.comentarios}" var="comentario">
					<c:if test="${empty comentario.autorPadre}">
						<c:set var="margen" value=""/>
					</c:if>
					<c:if test="${not empty comentario.autorPadre}">
						<c:set var="margen" value="ml-40"/>
					</c:if>
					<div class="sided-70 mb-40 <c:out value="${margen}"/>">
		
						<div class="s-left rounded">
							<img src="<c:out value="${requestScope.profileImages.get(comentario.idAutor)}"/>" alt="Foto de perfil de <c:out value="${comentario.autor}"/>">
						</div><!-- s-left -->
						
						<div class="s-right ml-100 ml-xs-85">
							<h5>
								<b><c:out value="${comentario.autor}"/></b><span class="font-8 color-ash">,<c:out value=" ${comentario.fecha}"/></span>
								<c:if test="${not empty comentario.autorPadre}">
									<span class="font-9"><i class="ion-reply plr-5"></i><i><c:out value="${comentario.autorPadre}"/></i></span>
								</c:if>
							</h5>
							<p class="mtb-15"><c:out value="${comentario.cuerpo}"/></p>
							<button class="btn-brdr-grey btn-b-sm plr-15 mr-10 mt-5"><b>Like</b></button>
							<button class="btn-brdr-grey btn-b-sm plr-15 mt-5"><b>Responder</b></button>
						</div><!-- s-right -->
						
					</div><!-- sided-70 -->
				</c:forEach>
			</c:if>
			
		</div>


		<!-- TODO cambiar el nombre de quien comenta y permitir comentarios e informar de los errores (caja vacia) -->
		<div class="col-md-12 col-lg-8 sided-70">
			<h4 class="p-title mt-20"><b>Deja un comentario</b></h4>
			<h5 class="mb-20">Comentando como <b>Shuhein Chui</b></h5>
			<form class="form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white mb-md-50">
				<textarea class="ptb-10" placeholder="Deja un comentario..." rows=3></textarea>
				<button class="btn-fill-primary plr-30" type="submit" formaction="EnviarComentario.do"><b>Comentar</b></button>
			</form>
		</div>
	</div>
</div>