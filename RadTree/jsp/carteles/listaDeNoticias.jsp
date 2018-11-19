<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--  TODO -->
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Noticias - RadTree</title>
	<meta name="description" content="Listado de todas las noticias">
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
			<a class="mt-10" href="${pageContext.request.contextPath}"><i
				class="mr-5 ion-ios-home"></i>Inicio<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10 color-ash"
				href="#">Noticias</a>
		</div>
		<!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<!-- Barra de búsqueda -->
				<div class="col-12 col-md-8 offset-0 offset-md-2 mb-50">
					<form name="busqueda" action="${pageContext.request.contextPath}/noticias" method="post">
						<div
							class="row form-block form-plr-15 form-h-45 form-brdr-lite-white">

							<c:if test="${empty param.busqueda}">
								<div class="col-9 col-md-10">
									<input class="w-100" type="text" name="busqueda"
										placeholder="Escribe aquí para buscar...">
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
									<i class="ion-search"></i></a>
								</button>
							</div>
						</div>
					</form>
				</div>
				<!-- NOTICIAS: Una div de este estilo por noticia -->
				<div class="col-12 col-md-6 mb-30 mb-sm-10">
					<div class="h-300x">
						<c:if test="${not empty requestScope.noticias}">
							<ul class="lista-titulos">
								<c:forEach items="${requestScope.noticias}" var="reto">
									<a class="pos-relative h-100 dplay-block"
										href="51_noticia.html">
										<div class="img-bg bg-2 bg-grad-layer-6"></div>

										<div class="abs-blr color-white p-20 bg-sm-color-7">
											<h4 class="mb-10 mb-sm-5">
												<b><c:out value="${noticia.titulo}" /></b>
											</h4>
											<ul class="list-li-mr-20">
												<li>${noticia.fechaRealizacion}</li>
												<li><i
													class="color-primary mr-5 font-12 ion-chatbubbles"></i> <c:out
														value="${reto.numComentarios}" /> <c:if
														test="${reto.numComentarios == 1}">
                           comentario
                        </c:if> <c:if test="${reto.numComentarios != 1}">
                           comentarios
                        </c:if>
											</ul>
										</div>
										<!--abs-blr -->
									</a>
									<!-- pos-relative -->
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${empty requestScope.noticias}">
							<p>
								<i>Parece que no hay nada por aquí...</i>
							</p>
						</c:if>
					</div>
				</div>


			</div>
			<!-- container -->
	</section>
	<%@ include file="/jsp/include/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
