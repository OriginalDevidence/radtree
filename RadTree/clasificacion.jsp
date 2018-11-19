<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Clasificación - RadTree</title>
	<meta name="description" content="Clasificación de usuarios por puntos obtenidos al responder preguntas">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">
	
	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">
	
	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
</head>
<body>
	
	<%@ include file="WEB-INF/header.jsp" %>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="."><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Clasificación</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			
			<div class="row">
				<div class="col-sm-12">
					<h3 class="mb-30"><b>Clasificación</b></h3>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<p>Puedes obtener puntos respondiendo a las <a class="link-brdr-btm-primary color-primary" href="${pageContext.request.contextPath}/preguntas">preguntas</a> de la web.
					Los usuarios que hayan conseguido más puntos aparecerán aquí.</p>
				</div>
				<div class="col-12 col-md-8 offset-md-2">
					<div class="oflow-auto">
						<table class="w-100 clasificacion mt-30">
							<tr class="bg-primary">
								<th>Posición</th>
								<th>Usuario</th>
								<th>Preguntas contestadas</th>
								<th>Puntuación</th>
							</tr>
							<c:forEach items="${requestScope.clasificacion}" var="row" varStatus="loop">
								<tr>
									<td><c:out value="${loop.index + 1}"/></td>
									<td>
										<form name="perfil" action="${pageContext.request.contextPath}/perfil" method="post">
											<input type="hidden" name="alias" value="<c:out value='${row.alias}'/>"/>
											<button class="color-primary" type="submit">
												<c:out value="${row.alias}"/>
											</button>
										</form>
									</td>
									<td><c:out value="${row.preguntasContestadas}"/></td>
									<td><c:out value="${row.puntuacion}"/></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>

		</div><!-- container -->
	</section>
	
	<%@ include file="WEB-INF/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>
	
</body>
</html>