<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Usuarios - RadTree</title>
	<meta name="description" content="Listado de todos los retos">
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

	<%@ include file="include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Usuarios</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
                <!-- Barra de búsqueda -->
				<div class="col-12 col-md-8 offset-0 offset-md-2 mb-50">
					<form name="busqueda" action="${pageContext.request.contextPath}/usuarios" method="post">
						<div class="row form-block form-plr-15 form-h-45 form-brdr-lite-white mb-30">
							
							<c:if test="${empty param.busqueda}">
								<div class="col-9 col-md-10">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."/>
								</div>
							</c:if>
							<c:if test="${not empty param.busqueda}">
								<div class="col-2">
									<button class="w-100 btn-fill-primary" name="busqueda" value="" type="submit">
										<i class="ion-close"></i>
									</button>
								</div>
								<div class="col-7 col-md-8">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar..."
										value="<c:out value="${param.busqueda}"/>">
								</div>
							</c:if>
							
							<div class="col-3 col-md-2">
								<button class="w-100 btn-fill-primary" type="submit">
									<i class="ion-search"></i>
								</button>
							</div>
							
						</div>
					</form>
					<c:if test="${empty param.busqueda}">
						<p>Debes introducir algo en la barra de búsqueda primero.</p>
					</c:if>
					<c:if test="${not empty param.busqueda and empty requestScope.usuarios}">
						<p><i>No se han encontrado usuarios con esa búsqueda. Prueba a hacer algún cambio.</i></p>
					</c:if>
					<c:if test="${not empty param.busqueda and not empty requestScope.usuarios}">
						<div class="col-12 col-lg-8 offset-lg-2">
							<div class="oflow-auto">
								<table class="w-100 clasificacion mt-30">
									<tr class="bg-primary">
										<th>Alias</th>
										<th>Nombre</th>
										<th>Apellidos</th>
									</tr>
									<c:forEach items="${requestScope.usuarios}" var="usuario">
										<tr>
											<td>
												<form name="perfil" action="${pageContext.request.contextPath}/perfil" method="post">
													<input type="hidden" name="alias" value="<c:out value='${usuario.alias}'/>"/>
													<button class="color-primary" type="submit">
														<b><c:out value="${usuario.alias}"/></b>
													</button>
												</form>
											</td>
											<td><c:out value="${usuario.nombre}"/></td>
											<td><c:out value="${usuario.apellidos}"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</c:if>
				</div>
            </div>

		</div><!-- container -->
	</section>

	<%@ include file="include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
