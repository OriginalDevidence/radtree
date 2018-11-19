<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Cola de validación - RadTree</title>
	<meta name="description" content="Cola de validación de contenidos para los administradores">
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
			<a class="mt-10" href=".."><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="../gestion-contenido">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Cola de validación</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			
			<div class="row">
				<div class="col-12">
					<h3 class="p-title mb-30"><b>Cola de validación</b></h3>
				</div>
				<c:if test="${requestScope.numInValidacion == null or requestScope.numInValidacion == 0}">
					<p class="ml-20 pr-20"><i>No hay elementos en la cola de validación. Cuando un usuario inserte o actualice cualquier contenido, aparecerá aquí.</i></p>
				</c:if>
				<c:if test="${requestScope.numInValidacion != null and requestScope.numInValidacion != 0}">
					<div class="col-12 col-md-4">
						<ul class="mb-20">
							<c:if test="${requestScope.elemento > 1}">
								<li class="mr-5">
									<form name="colaAtras" action="${pageContext.request.contextPath}/gestion-contenido/cola-validacion" method="post">
										<input type="hidden" name="elemento" value="<c:out value='${requestScope.elemento - 1}'/>"/>
										<button type="submit" class="btn-b-sm btn-fill-grey plr-10"><i class="ion-arrow-left-a"></i></button>
									</form>
								</li>
							</c:if>
							<li class="mr-10">Elemento</li>
							<!-- Se puede emplear type="number" pero no permite usar size, como hay flechas a cada lado no importa -->
							<li class="mr-10">
								<form name="colaCustom" action="${pageContext.request.contextPath}/gestion-contenido/cola-validacion" method="post">
									<input class="form-brdr-grey text-center" type="text" name="elemento"
									value="<c:out value="${requestScope.elemento}"/>" size="1"/>
									<!-- <input type="submit"/> -->
								</form>
							</li>
							<li class="mr-5">de <c:out value="${requestScope.numInValidacion}"/> de la cola</li>
							<c:if test="${requestScope.elemento < requestScope.numInValidacion}">
								<li>
									<form name="colaDespues" action="${pageContext.request.contextPath}/gestion-contenido/cola-validacion" method="post">
										<input type="hidden" name="elemento" value="<c:out value='${requestScope.elemento + 1}'/>"/>
										<button type="submit" class="btn-b-sm btn-fill-grey plr-10"><i class="ion-arrow-right-a"></i></button>
									</form>
								</li>
							</c:if>
						</ul>
					</div>
					<div class="col-12 col-md-4">
						<form name="validacion" action="${pageContext.request.contextPath}/gestion-contenido/cola-validacion/validar" method="post">
							<div class="row">
								<input type="hidden" name="idContenido"
									value="${requestScope.contenido.idContenido}" />
								<div class="col-6">
									<button type="submit" name="accion" value="aprobar"
										class="w-100 btn-b-sm btn-fill-primary plr-10">
										<i class="mr-5 ion-checkmark"></i>Aprobar
									</button>
								</div>
								<div class="col-6">
									<button type="submit" name="accion" value="denegar"
										class="w-100 btn-b-sm btn-fill-primary plr-10">
										<i class="mr-5 ion-close"></i>Denegar
									</button>
								</div>
							</div>
						</form>
					</div>
					
					<div class="mt-20 col-12">
						<form name="perfil" action="${pageContext.request.contextPath}/perfil" method="post">
							<h5>
								<b>Autor: </b>
								<input type="hidden" name="alias" value="<c:out value="${requestScope.alias}"/>"/>
								<button type="submit" class="link-brdr-btm-primary color-primary"><b><c:out value="${requestScope.autorCompleto}"/></b></button>
							</h5>
						</form>
						<h5 class="mt-10 mr-15 mb-50"><b>Fecha:</b> <c:out value="${requestScope.contenido.fechaRealizacion}"/></h5>
						
						<c:if test="${not empty requestScope.noticia}">
							<h4 class="p-title mr-15"><b><span class="color-primary">Noticia:</span> <c:out value="${requestScope.noticia.titulo}"/></b></h4>
							<p class="mt-10 text-justify"><c:out value="${requestScope.noticia.cuerpo}"/></p>
							<p class="mt-10">
								<b>URL de la fuente: </b>
								<a class="link-brdr-btm-primary color-primary" href="<c:out value="${requestScope.noticia.url}"/>"><c:out value="${requestScope.noticia.url}"/></a>
							</p>
						</c:if>
						<c:if test="${not empty requestScope.reto}">
							<h4 class="p-title mr-15"><b><span class="color-primary">Reto:</span> <c:out value="${requestScope.reto.titulo}"/></b></h4>
							<p class="mt-10 text-justify"><c:out value="${requestScope.reto.cuerpo}"/></p>
						</c:if>
						<c:if test="${not empty requestScope.pregunta}">
							<h4 class="p-title mr-15"><b><span class="color-primary">Pregunta:</span> <c:out value="${requestScope.pregunta.enunciado}"/></b></h4>
							<p class="mtb-10 text-justify"><b>Respuestas:</b></p>
							<c:forEach items="${requestScope.respuestas}" var="respuesta">
								<p>
									<c:if test="${respuesta.correcta}">
										<i class="ion-checkmark color-primary"></i>
									</c:if>
									<c:if test="${not respuesta.correcta}">
										<i class="ion-close color-red"></i>
									</c:if>
									<c:out value="${respuesta.enunciado}"/>
								</p>
							</c:forEach>
						</c:if>
					</div>
				</c:if>
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