<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>RadTree</title>
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
	
	<%@ include file="WEB-INF/header.jsp" %>
	
	<div class="container">

		<div class="h-600x h-sm-auto">

			<div class="w-2-3 w-sm-100 h-100 h-sm-100 float-left float-sm-none oflow-hidden">
		
				<!-- NOTICIAS -->
				<div class="pb-5 pr-5 pr-sm-0 float-left float-sm-none w-100 w-sm-100 h-2-3 h-sm-300x">
					<a class="pos-relative h-100 dplay-block" href="51_noticia.html">
					
						<div class="img-bg bg-1 bg-grad-layer-6"></div>
						
						<div class="abs-blr color-white p-20 bg-sm-color-7">
							<h3 class="mb-15 mb-sm-5 font-sm-13"><b>Secondary forests have short lifespans</b></h3>
							<ul class="list-li-mr-20">
								<li>by <span class="color-primary"><b>Olivia Capzallo, Missouri Botanical Garden</b></span> Jan 25, 2018</li>
								<li><i class="color-primary mr-5 font-12 ion-ios-bolt"></i>16,190</li>
								<li><i class="color-primary mr-5 font-12 ion-chatbubbles"></i>8</li>
							</ul>
						</div><!--abs-blr -->
					</a><!-- pos-relative -->
				</div><!-- h-2-3 -->
				
				<div class="pr-5 pr-sm-0 pt-5 float-left float-sm-none pos-relative w-50 w-sm-100 h-1-3 h-sm-300x">
					<a class="pos-relative h-100 dplay-block" href="51_noticia.html">
					
						<div class="img-bg bg-2 bg-grad-layer-6"></div>
						
						<div class="abs-blr color-white p-20 bg-sm-color-7">
							<h4 class="mb-10 mb-sm-5"><b>Feeding 10 billion people by 2050 within planetary limits may be achievable</b></h4>
							<ul class="list-li-mr-20">
								<li>Jan 25, 2018</li>
								<li><i class="color-primary mr-5 font-12 ion-ios-bolt"></i>20,390</li>
								<li><i class="color-primary mr-5 font-12 ion-chatbubbles"></i>30</li>
							</ul>
						</div><!--abs-blr -->
					</a><!-- pos-relative -->
				</div><!-- w-50 -->
				
				<div class="plr-5 plr-sm-0 pt-5 pt-sm-10 float-left float-sm-none pos-relative w-50 w-sm-100 h-1-3 h-sm-300x">
					<a class="pos-relative h-100 dplay-block" href="51_noticia.html">
					
						<div class="img-bg bg-3 bg-grad-layer-6"></div>
						
						<div class="abs-blr color-white p-20 bg-sm-color-7">
							<h4 class="mb-10 mb-sm-5"><b>Large-scale US wind power would cause warming that would take roughly a century to offset</b></h4>
							<ul class="list-li-mr-20">
								<li>Jan 25, 2018</li>
								<li><i class="color-primary mr-5 font-12 ion-ios-bolt"></i>19,140</li>
								<li><i class="color-primary mr-5 font-12 ion-chatbubbles"></i>26</li>
							</ul>
						</div><!--abs-blr -->
					</a><!-- pos-relative -->
				</div><!-- w-50 -->

			</div><!-- w-2-3 -->
			
			<div class="w-1-3 w-sm-100 h-100 h-sm-100 float-left float-sm-none oflow-hidden">

				<!-- PREGUNTAS -->
				<!-- TODO cambiar el h-sm-300x cuando tengamos las preguntas para que sea mas grande (hace falta?) -->
				<!-- TODO formulario de pregunta -->
				<div class="pl-5 pl-sm-0 pt-0 pt-sm-20 float-left float-sm-none pos-relative w-100 w-sm-100 h-100 h-sm-100">
					<div class="p-20 p-sm-10">
						<h4 class="p-title mb-20"><b>Pregunta del día</b></h4>
						<h5 class="mb-5">¿Cuántos árboles vas a plantar hoy?</h5>
						<p>a) 10</p>
						<p>b) 20</p>
						<p>c) 30</p>
						<p>d) 40</p>
						<p class="mt-5"><i>Autor: Martin Martínez</i></p>
						<a class="mt-15 color-primary link-brdr-btm-primary" href="53_pregunta.html">Ver la pregunta</a>
						<hr class="mtb-25">
						<h4 class="p-title mb-20"><b>Reto del día</b></h4>
						<p>Prueba a ir en bicicleta a la universidad en lugar de ir en coche por una semana! ...etc etc</p>
						<p class="mt-5"><i>Autor: Rodrigo Rodríguez</i></p>
						<a class="mt-15 color-primary link-brdr-btm-primary" href="55_reto.html">Ver el reto</a>
					</div>
				</div>

			</div>

		</div><!-- h-100vh -->
	</div><!-- container -->
	
	
	<section>
		<div class="container">
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h4 class="p-title"><b>¿Quiénes somos?</b></h4>
					<div class="row">
					
						<div class="col-12">
							<p class="mb-10">RadTree es un proyecto de la Universidad de Zaragoza cuya misión es intentar concienciar más al público sobre los peligros medioambientales y sus mitos relacionados, y como llegar a un mundo sostenible. Puedes participar respondiendo a preguntas o participando en los retos planteados.</p>
							<p class="mb-10">Respondiendo a las preguntas participas en una clasificación global de todos los usuarios.</p>
							<p>¿Te crees capaz de llegar a la cima?</p>
						</div><!-- col-sm-6 -->

					</div>
					<a class="dplay-block btn-brdr-primary mt-30 mb-md-50" href="20_quienesSomos.html"><b>Haz click aquí para saber más sobre nosotros</b></a>
				</div><!-- col-md-9 -->
				
				<div class="d-none d-md-block d-lg-none col-md-3"></div>
				<div class="col-md-12 col-lg-4">
						<div class="mb-md-0">
							<h4 class="p-title"><b>Newsletter</b></h4>
							<p class="mb-20">¡Suscríbete a nuestra newsletter para recibir notificaciones sobre actualizaciones, nuevas preguntas y mucho más!</p>
							<form class="nwsltr-primary-1">
								<input type="text" placeholder="Email"/>
								<button type="submit"><i class="ion-ios-paperplane"></i></button>
							</form>
						</div><!-- mtb-50 -->
					</div><!--  pl-20 -->
				</div><!-- col-md-3 -->
				
			</div><!-- row -->
		</div><!-- container -->
	</section>
	
	<%@ include file="WEB-INF/footer.jsp" %>
	
	<!-- SCIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>
	
</body>
</html>