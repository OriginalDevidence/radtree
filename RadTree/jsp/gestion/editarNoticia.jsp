<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Editar noticia - RadTree</title>
	<meta name="description" content="Formulario para la edición de noticias">
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

	<%@ include file="/jsp/include/header.jsp"%>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}/"><i class="mr-5 ion-ios-home"></i>Inicio<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10"
				href="${pageContext.request.contextPath}/gestion-contenido">Gestionar contenido<i
				class="mlr-10 ion-chevron-right"></i></a> <a class="mt-10 color-ash"
				href="#">Editar noticia</a>
		</div>
		<!-- container -->
	</section>


	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8 mb-40">
					<h3 class="mb-30"><b>Editar noticia</b></h3>
					<form name="editarNoticia" action="${pageContext.request.contextPath}/gestion-contenido/editar-noticia/editar" method="post">

						<input type="hidden" name="id" value="<c:out value="${requestScope.noticia.idContenido}"/>"/>

						<div class="row form-block form-plr-15 form-h-45 form-mb-20">
							<div class="col-sm-12">
								<label for="titulo">Título de la Noticia</label>
								<c:if test="${not empty requestScope.errores.get('titulo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('titulo')}"/>
									</span>
								</c:if>
								<input
									class="brdr-grey" type="text" name="titulo"
									placeholder="Título"
									value="<c:out value="${requestScope.noticia.titulo}"/>" />
							</div>

							<div class="col-sm-12">
								<label for="cuerpo">Cuerpo</label>
								<c:if test="${not empty requestScope.errores.get('cuerpo')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('cuerpo')}"/>
									</span>
								</c:if>
								<textarea class="brdr-grey p-10" name="cuerpo"
									rows=8><c:out value="${requestScope.noticia.cuerpo}" /></textarea>
							</div>

							<div class="col-sm-12">
								<label for="url">URL de la fuente</label>
								<c:if test="${not empty requestScope.errores.get('url')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('url')}"/>
									</span>
								</c:if>
								<input
									class="brdr-grey" type="text" name="url"
									placeholder="URL de la fuente"
									value="<c:out value="${requestScope.noticia.url}"/>" />
							</div>

							<div class="col-sm-12">
								<label for="imagen">Imagen</label> <input class="m-5"
									name="imagen" type="file" placeholder="Subir imagen" accept=".jpg, .jpeg, .png">
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
                
                <div class="col-md-12 col-lg-4">
                	<div class="row">
                		<div class="col-12">
			                <h3 class="p-title mb-30">
								<b>Imagen de la noticia</b>
							</h3>
							<p class="mb-20">Los formatos aceptados son jpg, jpeg y png. Tamaño máximo de fichero 5 MB.</p>
							<form name="editarFoto" action="${pageContext.request.contextPath}/gestion-contenido/subir-imagen" method="post" enctype="multipart/form-data">
								<input class="m-5" type="file" name="imagen" accept=".jpg, .jpeg, .png"/>
								<input type="hidden" name="id" value="<c:out value="${requestScope.noticia.idContenido}"/>"/>
								<button class="mt-20 w-100 btn-fill-primary" type="submit"><b>Subir imagen</b></button>
							</form>
                		</div>
                		<div class="col-12 mt-40 mb-30">
                			<img class="w-100 h-auto" src="<c:out value="${requestScope.noticia.urlImagen}"/>" alt="Imagen de la noticia"/>
                		</div>
                	</div>
                </div>

			</div>

		</div><!-- container -->
	</section>

	<%@ include file="/jsp/include/footer.jsp"%>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
