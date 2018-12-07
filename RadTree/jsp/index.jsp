<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Inicio - RadTree</title>
	<meta name="description" content="RadTree es un proyecto de la Universidad de Zaragoza">
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
	
	<section class="pt-0">
	<div class="container">
		<div class="row">
			<div class="col-12 col-lg-8">
			
				<div class="h-600x h-sm-auto">
		
					<div class="h-100 w-100 float-left float-sm-none oflow-hidden">
				
						<!-- NOTICIAS -->
						<div class="pb-5 pr-5 pr-sm-0 float-left float-sm-none w-100 w-sm-100 h-2-3 h-sm-300x">
							<div class="pos-relative h-100 dplay-block">
								<form name="noticia" action="${pageContext.request.contextPath}/noticias/ver" method="post">
									<input type="hidden" name="id" value="<c:out value="${requestScope.indexNoticia1.idContenido}"/>"/>
									<button type="submit">
										<div class="img-bg bg-grad-layer-6" style="background: url(<c:out value="${requestScope.indexNoticia1.urlImagen}"/>) no-repeat center; background-size: cover;"></div>
										
										<div class="abs-blr color-white p-20 bg-sm-color-7">
											<h3 class="w-100 mb-10 font-14 font-sm-13 font-sans text-left"><b><c:out value="${requestScope.indexNoticia1.titulo}"/></b></h3>
											<ul class="float-left list-li-mr-20">
												<li class="float-left text-left font-11 font-sans mt-5"><c:out value="${requestScope.indexNoticia1.fechaRealizacion}"/></li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-ios-bolt"></i>
													<c:out value="${requestScope.indexNoticia1.numVisitas}"/>
												</li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-chatbubbles"></i>
													<c:out value="${requestScope.indexNoticia1.numComentarios}"/>
												</li>
											</ul>
										</div>
									</button>
								</form>
							</div>
						</div>
						
						<div class="pr-5 pr-sm-0 pt-5 float-left float-sm-none pos-relative w-50 w-sm-100 h-1-3 h-sm-300x">
							<div class="pos-relative h-100 dplay-block">
								<form name="noticia" action="${pageContext.request.contextPath}/noticias/ver" method="post">
									<input type="hidden" name="id" value="<c:out value="${requestScope.indexNoticia2.idContenido}"/>"/>
									<button type="submit">
										<div class="img-bg bg-grad-layer-6" style="background: url(<c:out value="${requestScope.indexNoticia2.urlImagen}"/>) no-repeat center; background-size: cover;"></div>
										
										<div class="abs-blr color-white p-20 bg-sm-color-7">
											<h3 class="w-100 mb-10 float-left font-13 font-sans text-left"><b><c:out value="${requestScope.indexNoticia2.titulo}"/></b></h3>
											<ul class="float-left list-li-mr-20">
												<li class="float-left text-left font-11 font-sans mt-5"><c:out value="${requestScope.indexNoticia2.fechaRealizacion}"/></li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-ios-bolt"></i>
													<c:out value="${requestScope.indexNoticia2.numVisitas}"/>
												</li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-chatbubbles"></i>
													<c:out value="${requestScope.indexNoticia2.numComentarios}"/>
												</li>
											</ul>
										</div>
									</button>
								</form>
							</div>
						</div>
						
						<div class="pr-5 pr-sm-0 pt-5 float-left float-sm-none pos-relative w-50 w-sm-100 h-1-3 h-sm-300x">
							<div class="pos-relative h-100 dplay-block">
								<form name="noticia" action="${pageContext.request.contextPath}/noticias/ver" method="post">
									<input type="hidden" name="id" value="<c:out value="${requestScope.indexNoticia3.idContenido}"/>"/>
									<button type="submit">
										<div class="img-bg bg-grad-layer-6" style="background: url(<c:out value="${requestScope.indexNoticia3.urlImagen}"/>) no-repeat center; background-size: cover;"></div>
										
										<div class="abs-blr color-white p-20 bg-sm-color-7">
											<h3 class="w-100 mb-10 float-left font-13 font-sans text-left"><b><c:out value="${requestScope.indexNoticia3.titulo}"/></b></h3>
											<ul class="float-left list-li-mr-20">
												<li class="float-left text-left font-11 font-sans mt-5"><c:out value="${requestScope.indexNoticia3.fechaRealizacion}"/></li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-ios-bolt"></i>
													<c:out value="${requestScope.indexNoticia3.numVisitas}"/>
												</li>
												<li class="float-left text-left font-11 font-sans">
													<i class="color-primary mr-5 font-12 ion-chatbubbles"></i>
													<c:out value="${requestScope.indexNoticia3.numComentarios}"/>
												</li>
											</ul>
										</div>
									</button>
								</form>
							</div>
						</div>
		
					</div>
				</div>
			</div>
					
			<div class="col-12 col-lg-4">
				<div class="h-100 w-100 float-left float-sm-none oflow-hidden">
	
					<div class="pl-5 pl-sm-0 pt-0 pt-sm-20 float-left float-sm-none pos-relative w-100 w-sm-100 h-100 h-sm-100">
						<div class="pt-10 p-sm-10">
							<h4 class="p-title mb-20"><b>Pregunta del día</b></h4>
							<h5 class="mb-5"><c:out value="${requestScope.indexPregunta.enunciado}"/></h5>
							<form name="verPregunta" action="${pageContext.request.contextPath}/preguntas/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.indexPregunta.idContenido}"/>"/>
								<button type="submit" class="mt-15 color-primary link-brdr-btm-primary"><b>Responder a la pregunta</b></button>
							</form>
							<hr class="mtb-25">
							<h4 class="p-title mb-20"><b>Reto del día</b></h4>
							<h5 class="mb-10"><b><c:out value="${requestScope.indexReto.titulo}"/></b></h5>
							<p><c:out value="${requestScope.indexReto.cuerpo}"/></p>
							<form name="verReto" action="${pageContext.request.contextPath}/retos/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.indexReto.idContenido}"/>"/>
								<button type="submit" class="mt-15 color-primary link-brdr-btm-primary"><b>Ver el reto</b></button>
							</form>
						</div>
					</div>
	
				</div>
			</div>
		</div>
		<div class="row">
		
			<div class="col-md-12 col-lg-8 mt-30">
				<h4 class="p-title"><b>¿Quiénes somos?</b></h4>
				<div class="row">
				
					<div class="col-12">
						<p class="mb-10 text-justify">RadTree es un proyecto de la Universidad de Zaragoza cuya misión es intentar concienciar más al público sobre los peligros medioambientales y sus mitos relacionados, y como llegar a un mundo sostenible. Puedes participar respondiendo a preguntas o participando en los retos planteados.</p>
						<p class="mb-10 text-justify">Respondiendo a las preguntas participas en una clasificación global de todos los usuarios.</p>
						<p>¿Te crees capaz de llegar a la cima?</p>
					</div>
	
				</div>
				<a class="w-100 btn-brdr-primary mt-30 plr-20" href="${pageContext.request.contextPath}/quienes-somos"><b>¡Haz click aquí si quieres saber más!</b></a>
			</div>
			
			<div class="d-none d-md-block d-lg-none col-md-3"></div>
			<div class="col-md-12 col-lg-4 mt-30">
				<div class="mb-md-0">
					<h4 class="p-title"><b>Newsletter</b></h4>
					<p class="mb-20">¡Suscríbete a nuestra newsletter para recibir notificaciones sobre actualizaciones, nuevas preguntas y mucho más!</p>
					<p class="mb-20"><i>(Nota: no está implementado)</i></p>
					<form class="nwsltr-primary-1" method="post">
						<input type="text" placeholder="Email"/>
						<button><i class="ion-ios-paperplane"></i></button>
					</form>
				</div>
			</div>
			
		</div>
	</div>
	</section>

	<%@ include file="/jsp/include/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>
	
</body>
</html>