<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sistinfo.utils.CookieManager" %>
<%--
	Comprueba los errores que han podido ocurrir en el inicio de sesion y les añade formato
--%>
<%
	String errorMessage = (String)request.getAttribute("error");
	if (errorMessage != null) {
		// Dar formato al error
		String estiloCabecera = "<p class=\"font-10 color-red lh-30 mb-20 ml-15\"><i class=\"ion-close\"></i><span class=\"pl-5\">";
		String estiloFinal = "</span></p>";
		request.setAttribute("error", estiloCabecera + errorMessage + estiloFinal);
	} else {
		String alias = CookieManager.getAliasFromCookies(request);
		String claveHash = CookieManager.getClaveHashFromCookies(request);
		if (alias != null && claveHash != null) { // ya se ha comprobado que no son vacias en CookieManager
			request.setAttribute("usoCookies", true);
			request.setAttribute("loginCookie", alias);
			request.setAttribute("claveHashCookie", claveHash);
			RequestDispatcher dispatcher = request.getRequestDispatcher("IniciarSesion");
			dispatcher.forward(request, response);
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Iniciar sesión</title>
	<meta name="description" content="Inicio de sesión para usuarios">
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
			<a class="mt-10 color-ash" href="#">Iniciar sesión</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			<div class="row">
			
				<div class="col-md-12 col-lg-8">
					<h3 class="p-title mb-30"><b>Iniciar sesión</b></h3>
					<form name="inicioSesion" action="IniciarSesion" method="post">

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">
						
							<c:out value="${requestScope.error}" escapeXml="false"/>

							<div class="col-12">
								<label for="identificador">Email o alias</label>
								<input type="text" name="identificador" placeholder="Email o alias"
									value="<c:out value="${param.identificador}"/>"/>
							</div>
							
							<div class="col-12">
								<label for="clave">Contraseña</label>
								<input type="password" name="clave" placeholder="Contraseña"/>
							</div>

						</div>
						
						<div class="row mt-20">
							<div class="col-12">
								<button class="w-100 btn-fill-primary" type="submit"><b>Iniciar sesión</b></button>
							</div>
						</div>
					
					</form>

					<a class="mt-30 color-primary link-brdr-btm-primary" href="04_olvidoClave.html"><b>¿Olvidaste tu contraseña?</b></a>
					
				</div>

				<div class="col-md-12 col-lg-4">
					<h3 class="mb-30"><b>¿No tienes una cuenta?</b></h3>
					<a class="w-100 btn-fill-primary" href="02_registro.html"><b>Regístrate</b></a>
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