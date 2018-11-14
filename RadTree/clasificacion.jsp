<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Clasificación</title>
	<meta name="description" content="Clasificación de usuarios por puntos obtenidos al responder preguntas">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">
	
	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">
	
	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
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
				<div class="col-sm-12">
					<div class="container">
						<div class="row">
							<div class="col-sm-12 col-md-4 mb-5">
								<button class="w-100 btn-fill-grey" href="#"><b>Semanal</b></button>
							</div>
							<div class="col-sm-12 col-md-4 mb-5">
								<button class="w-100 btn-fill-grey" href="#"><b>Mensual</b></button>
							</div>
							<div class="col-sm-12 col-md-4 mb-5">
								<button class="w-100 btn-fill-primary" href="#"><b>Todos los tiempos</b></button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="oflow-auto">
						<table class="w-100 clasificacion mt-30">
							<tr class="bg-primary">
								<th>Usuario</th>
								<th>Preguntas contestadas</th>
								<th>Preguntas acertadas</th>
								<th>Puntuación</th>
							</tr>
							<tr>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
							</tr>
							<tr>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
							</tr>
							<tr>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
							</tr>
							<tr>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
							</tr>
							<tr>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
								<td>Test</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

		</div><!-- container -->
	</section>
	
	<%@ include file="WEB-INF/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>