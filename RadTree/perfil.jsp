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
	
	<header>
		<div class="bg-191">
			<div class="container">	
				<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
				
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5">
						<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
						<li><a href="#"><i class="ion-social-twitter"></i></a></li>
						<li><a href="#"><i class="ion-social-instagram"></i></a></li>
					</ul>

					<ul class="float-right list-a-plr-10 list-a-plr-sm-5 mtb-5 mtb-sm-0">
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5" href="02_registro.html">Regístrate</a></li>
						<li><span class="plr-10 plr-sm-5">o</i></li>
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5" href="03_inicioSesion.html">Inicia sesión</a></li>
					</ul>
					<!-- TODO: Para cuando este logueado, añadir menu que lleve al perfil o logout
					<ul class="float-right list-a-plr-10 list-a-plr-sm-5 ptb-5 mtb-5 mtb-sm-0">
						<li>
							<a href="#">Bienvenido, Diego<i class="pl-10 ion-arrow-down-b"></i></a>
						</li>
					</ul>-->
					
				</div><!-- top-menu -->
			</div><!-- container -->
		</div><!-- bg-191 -->
		
		<div class="container">
			<a class="logo" href="index.html"><img src="images/logo-black.png" alt="Logo"></a>
			
			<a class="right-area src-btn" href="#" >
				<i class="active src-icn ion-search"></i>
				<i class="close-icn ion-close"></i>
			</a>
			<div class="src-form">
				<form>
					<input type="text" placeholder="Search here">
					<button type="submit"><i class="ion-search"></i></a></button>
				</form>
			</div><!-- src-form -->
			
			<a class="menu-nav-icon" data-menu="#main-menu" href="#"><i class="ion-navicon"></i></a>
			
			<ul class="main-menu" id="main-menu">
				<li class="drop-down"><a href="">CARTELES<i class="ion-arrow-down-b"></i></a>
					<ul class="drop-down-menu drop-down-inner">
						<li><a href="50_listaDeNoticias.html">NOTICIAS</a></li>
						<li><a href="52_listaDePreguntas.html">PREGUNTAS</a></li>
						<li><a href="54_listaDeRetos.html">RETOS</a></li>
					</ul>
				</li>
				<li><a href="60_clasificacion.html">CLASIFICACION</a></li>
				<li><a href="20_quienesSomos.html">QUIENES SOMOS</a></li>
				<li><a class="btn-b-md btn-fill-primary lh-30" href="30_gestionContenido.html">GESTIONAR CONTENIDO</a></li>
			</ul>
			<div class="clearfix"></div>
		</div><!-- container -->
	</header>
	
	
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
					<h3 class="p-title mb-30"><b>Perfil de *nombre*</b></h3>
					
					<!-- Medallas de perfil (administrador, creador de contenido, etc) -->
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5"></ul>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-settings"></i>Administrador</li>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-paintbrush"></i>Creador de contenido</li>
					</ul>

					<div class="row mt-30">
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Alias</h4>
							<p>JuanEcologico27</p>
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
				</div>
			
			</div>

		</div><!-- container -->
	</section>
	
	
	<footer class="bg-191 color-ccc">
		<div class="container">
			<div class="pt-50 pb-20 pos-relative">
				<div class="abs-tblr pt-50 z--1 text-center">
					<div class="h-80 pos-relative"><img class="opacty-1 h-100 w-auto" src="images/map.png" alt=""></div>
				</div>
				<div class="row">
				
					<div class="col-sm-4">
						<div class="mb-30">
							<a href="index.html"><img src="images/logo-white.png"></a>
							<p class="mt-20 color-ccc">
								RadTree es un proyecto de la Universidad de Zaragoza que intenta concienciar a la población sobre los peligros medioambientales.
							</p>
							<p class="mb-20"><a class="mt-10 link-brdr-btm-primary color-primary" href="20_quienesSomos.html">¿Quieres saber más?</a></p>
							<p class="color-ash">
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="ion-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							</p>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
					<div class="col-sm-4">
						<div class="mb-30">
							<h5 class="color-primary mb-20"><b>ÚLTIMAS NOTICIAS</b></h5>
							<div class="mb-20">
								<a class="color-white" href="51_noticia.html"><b>Secondary forests have short lifespans</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
							<div class="brdr-ash-1 opacty-2 mr-30"></div>
							<div class="mt-20">
								<a class="color-white" href="51_noticia.html"><b>Feeding 10 billion people by 2050 within planetary limits may be achievable</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
					<div class="col-sm-4">
						<div class="mb-30">
							<h5 class="color-primary mb-20"><b>NOTICIAS MÁS POPULARES</b></h5>
							<div class="mb-20">
								<a class="color-white" href="51_noticia.html"><b>Feeding 10 billion people by 2050 within planetary limits may be achievable</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
							<div class="brdr-ash-1 opacty-2 mr-30"></div>
							<div class="mt-20">
								<a class="color-white" href="51_noticia.html"><b>Secondary forests have short lifespans</b></a>
								<h6 class="mt-10">Jan 25, 2018</h6>
							</div>
						</div><!-- mb-30 -->
					</div><!-- col-md-4 -->
					
				</div><!-- row -->
			</div><!-- ptb-50 -->
			
			<div class="brdr-ash-1 opacty-2"></div>
			
			<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
			
				<ul class="float-right float-sm-none list-a-plr-10 list-a-plr-sm-5 list-a-ptb-15 list-a-ptb-sm-5">
					<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
					<li><a href="#"><i class="ion-social-twitter"></i></a></li>
					<li><a href="#"><i class="ion-social-instagram"></i></a></li>
				</ul>
				
			</div><!-- oflow-hidden -->
		</div><!-- container -->
	</footer>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>