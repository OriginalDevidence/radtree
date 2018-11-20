<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title><c:out value="${requestScope.pregunta.enunciado}" /> -
		RadTree</title>
	<meta name="description"
		content="Una de las preguntas hechas por los usuarios de RadTree">
	<meta name="author"
		content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">
	
	<!-- Font -->
	<link
		href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700"
		rel="stylesheet">
	
	<!-- Stylesheets -->
	<link
		href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css"
		rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css"
		rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css"
		rel="stylesheet">
	<link rel="icon" type="image/jpg"
		href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
</head>
<body>

	<%@ include file="/jsp/include/header.jsp"%>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i
				class="mr-5 ion-ios-home"></i>Inicio<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10"
				href="${pageContext.request.contextPath}/preguntas">Preguntas<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10 color-ash"
				href="#"><c:out value="${requestScope.pregunta.enunciado}" /></a>
		</div>
		<!-- container -->
	</section>

	<section>
		<div class="container">

				<div class="row">
					<div class="col-12 col-lg-8">
						<h3 class="mb-10">
							<b><c:out value="${requestScope.pregunta.enunciado}" /></b>
						</h3>
						
						<form name="perfilAutor" action="${pageContext.request.contextPath}/perfil" method="post">
	                    	<input type="hidden" name="alias" value="<c:out value='${requestScope.autorAlias}'/>"/>
	                    	<p><i><b>Autor: </b>
		                   		<button class="link-brdr-btm-primary color-primary mb-15" type="submit"><c:out value='${requestScope.autorCompleto}'/></button>
							</i></p>
	                    </form>
	                    
						<c:if test="${not empty requestScope.errorArriba}">
							<i class="mtb-10 ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
							<c:out value="${requestScope.errorArriba}"/>
							</span>
						</c:if>

						<form class="pregunta" name="contestarPregunta"
							action="${pageContext.request.contextPath}/preguntas/contestar"
							method="post">

							<input type="hidden" name="id" value="${requestScope.pregunta.idContenido}" />
							
							<!-- Respuestas -->
							<c:forEach items="${requestScope.respuestas}" var="respuesta" varStatus="loop">
							
								<c:set var="resN">res${loop.index + 1}</c:set>
								<c:set var="resCorrectaN">resCorrecta${loop.index + 1}</c:set>
								<c:set var="idRespuestaN">idRespuesta${loop.index + 1}</c:set>
	
								<div class="row mt-15 mt-sm-10">
									<%-- Número de respuesta para usuarios no registrados,
										 checkboxes para usuarios registrados sin contestar
										 y ticks verdes / cruces rojas para usuarios registrados que hayan contestado --%>
									<div class="col-2 col-md-1">
										<c:if test="${requestScope.usuarioNoReg}">
											<span class="float-right"><b><c:out value="${loop.index + 1}"/>)</b></span>
										</c:if>
										<c:if test="${not requestScope.usuarioNoReg and not requestScope.contestada}">
											<input class="float-right" type="checkbox"
												name="<c:out value="${resCorrectaN}"/>" />
											<input type="hidden"
												name="<c:out value="${idRespuestaN}"/>"
												value="${respuesta.idRespuesta}" />
										</c:if>
										<c:if test="${not requestsScope.usuarioNoReg and requestScope.contestada}">
											<c:if test="${respuesta.correcta}">
												<i class="float-right lh-40 pt-5 ion-checkmark color-primary mr-10"></i>
											</c:if>
											<c:if test="${not respuesta.correcta}">
												<i class="float-right lh-40 pt-5 ion-close color-red mr-10"></i>
											</c:if>
										</c:if>
									</div>
	
									<!-- Señalar las respuestas del usuario que estén mal-->
									<div class="col-9 col-md-10
										<c:if test="${not requestsScope.usuarioNoReg and requestScope.contestada}">
											<c:if test="${not requestScope[resCorrectaN]}">
												alert alert-danger
											</c:if>
											<c:if test="${requestScope[resCorrectaN]}">
												alert alert-success
											</c:if>
										</c:if>">
										<!-- Señalar las respuestas correctas de la pregunta-->
										<p><c:out value="${respuesta.enunciado}" /></p>
									</div>
								</div>
	
							</c:forEach>
							
							<c:if test="${requestScope.usuarioNoReg}">
								<div class="row mt-20 mt-sm-40">
									<p class="mb-30">
										<i>¿Quieres saber la respuesta?
											<a class="link-brdr-btm-primary color-primary" href="${pageContext.request.contextPath}/iniciar-sesion">Inicia sesión</a>
											para responder</i>
									</p>
								</div>
							</c:if>
							<c:if test="${not requestScope.usuarioNoReg and not requestScope.contestada}">
								<div class="row mt-20 mt-sm-40">
									<div class="col-12 col-md-6">
										<button class="mb-10 w-100 btn-fill-primary font-sans"
											type="submit">
											<b>Validar pregunta</b>
										</button>
									</div>
									<div class="col-12 col-md-6">
										<a class="mb-10 w-100 btn-fill-grey"
											href="${pageContext.request.contextPath}/preguntas">
											<b>No responder y pasar</b>
										</a>
									</div>
								</div>
							</c:if>
							
						</form>

					</div>
					
					<c:if test="${sessionScope.usuario.tipoUsuario == 'ADMINISTRADOR'}">
						<div class="col-12 col-lg-4">
							<form name="borrarContenido" action="${pageContext.request.contextPath}/preguntas/borrar" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.pregunta.idContenido}"/>"/>
								<input type="hidden" name="redirect" value="preguntas"/>
								<button class="mt-md-30 w-100 btn-brdr-red" type="submit"><i class="ion-trash-b mr-10"></i><b>Borrar contenido</b></button>
							</form>
						</div>
					</c:if>
				</div>

		</div>
		<!-- container -->

		<!-- COMENTARIOS -->
		<%@ include file="/jsp/include/comentarios.jsp"%>

	</section>

	<%@ include file="/jsp/include/footer.jsp"%>

	<!-- SCRIPTS -->
	<script
		src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
