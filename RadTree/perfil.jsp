<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="sistinfo.capadatos.dao.UsuarioDAO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%--
	Almacena datos de usuario (UsuarioVO) en la request para que luego pueda ser usada por la bean
	Orden de comprobaciones:
	- Si ya hay un UsuarioVO en la request (id="usuario"), no hacer nada
	- Si no hay un UsuarioVO en la request, intentar cargar los datos del usuario con id idUsuario (atributo de request)
		- Si no lo encuentra, intentar cargar los datos del usuario almacenado en la sesion
--%>
<%
	if (request.getAttribute("usuario") == null) {
		// Encontrar un ID de usuario para mostrar
		Long idUsuario = (Long)request.getAttribute("idUsuario");
		if (idUsuario == null || idUsuario == 0L) {
			idUsuario = (Long)session.getAttribute("idUsuario");
			if (idUsuario == null || idUsuario == 0L) {
				// No sabemos qué usuario mostrar
	            response.sendRedirect("70_errorInterno.html");
			}
		}
		// Cargar el usuario con ese ID en la request
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			UsuarioVO usuario = usuarioDAO.getUsuarioById(idUsuario);
			if (usuario == null) {
	            response.sendRedirect("70_errorInterno.html");
			} else {
				request.setAttribute("usuario", usuario);
			}
		} catch (ErrorInternoException e) {
            response.sendRedirect("70_errorInterno.html");
		}
	}
%>
<jsp:useBean id="usuario" class="sistinfo.capadatos.vo.UsuarioVO" scope="request"/>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><jsp:getProperty name="usuario" property="alias"/> - RadTree</title>
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
			<!-- TODO: enviar al usuario a este mismo perfil -->
			<a class="mt-10 color-ash" href="perfil.jsp">Perfil de <jsp:getProperty name="usuario" property="alias"/></a>
		</div><!-- container -->
	</section>
	
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h3 class="p-title mb-30"><b>Perfil de <jsp:getProperty name="usuario" property="alias"/></b></h3>
					
					<!-- Medallas de perfil (administrador, creador de contenido, etc) -->
					<!-- TODO arreglar -->
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5"></ul>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-settings"></i>Administrador</li>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-paintbrush"></i>Creador de contenido</li>

					<div class="row mt-30">
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Alias</h4>
							<p><jsp:getProperty name="usuario" property="alias"/></p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Fecha de nacimiento</h4>
							<p><jsp:getProperty name="usuario" property="fechaNacimiento"/></p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Nombre</h4>
							<p><jsp:getProperty name="usuario" property="nombre"/></p>
						</div>
						
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Apellidos</h4>
							<p><jsp:getProperty name="usuario" property="apellidos"/></p>
						</div>
							
						<div class="col-12 mb-20">
							<h4 class="mb-5">Email</h4>
							<p><jsp:getProperty name="usuario" property="email"/></p>
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

	<jsp:include page="WEB-INF/footer.jsp"/>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>