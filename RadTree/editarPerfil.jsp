<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%-- 
	Comprobar que hay un usuario con la sesión iniciada y dar formato a los errores de editar perfil
	(SuppressWarnings para evitar el warning de type cast de "errores", aunque esta bien hecho))
--%>
<%! @SuppressWarnings("unchecked") %>
<%
	UsuarioVO usuario = (UsuarioVO)request.getSession().getAttribute("usuario");
	if (usuario == null) {
		response.sendRedirect("inicioSesion.jsp");
	} else if (request.getAttribute("errores") instanceof HashMap) {
		// Hay errores en un registro previo, darles formato
		Map<String, String> errores = (HashMap<String, String>)request.getAttribute("errores");
		String estiloCabecera = "<i class=\"ml-10 ion-close color-red\"></i><span class=\"pl-5 font-10 color-red\">";
		String estiloFinal = "</span>";
		// Añadir formato
		for (String k : errores.keySet()) {
			errores.replace(k, estiloCabecera + errores.get(k) + estiloFinal);
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${sessionScope.usuario.alias}"/> - Editar perfil</title>
	<meta name="description" content="Formulario para editar los datos del perfil">
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
			<a class="mt-10" href="perfil.jsp?alias=<c:out value="${sessionScope.usuario.alias}"/>">Perfil de <c:out value="${sessionScope.usuario.alias}"/><i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Editar perfil</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h3 class="p-title mb-30"><b>Editar perfil</b></h3>

					<form name="editarUsuario" action="EditarUsuario.do" method="post">

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-12 col-md-6">
								<label for="alias">Alias</label>
								<c:out value="${requestScope.errores.get('alias')}" escapeXml="false"/>
								<input type="text" name="alias" placeholder="Alias" value="<c:out value="${sessionScope.usuario.alias}"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="nacimiento">Fecha de nacimiento</label>
								<c:out value="${requestScope.errores.get('nacimiento')}" escapeXml="false"/>
								<input type="date" name="nacimiento" value="<c:out value="${sessionScope.usuario.fechaNacimiento}"/>"/>
							</div>

							<div class="col-12 col-sm-6">
								<label for="nombre">Nombre</label>
								<c:out value="${requestScope.errores.get('nombre')}" escapeXml="false"/>
								<input type="text" name="nombre" placeholder="Nombre" value="<c:out value="${sessionScope.usuario.nombre}"/>"/>
							</div>

							<div class="col-12 col-sm-6">
								<label for="apellidos">Apellidos</label>
								<c:out value="${requestScope.errores.get('apellidos')}" escapeXml="false"/>
								<input type="text" name="apellidos" placeholder="Apellidos" value="<c:out value="${sessionScope.usuario.apellidos}"/>"/>
							</div>
							
							<div class="col-12">
								<label for="email">Email</label>
								<c:out value="${requestScope.errores.get('email')}" escapeXml="false"/>
								<input type="email" name="email" placeholder="Email" value="<c:out value="${sessionScope.usuario.email}"/>"/>
							</div>

						</div>

						<div class="row">
							<div class="col-12">
								<label for="imagen">Foto de perfil</label>
							</div>
							<div class="col-12">
								<input class="m-5" type="file" name="imagen"/>
							</div>
						</div>
						
						<div class="row mb-50">
							<div class="col-sm-12 mt-20">
								<button class="w-100 btn-fill-primary" type="submit"><b>Modificar datos</b></button>
							</div>
						</div>
					
					</form>

				</div>

				<div class="col-md-12 col-lg-4">
					<h3 class="p-title mb-30"><b>Eliminar perfil</b></h3>
					<p>Ten cuidado, una vez eliminado no se puede volver atrás.</p>
					<button class="w-100 dplay-block btn-brdr-red mt-30 mb-md-50"><b>Eliminar perfil</b></button>
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