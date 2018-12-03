<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Noticias - RadTree</title>
<meta name="description" content="Listado de todas las noticias">
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



<script>
	function showdivDisplay() {
		var mydiv = document.getElementById("mydiv");
		var mybutton = document.getElementById("mybutton");

		if (mydiv != null && mybutton != null) {
			mybutton.onclick = hidedivDisplay;
			mydiv.style.display = "";
		}

	}
	function hidedivDisplay() {
		var mydiv = document.getElementById("mydiv");
		var mybutton = document.getElementById("mybutton");

		if (mydiv != null && mybutton != null) {
			mybutton.onclick = showdivDisplay;
			mydiv.style.display = "none";
		}
	}
</script>
</head>

<body>

	<%@ include file="/jsp/include/header.jsp"%>

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
			<form name="busqueda"
				action="${pageContext.request.contextPath}/noticias" method="post">
				<div class="row">
					<!-- Barra de búsqueda -->
					<div class="col-12 col-md-8 offset-0 offset-md-2 mb-10">

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
									<button class="w-100 btn-fill-primary" name="busqueda" value=""
										type="submit">
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

					</div>
				</div>

				<div class="mx-2 mb-30">

					<!-- Abrir Filtros -->
					<button class="col-fit col-md-fit btn-fill-grey" id="mybutton"
						type="button" onclick="showdivDisplay()">
						<i class="ion-ios-settings-strong"></i> <b>Filtros</b>
					</button>

					<!-- Filtros -->
					<div id="mydiv" style="display: none" class="text-center">


						<b class="text alert-wo-margin alert-success"> <input
							class="mt-30" type="checkbox" name="filtroTitulo" /> Título
						</b> <b class="text alert-wo-margin alert-success"><input
							class="mt-30" type="checkbox" name="filtroCuerpo" /> Cuerpo </b> <b
							class="text alert-wo-margin alert-success"><input
							class="mt-30" type="checkbox" name="filtroUrl" /> URL</b>


					</div>

					<!-- Separador -->
					<h4 class="p-title"></h4>
				</div>

			</form>

			<div class="row">
				<c:if test="${not empty requestScope.noticias}">
					<c:forEach items="${requestScope.noticias}" var="noticia">

						<div class="col-12 col-md-6 mb-30 mb-sm-10">
							<div class="h-300x">
								<div class="pos-relative h-100 dplay-block">
									<form name="noticia"
										action="${pageContext.request.contextPath}/noticias/ver"
										method="post">
										<input type="hidden" name="id"
											value="<c:out value="${noticia.idContenido}"/>" />
										<button type="submit" name="buscar">
											<div class="img-bg bg-grad-layer-6"
												style="background: url(<c:out value="${noticia.urlImagen}"/>) no-repeat center; background-size: cover;"></div>

											<div class="abs-blr color-white p-20 bg-sm-color-7">
												<h4 class="mb-10 mb-sm-5 font-14 text-left font-sans">
													<b><c:out value="${noticia.titulo}" /></b>
												</h4>
												<ul class="list-li-mr-20">
													<li class="text-left float-left font-11 font-sans mt-5"><c:out
															value="${noticia.fechaRealizacion}" /></li>
													<li class="text-left float-left font-11 font-sans"><i
														class="color-primary mr-5 font-11 ion-ios-bolt"></i> <c:out
															value="${noticia.numVisitas}" /> <c:if
															test="${noticia.numVisitas == 1}">
						                                	 visita
						                                </c:if> <c:if
															test="${noticia.numVisitas != 1}">
						                                	 visitas
						                                </c:if></li>
													<li class="text-left float-left font-11 font-sans"><i
														class="color-primary mr-5 font-11 ion-chatbubbles"></i> <c:out
															value="${noticia.numComentarios}" /> <c:if
															test="${noticia.numComentarios == 1}">
						                                	 comentario
						                                </c:if> <c:if
															test="${noticia.numComentarios != 1}">
						                                	 comentarios
						                                </c:if></li>
												</ul>
											</div>
										</button>
									</form>
								</div>
							</div>
						</div>

					</c:forEach>
				</c:if>
			</div>
		</div>

		<div class="text-center">
			<c:if test="${empty currentPage}">
				<c:set var="currentPage" scope="request" value="${1}" />
			</c:if>

			<form name="paginacion"
				action="${pageContext.request.contextPath}/noticias" method="post">
				<input type="hidden" name="currentPage" value="${currentPage}" /> <input
					type="hidden" name="noOfPages" value="${noOfPages}" /> <input
					type="hidden" name="busquedaAnterior" value="${busquedaAnterior}" />


				<!--For displaying Previous link except for the 1st page -->
				<c:if test="${requestScope.currentPage ne 1}">
					<button class="col-fit col-md-fit btn-fill-primary" type="submit"
						name="button" value="primeraPagina">
						<i class="ion-chevron-left"></i><i class="ion-chevron-left"></i>
					</button>
					<button class="col-fit col-md-fit btn-fill-primary" type="submit"
						name="button" value="anteriorPagina">
						<i class="ion-chevron-left"></i>
					</button>
				</c:if>




				<!--For displaying Page numbers. 
			    The when condition does not display a link for the current page-->
				<c:set var="maxPagNum" value="${10}"></c:set>
				<c:set var="startPagNum" value="${1}"></c:set>

				<c:if test="${currentPage - 4 > 1}">
					<c:set var="startPagNum" value="${currentPage - 4}"></c:set>
					<c:set var="maxPagNum" value="${maxPagNum + currentPage - 4}"></c:set>
				</c:if>

				<c:if test="${maxPagNum > noOfPages}">
					<c:set var="maxPagNum" value="${noOfPages}"></c:set>
				</c:if>

				<c:forEach begin="${startPagNum}" end="${maxPagNum}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">

							<span class="text alert-wo-margin alert-success"><b>${i}</b></span>

						</c:when>
						<c:otherwise>

							<button class="col-fit col-md-fit btn-fill-primary" type="submit"
								name="irPagina" value="${i}">
								<b>${i}</b>
							</button>

						</c:otherwise>
					</c:choose>
				</c:forEach>



				<!--For displaying Next link -->
				<c:if test="${currentPage ne noOfPages}">

					<button class="col-fit col-md-fit btn-fill-primary" type="submit"
						name="button" value="siguientePagina">
						<i class="ion-chevron-right"></i>
					</button>
					<button class="col-fit col-md-fit btn-fill-primary" type="submit"
						name="button" value="ultimaPagina">
						<i class="ion-chevron-right"></i> <i class="ion-chevron-right"></i>
					</button>

				</c:if>

			</form>
		</div>

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
