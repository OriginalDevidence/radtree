<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="sistinfo.capamodelo.resultados.ResultadoRegistro" %>
<%--
	Comprueba los errores que han podido ocurrir en el registro y les añade formato
--%>
<%
	Map<String, String> errores = (Map<String, String>)request.getAttribute("errores");
	if (errores != null) {
		String estiloCabecera = "<i class=\"ml-10 ion-close color-red\"></i><span class=\"pl-5 font-10 color-red\">";
		String estiloFinal = "</span>";
		// Añadir formato
		for (String k : errores.keySet()) {
			errores.replace(k, estiloCabecera + errores.get(k) + estiloFinal);
		}
		ResultadoRegistro resultadoRegistro = new ResultadoRegistro(
			errores.getOrDefault("alias", ""),
			(String)request.getParameter("alias"),
			errores.getOrDefault("nacimiento", ""),
			(String)request.getParameter("nacimiento"),
			errores.getOrDefault("nombre", ""),
			(String)request.getParameter("nombre"),
			errores.getOrDefault("apellidos", ""),
			(String)request.getParameter("apellidos"),
			errores.getOrDefault("email", ""),
			(String)request.getParameter("email"),
			errores.getOrDefault("clave", ""),
			(String)request.getParameter("clave"),
			errores.getOrDefault("reclave", ""),
			(String)request.getParameter("reclave")
		);
		request.setAttribute("resultadoRegistro", resultadoRegistro);
	}
%>
<%-- Bean que almacena el resultado del registro --%>
<jsp:useBean id="resultadoRegistro" class="sistinfo.capamodelo.resultados.ResultadoRegistro" scope="request"/>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Registro</title>
	<meta name="description" content="Registro de usuario">
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
			<a class="mt-10 color-ash" href="#">Registro</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			<div class="row">
			
				<div class="col-md-12 col-lg-8 pb-50">
					<h3 class="p-title mb-30"><b>Registro</b></h3>
					<form name="registro" action="RegistrarUsuario" method="post">

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

							<div class="col-sm-12 col-md-6">
								<label for="alias">Alias</label>
								<jsp:getProperty name="resultadoRegistro" property="errorAlias"/>
								<input class="mt-5" type="text" name="alias" placeholder="Alias"
									value="<jsp:getProperty name="resultadoRegistro" property="valorAlias"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="nacimiento">Fecha de nacimiento</label>
								<jsp:getProperty name="resultadoRegistro" property="errorNacimiento"
								/>
								<input class="mt-5" type="date" name="nacimiento"
									value="<jsp:getProperty name="resultadoRegistro" property="valorNacimiento"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="nombre">Nombre</label>
								<jsp:getProperty name="resultadoRegistro" property="errorNombre"/>
								<input class="mt-5" type="text" name="nombre" placeholder="Nombre"
									value="<jsp:getProperty name="resultadoRegistro" property="valorNombre"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="apellidos">Apellidos</label>
								<jsp:getProperty name="resultadoRegistro" property="errorApellidos"/>
								<input class="mt-5" type="text" name="apellidos" placeholder="Apellidos"
									value="<jsp:getProperty name="resultadoRegistro" property="valorApellidos"/>"/>
							</div>
							
							<div class="col-12">
								<label for="email">Email</label>
								<jsp:getProperty name="resultadoRegistro" property="errorEmail"/>
								<input class="mt-5" type="email" name="email" placeholder="Email"
									value="<jsp:getProperty name="resultadoRegistro" property="valorEmail"/>"/>
							</div>
							
							<div class="col-sm-12 col-md-6">
								<label for="clave">Contraseña</label>
								<jsp:getProperty name="resultadoRegistro" property="errorClave"/>
								<input class="mt-5" type="password" name="clave" placeholder="Contraseña"/>
							</div>
							
							<div class="col-sm-12 col-md-6">
								<label for="reclave">Repite la contraseña</label>
								<jsp:getProperty name="resultadoRegistro" property="errorReclave"/>
								<input class="mt-5" type="password" name="reclave" placeholder="Repite la contraseña"/>
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
						
						<div class="row mt-40">
							<div class="col-12">
								<button class="w-100 btn-fill-primary" type="submit"><b>Registrarse</b></button>
							</div>
						</div>
					
					</form>
				</div>

				<div class="col-md-6 col-lg-4">
					<h3 class="mb-30"><b>¿Ya tienes una cuenta?</b></h3>
					<a class="w-100 btn-fill-primary" href="inicioSesion.jsp"><b>Iniciar sesión</b></a>
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