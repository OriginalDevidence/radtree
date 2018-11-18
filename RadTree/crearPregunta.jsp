<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE HTML>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Crear pregunta</title>
<meta name="description"
	content="Formulario para la creación de preguntas">
<meta name="author"
	content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

<!-- Font -->
<link
	href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700"
	rel="stylesheet">

<!-- Stylesheets -->
<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
</head>

<body>
	<%@ include file="WEB-INF/header.jsp"%>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10"
				href="${pageContext.request.contextPath}/gestion-contenido">Gestionar contenido<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10 color-ash"
				href="">Crear pregunta</a>
		</div>
		<!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30">
						<b>Crear pregunta</b>
					</h3>
					<form name="crearPregunta" action="${pageContext.request.contextPath}/gestion-contenido/crear-pregunta/crear" method="post">

						<div
							class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">
							
							
							<c:if test="${not empty requestScope.erroresArriba}">
								<div class="col-10 offset-1 mb-30 alert alert-danger">
									<ul>
										<c:forEach items="${requestScope.erroresArriba}" var="errorArriba">
										    <li><i class="ml-10 ion-close color-red"></i>
											<c:out value="${requestScope.erroresArriba.get('errorArriba')}"/></li>
										</c:forEach>
									</ul>
								</div>
							</c:if>
							
							<div class="col-12">
								<label for="titulo">Título de la pregunta</label>

								<c:if test="${not empty requestScope.errores.get('enunciado')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('enunciado')}"/>
									</span>
								</c:if>
							
								<input class="mt-5" type="text" name="enunciado"
									placeholder="Título de la pregunta"
									value="<c:out value="${param.enunciado}"/>" />
							</div>


							<c:if test = "${respuestasTotales == null}">
								<c:set var = "respuestasTotales" scope = "request" value = "${2}"/>
							</c:if>


							<input type="hidden" name="respuestasTotales" value = "${respuestasTotales}"/>

							<c:forEach var = "i" begin = "1" end = "${respuestasTotales}">
        						

								<div class="pl-50 pl-sm-25 col-10">
									<label for="res<c:out value = "${i}"/>">Respuesta <c:out value = "${i}"/></label> 
									
									<c:if test="${not empty requestScope.errores.get('respuesta' += i)}">
										<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
										<c:out value="${requestScope.errores.get('respuesta' += i)}"/>
										</span>
									</c:if>
									
									<input type="text"
										name="res<c:out value = "${i}"/>" placeholder="Respuesta <c:out value = "${i}"/>" 
										<c:set var="resN">res${i}</c:set>
										value="<c:out value="${param[resN]}"/>" />
								</div>
							
								<div class="col-2">
									<input class="mt-30" type="checkbox" name="correcta<c:out value = "${i}"/>"/>
								</div>
							</c:forEach>
							
							<div class="pl-50 pl-sm-25 col-12">
								<button class="plr-10 btn-b-md btn-fill-primary" type="submit"
									name="button" value="annadirRespuesta">
									<i class="ion-plus-round"></i>
								</button>
								<button class="plr-10 btn-b-md btn-fill-primary" type="submit"
									name="button" value="quitarRespuesta">
									<i class="ion-minus-round"></i>
								</button>
							</div>

						</div>

						<div class="row">
							<div class="col-12 mt-30">
							
								<c:if test="${not empty requestScope.errores.get('idAutor')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('idAutor')}"/>
									</span>
								</c:if>
								
								<button class="w-100 btn-fill-primary" type="submit" name="button" value="crearPregunta">
									<b>Crear pregunta</b>
								</button>

							</div>
						</div>

					</form>
				</div>
			</div>

		</div>
		<!-- container -->
	</section>


	<%@ include file="WEB-INF/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>