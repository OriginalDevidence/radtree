<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- COMENTARIOS -->
<hr class="mtb-50 mtb-sm-20">
<div class="container">
	<div class="row">
		<div class="col-12">
			<h3 class="p-title mb-40"><b>Comentarios</b></h3>

			<script src="${pageContext.request.contextPath}/common/comments.js"></script>

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
							<img src="<c:out value="${pageContext.request.contextPath}/${requestScope.profileImages.get(comentario.idAutor)}"/>" alt="Foto de perfil de <c:out value="${comentario.autor}"/>">
						</div><!-- s-left -->
						
						<div class="s-right ml-100 ml-xs-85">
							<h5>
								<b><c:out value="${comentario.autor}"/></b><span class="font-8 color-ash">,<c:out value=" ${comentario.fecha}"/></span>
								<c:if test="${not empty comentario.autorPadre}">
									<span class="font-9"><i class="ion-reply plr-5"></i><i><c:out value="${comentario.autorPadre}"/></i></span>
								</c:if>
							</h5>
							<p class="mtb-15"><c:out value="${comentario.cuerpo}"/></p>
							<c:if test="${not empty sessionScope.usuario}">
								<button class="btn-brdr-grey btn-b-sm plr-15 mt-5"
									onclick="setRespuestaDe(<c:out value="${comentario.idComentario}"/>, '<c:out value="${comentario.autor}'"/>)"><b>Responder</b></button>
							</c:if>
						</div><!-- s-right -->
						
					</div><!-- sided-70 -->
				</c:forEach>
			</c:if>
			
		</div>

		<div class="col-md-12 col-lg-8 sided-70">
			<h4 class="p-title mt-20"><b>Deja un comentario</b></h4>
			<c:if test="${empty sessionScope.usuario}">
				<p class="mb-20"><a class="link-brdr-btm-primary color-primary" href="${pageContext.request.contextPath}/iniciar-sesion">Inicia sesión</a> para comentar.</p>
			</c:if>
			<c:if test="${not empty sessionScope.usuario}">
				<h5 class="mb-20">Comentando como <b><c:out value="${sessionScope.usuario.alias}"/></b></h5>
				<h5 id="respondiendo" class="mb-20"></h5>
				<form
					class="form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white mb-md-50"
					name="enviarComentario" action="${pageContext.request.contextPath}/retos/comentar" method="post">
					<textarea class="ptb-10" name="cuerpo" maxlength=300 required
						placeholder="Deja un comentario..." rows=3></textarea>
					<input type="hidden" name="id" value="<c:out value="${requestScope.id}"/>"/>
					<input id="respuestaDe" type="hidden" name="respuestaDe" value=""/>
					<button class="btn-fill-primary plr-30" type="submit">
						<b>Comentar</b>
					</button>
				</form>
			</c:if>
		</div>
	</div>
</div>