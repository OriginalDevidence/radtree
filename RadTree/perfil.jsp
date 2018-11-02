<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sistinfo.capadatos.dao.UsuarioDAO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.utils.CookieManager" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%--
	Almacena datos de usuario (UsuarioVO) en la request para que luego pueda ser usada por la bean
	Orden de comprobaciones:
	- Si ya hay un UsuarioVO en la request, no hacer nada
	- Si no hay un UsuarioVO en la request, intentar cargar los datos del usuario con el alias incluido en los parametros
		- Si no lo encuentra, intentar cargar los datos del usuario de las cookies
--%>
<%
	if (request.getAttribute("usuario") == null) {
		// Encontrar un ID de usuario para mostrar
		String alias = (String)request.getParameter("alias");
		if (alias == null || alias.trim().isEmpty()) {
			alias = CookieManager.getAliasFromCookies(request);
			if (alias == null) { // CookieManager ya comprueba que es vacio
				// No sabemos qué usuario mostrar
	            response.sendRedirect("errorInterno.html");
			} else {
				// Mostrar el usuario alias
				RequestDispatcher dispatcher = request.getRequestDispatcher("perfil.jsp?alias=" + alias);
				response.sendRedirect("perfil.jsp?alias=" + alias);
				dispatcher.include(request, response);
			}
		} else {// Cargar el usuario con ese alias
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			try {
				UsuarioVO usuario = usuarioDAO.getUsuarioByAlias(alias);
				if (usuario == null) {
		            response.sendRedirect("errorInterno.html");
				} else {
					request.setAttribute("usuario", usuario);
				}
			} catch (ErrorInternoException e) {
	            response.sendRedirect("errorInterno.html");
			}
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.usuario.alias}"/> - RadTree</title>
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

	<%@ include file="WEB-INF/header.jsp" %>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.html"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Perfil de <c:out value="${requestScope.usuario.alias}"/></a>
		</div><!-- container -->
	</section>
	
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h3 class="p-title mb-30"><b>Perfil de <c:out value="${requestScope.usuario.alias}"/></b></h3>
					
					<!-- Medallas de perfil (administrador, creador de contenido, etc) -->
					<!-- TODO arreglar -->
					<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5"></ul>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-settings"></i>Administrador</li>
						<li class="bg-primary ptb-5 plr-10"><i class="mr-5 ion-paintbrush"></i>Creador de contenido</li>

					<div class="row mt-30">
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Alias</h4>
							<p><c:out value="${requestScope.usuario.alias}"/></p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Fecha de nacimiento</h4>
							<p><c:out value="${requestScope.usuario.fechaNacimiento}"/></p>
						</div>

						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Nombre</h4>
							<p><c:out value="${requestScope.usuario.nombre}"/></p>
						</div>
						
						<div class="col-12 col-sm-6 mb-20">
							<h4 class="mb-5">Apellidos</h4>
							<p><c:out value="${requestScope.usuario.apellidos}"/></p>
						</div>
							
						<div class="col-12 mb-20">
							<h4 class="mb-5">Email</h4>
							<p><c:out value="${requestScope.usuario.email}"/></p>
						</div>
						
						<!-- TODO Funcionalidad -->
						<div class="col-sm-12 mtb-20">
							<a class="color-primary link-brdr-btm-primary" href="CerrarSesion.do"><b>Cerrar sesión</b></a>
						</div>
						
						<div class="col-sm-12 mb-20">
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

	<%@ include file="WEB-INF/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>