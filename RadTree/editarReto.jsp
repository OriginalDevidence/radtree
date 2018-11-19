<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Editar reto - RadTree</title>
	<meta name="description" content="Formulario para la edición de retos">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

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
			<a class="mt-10" href=".."><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="../gestion-contenido">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Editar reto</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b>Editar reto</b></h3>
					<form name="editarReto" action="${pageContext.request.contextPath}/gestion-contenido/editar-reto/editar" method="post">
				
						<input type="hidden" name="idContenido" value="<c:out value="${requestScope.reto.idContenido}"/>"/>

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-8">
								<label for="titulo">Título del reto</label>
								<c:if test="${not empty requestScope.errores.get('titulo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('titulo')}"/>
									</span>
								</c:if>
								<input type="text" name="titulo" placeholder="Título" value="<c:out value="${requestScope.reto.titulo}"/>"/>
							</div>

							<div class="col-sm-12">
								<label for="reto">Planteamiento del reto</label>
								<c:if test="${not empty requestScope.errores.get('cuerpo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('cuerpo')}"/>
									</span>
								</c:if>
								<textarea class="p-10" name="cuerpo"><c:out value="${requestScope.reto.cuerpo}"/></textarea>
							</div>

						</div>

						<div class="row">
							<div class="col-sm-12 mt-30">
								<p class="mb-20"><i>
									Recuerda que todos los contenidos deben ser aprobados previamente por un administrador antes de ser visibles para el público.
									Este proceso puede tomar un tiempo, así que ten paciencia.
								</i></p>
								<button class="w-100 btn-fill-primary" type="submit"><b>Finalizar edición</b></button>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div><!-- container -->
	</section>

	<%@ include file="WEB-INF/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
