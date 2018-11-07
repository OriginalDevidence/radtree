<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	Comprobar que hay un usuario logueado y es administrador
	Cargar un contenido de la cola de validación
	Por parámetro se pasa el número, 1 siendo el más viejo y n siendo el más nuevo
--%>
<%
	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
	if (usuario == null || usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
		response.sendRedirect("errorInterno.html");
	} else {
		ContenidoDAO contenidoDAO = new ContenidoDAO();
		try {
			int numValidacion = contenidoDAO.getNumContenidosInColaValidacion();
			request.setAttribute("numInValidacion", numValidacion);
		} catch (ErrorInternoException e) {
            response.sendRedirect("errorInterno.html");
		}
	}
%>
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
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
</head>
<body>
	
	<%@ include file="WEB-INF/header.jsp" %>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.jsp"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="gestionContenido.jsp">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Cola de validación</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			
			<div class="row">
				<div class="col-12">
					<h3 class="mb-15"><b>Cola de validación</b></h3>
				</div>
				<div class="col-12 col-md-4">
					<ul class="mb-20">
						<li class="mr-5"><button class="btn-b-sm btn-fill-grey plr-10"><i class="ion-arrow-left-a"></i></button></li>
						<li class="mr-10">Elemento</li>
						<!-- Se puede emplear type="number" pero no permite usar size, como hay flechas a cada lado no importa -->
						<li class="mr-10"><input class="form-brdr-grey text-center" type="text" value="1" size=1></li>
						<li class="mr-5">de la cola de validación</li>
						<li><button class="btn-b-sm btn-fill-grey plr-10"><i class="ion-arrow-right-a"></i></button></li>
					</ul>
				</div>
				<div class="col-6 col-md-2">
					<button class="w-100 btn-b-sm btn-fill-primary plr-10"><i class="mr-5 ion-checkmark"></i>Aprobar</button>
				</div>
				<div class="col-6 col-md-2">	
					<button class="w-100 btn-b-sm btn-fill-primary plr-10"><i class="mr-5 ion-close"></i>Denegar</button>
				</div>
			</div>

			<div class="row mt-20">
				<div class="col-12">
					<h5 class="mr-15"><b>Autor:</b> *nombre*</h4>
					<h5 class="mr-15 mb-15"><b>Fecha:</b> *fecha*</h4>
					<h4 class="mr-15"><b>Noticia: *titulo*</b></h4>
					<p class="mt-10">*descripcion de la noticia*</p>
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