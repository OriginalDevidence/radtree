<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Gestionar contenido - RadTree</title>
	<meta name="description" content="Menú para la gestión del contenido del sistema">
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
			<a class="mt-10 color-ash" href="#">Gestionar contenido</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h3 class="p-title mb-30"><b>Mi contenido</b></h3>
					<div class="row">
						<div class="col-sm-12 col-md-4 mb-5">
							<form name="verNoticias" action="${pageContext.request.contextPath}/gestion-contenido" method="post">
								<input type="hidden" name="tipoContenido" value="noticia"/>
								<c:if test="${requestScope.tipoContenido == 'noticia'}">
									<button type="submit" class="w-100 btn-fill-primary"><b>Mis noticias</b></button>
								</c:if>
								<c:if test="${requestScope.tipoContenido != 'noticia'}">
									<button type="submit" class="w-100 btn-fill-grey"><b>Mis noticias</b></button>
								</c:if>
							</form>
						</div>
						<div class="col-sm-12 col-md-4 mb-5">
							<form name="verNoticias" action="${pageContext.request.contextPath}/gestion-contenido" method="post">
								<input type="hidden" name="tipoContenido" value="pregunta"/>
								<c:if test="${requestScope.tipoContenido == 'pregunta'}">
									<button type="submit" class="w-100 btn-fill-primary"><b>Mis preguntas</b></button>
								</c:if>
								<c:if test="${requestScope.tipoContenido != 'pregunta'}">
									<button type="submit" class="w-100 btn-fill-grey"><b>Mis preguntas</b></button>
								</c:if>
							</form>
						</div>
						<div class="col-sm-12 col-md-4 mb-5">
							<form name="verNoticias" action="${pageContext.request.contextPath}/gestion-contenido" method="post">
								<input type="hidden" name="tipoContenido" value="reto"/>
								<c:if test="${requestScope.tipoContenido == 'reto'}">
									<button type="submit" class="w-100 btn-fill-primary"><b>Mis retos</b></button>
								</c:if>
								<c:if test="${requestScope.tipoContenido != 'reto'}">
									<button type="submit" class="w-100 btn-fill-grey"><b>Mis retos</b></button>
								</c:if>
							</form>
						</div>
					</div>
					<ul class="mtb-20">
						<c:if test="${requestScope.tipoContenido == 'noticia'}">
							<c:set var="contenidos" value="${requestScope.noticias}"/>
						</c:if>
						<c:if test="${requestScope.tipoContenido == 'pregunta'}">
							<c:set var="contenidos" value="${requestScope.preguntas}"/>
						</c:if>
						<c:if test="${requestScope.tipoContenido == 'reto'}">
							<c:set var="contenidos" value="${requestScope.retos}"/>
						</c:if>
						<c:if test="${empty contenidos}">
							<i>No has subido ningun contenido de este tipo todavía.</i>
						</c:if>
						<c:if test="${not empty contenidos}">
							<c:forEach items="${contenidos}" var="contenido" varStatus="loop">
								<li class="w-100 mtb-10">
									<ul>
										<li><h4 class="mr-10">
											<c:if test="${contenido.estado == 'PENDIENTE'}">
												<i class="color-red ion-alert-circled mr-5"></i><span class="color-red mr-10">No validado</span>
											</c:if>
											<c:if test="${contenido.estado == 'BORRADO'}">
												<i class="color-lite-black ion-trash-b mr-5"></i><span class="color-lite-black mr-10">Borrado</span>
											</c:if>
											<c:if test="${requestScope.tipoContenido == 'noticia'}">
												<b><c:out value="Noticia #"/><c:out value="${loop.index + 1}"/></b>
											</c:if>
											<c:if test="${requestScope.tipoContenido == 'pregunta'}">
												<b><c:out value="Pregunta #"/><c:out value="${loop.index + 1}"/></b>
											</c:if>
											<c:if test="${requestScope.tipoContenido == 'reto'}">
												<b><c:out value="Reto #"/><c:out value="${loop.index + 1}"/></b>
											</c:if>
										</h4></li>
										<c:if test="${contenido.estado == 'VALIDADO'}">
											<li>
												<form name="ver"
													action="${pageContext.request.contextPath}/<c:out value="${requestScope.contenidoPathNameView}"/>/ver"
													method="post">
													<input type="hidden" name="id"
														value="<c:out value="${contenido.idContenido}"/>" />
													<button type="submit"
														class="plr-10 btn-b-sm btn-fill-primary">
														<i class="mr-5 ion-eye"></i>Ver
													</button>
												</form>
											</li>
										</c:if>
										<li>
											<form name="editar"
												action="${pageContext.request.contextPath}/gestion-contenido/editar-<c:out value="${requestScope.contenidoPathNameEdit}"/>"
												method="post">
												<input type="hidden" name="id"
													value="<c:out value="${contenido.idContenido}"/>" />
												<button type="submit"
													class="plr-10 btn-b-sm btn-fill-primary">
													<i class="mr-5 ion-edit"></i>Editar
												</button>
											</form>
										</li>
									</ul>
									<p class="mt-10">
										<c:if test="${tipoContenido == 'noticia'}">
											<c:out value="${contenido.titulo}"/>
										</c:if>
										<c:if test="${tipoContenido == 'pregunta'}">
											<c:out value="${contenido.enunciado}"/>
										</c:if>
										<c:if test="${tipoContenido == 'reto'}">
											<c:out value="${contenido.titulo}"/>
										</c:if>
									</p>
								</li>
							</c:forEach>
						</c:if>
					</ul>
					
				</div>

				<div class="col-md-6 col-lg-4">
					<c:if test="${sessionScope.usuario.tipoUsuario == 'ADMINISTRADOR'}">
						<h3 class="p-title mb-30">
							<b>Validar contenido</b>
						</h3>
						<p class="mb-25">
							Actualmente hay <b><c:out
									value="${requestScope.numInValidacion}" /></b> 
							<c:if test="${requestScope.numInValidacion == 1}">elemento</c:if>
							<c:if test="${requestScope.numInValidacion != 1}">elementos</c:if>
							en la cola de validación.
						</p>
						<a class="w-100 mb-40 btn-fill-primary"
							href="${pageContext.request.contextPath}/gestion-contenido/cola-validacion"><b>Ver
								cola de validación</b></a>
					</c:if>
					<h3 class="p-title mb-30">
						<b>Gestionar contenido</b>
					</h3>
					<p class="mb-25">Todos los contenidos subidos han de ser
						aprobados previamente por un administrador para poder ser
						mostrados en la web.</p>
					<a class="w-100 mb-15 btn-fill-primary"
						href="${pageContext.request.contextPath}/gestion-contenido/crear-noticia"><b>Crear
							noticia</b></a> <a class="w-100 mb-15 btn-fill-primary"
						href="${pageContext.request.contextPath}/gestion-contenido/crear-pregunta"><b>Crear
							pregunta</b></a> <a class="w-100 mb-15 btn-fill-primary"
						href="${pageContext.request.contextPath}/gestion-contenido/crear-reto"><b>Crear
							reto</b></a>
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