<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.pregunta.enunciado}"/> - RadTree</title>
	<meta name="description" content="Una de las preguntas hechas por los usuarios de RadTree">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
	<link rel="icon" type="image/jpg" href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
</head>
<body>

	<%@ include file="/jsp/include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="${pageContext.request.contextPath}/preguntas">Preguntas<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#"><c:out value="${requestScope.pregunta.enunciado}"/></a>
		</div><!-- container -->
	</section>


	<section>
		<div class="container">
			
			<form class="pregunta" name="contestarPregunta" action="${pageContext.request.contextPath}/preguntas/contestar" method="post">
			
			<div class="row">
				<div class="col-md-12" >
					<h3 class="mb-10"><b><c:out value="${requestScope.pregunta.enunciado}"/></b></h3>
					<p class="mb-30"><i><c:out value="${requestScope.autorCompleto}"/></i></p>
					
						<c:set var = "cuenta" value = "${0}"/>
						<input type="hidden" name="idPregunta" value="${requestScope.pregunta.idContenido}"/>
						<c:forEach items="${requestScope.respuestas}" var="respuesta">
							<c:set var = "cuenta" value = "${cuenta + 1}"/>	
							
							<div class="row mt-10">
								<div class="col-2 col-sm-1 ">
									<c:if test="${requestScope.contestada == false}">
										<input type="checkbox" name="resCorrecta<c:out value="${cuenta}"/>"/>
									</c:if>
									
									<input type="hidden" name="idRespuesta<c:out value="${cuenta}"/>" value="${respuesta.idRespuesta}"/>
								</div>
								
								<!-- Señalar las respuestas del usuario que estén mal-->
								<c:set var="resN">respCorrecta${cuenta}</c:set>
								<div class="col-10 col-sm-10
									<c:if test="${requestScope.contestada}">
										<c:if test="${not requestScope[resN]}">
											alert alert-danger
										</c:if>
										<c:if test="${requestScope[resN]}">
											alert alert-success
										</c:if>
									</c:if>
								">
								<!-- Señalar las respuestas correctas de la pregunta-->
									<p>
									<c:if test="${requestScope.contestada}">
										<c:if test="${respuesta.correcta}">
											<i class="ion-checkmark color-primary mr-10"></i>
										</c:if>
										<c:if test="${not respuesta.correcta}">
											<i class="ion-close color-red mr-10"></i>
										</c:if>
									</c:if>
									<c:out value="${respuesta.enunciado}"/>
									</p>
								</div>
							</div>
							
						</c:forEach>
						<input type="hidden" name="respuestasTotales" value="${cuenta}"/>
					
				</div>
            </div>
            
			<div class="row mt-20 mt-sm-40">
				<c:if test="${not requestScope.contestada}">
					<div class="col-12 col-md-4">
						<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-primary" type="submit"
							name="button" value="validarRespuesta"><b>Validar pregunta</b>
						</button>
					</div>
					<div class="col-12 col-md-4">
						<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-grey" type="submit"><b>No responder y pasar</b></button>
					</div>
				</c:if>
				<c:if test="${requestScope.contestada}">
					<div class="col-12 col-md-4">
						<button class="mb-10 plr-90 plr-sm-10 w-100 btn-fill-primary" type="submit"><b>Seguir Contestando</b></button>
					</div>
				</c:if>
			</div>

			<div class="row">
				<div class="col-12">
				</div>
			</div>
			</form>
			
			
		</div><!-- container -->

		<!-- COMENTARIOS -->
		<%@ include file="/jsp/include/comentarios.jsp" %>

	</section>

	<%@ include file="/jsp/include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
