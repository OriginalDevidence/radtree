<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>Crear pregunta - RadTree</title>
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
	<link rel="icon" type="image/jpg" href="${pageContext.request.contextPath}/images/RadTree_Logo_x32.jpg" />
</head>

<body onload="location.href='#elemento-salto';">
	<%@ include file="/jsp/include/header.jsp"%>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10"
				href="${pageContext.request.contextPath}/gestion-contenido">Gestionar contenido<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10 color-ash"
				href="#">Crear pregunta</a>
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

						<c:if test="${respuestasTotales == null}">
							<c:set var="respuestasTotales" scope="request" value="${2}"/>
						</c:if>
						<input type="hidden" name="respuestasTotales" value="${respuestasTotales}"/>

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">
							
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
							
							<!-- TÍTULO -->
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

							<!-- RESPUESTAS -->
							<c:forEach var="i" begin="1" end="${respuestasTotales}">
								<c:set var="resN">res<c:out value="${i}"/></c:set>
								<c:set var="correctaN">correcta<c:out value="${i}"/></c:set>
        						
								<div class="pl-50 pl-sm-25 col-10">
									<label for="<c:out value="${resN}"/>">Respuesta <c:out value = "${i}"/></label> 
									<c:if test="${not empty requestScope.errores.get('respuesta' += i)}">
										<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
										<c:out value="${requestScope.errores.get('respuesta' += i)}"/>
										</span>
									</c:if>
									<input type="text" name="<c:out value ="${resN}"/>"
										placeholder="Respuesta <c:out value = "${i}"/>" 
										value="<c:out value="${param[resN]}"/>" />
								</div>
							
								<div class="col-2">
									<input class="mt-30" type="checkbox" name="<c:out value="${correctaN}"/>"
										<c:if test="${param[correctaN] == 'on'}">checked</c:if>/>
								</div>
							</c:forEach>
							
							<div class="pl-50 pl-sm-25 col-12">
								<button class="plr-10 btn-b-md btn-fill-primary" type="submit"
									name="button" value="annadirRespuesta" > 
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
								<p class="mb-20"><i>
									Recuerda que todos los contenidos deben ser aprobados previamente por un administrador antes de ser visibles para el público.
									Este proceso puede tomar un tiempo, así que ten paciencia.
								</i></p>
								<button class="w-100 btn-fill-primary" name="button" value="crearPregunta" type="submit"><b>Crear pregunta</b></button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>
		<!-- container -->
	</section>


	<%@ include file="/jsp/include/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>

</body>
</html>