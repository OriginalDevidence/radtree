<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- TODO: Cambiar *usuario* por el nombre de usuario -->
	<title>Perfil de *usuario*</title>
	<meta name="description" content="Página de perfil de usuario">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">
	
	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">
	
	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
	
</head>
<body>

	<jsp:include page="WEB-INF/header.jsp"/>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.html"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="">Perfil de *nombre*</a>
		</div><!-- container -->
	</section>
	
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<jsp:useBean id="usuario" class="sistinfo.capadatos.vo.UsuarioVO">
					<h3 class="p-title mb-30"><b>Perfil de *nombre*</b></h3>
					
					<!-- Medallas de perfil (administrador, creador de contenido, etc) -->
					<!-- TODO arreglar -->
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5"></ul>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-settings"></i>Administrador</li>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-paintbrush"></i>Creador de contenido</li>

					<div class="row mt-30">
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Alias</h4>
							<p>
								<jsp:getProperty name="usuario" property="alias"/>
								<jsp:getProperty name="usuario" property="puntuacion"/>
							</p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Fecha de nacimiento</h4>
							<p>04/03/1981</p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Nombre</h4>
							<p>Juan</p>
						</div>
						
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Apellidos</h4>
							<p>Martinez Perez</p>
						</div>
							
						<div class="col-12 mb-20">
							<h4 class="mb-5">Email</h4>
							<p>juanmartinezperez@gmail.com</p>
						</div>
						
						<div class="col-sm-12 mtb-20">
							<a class="color-primary link-brdr-btm-primary" href="11_editarPerfil.html"><b>Editar datos del perfil</b></a>
						</div>
						
						<div class="col-sm-12 mb-20">
							<a class="color-primary link-brdr-btm-primary" href="05_cambiarClave.html"><b>Cambiar contraseña</b></a>
						</div>
					</div>
					</jsp:useBean>
				</div>
			
			</div>

		</div><!-- container -->
	</section>

	<jsp:include page="WEB-INF/footer.jsp"/>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>