<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Registro - RadTree</title>
	<meta name="description" content="Registro de usuario">
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
	
	<%@ include file="/jsp/include/header.jsp" %>
	
	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">Registro</a>
		</div><!-- container -->
	</section>
	
	<section>
		<div class="container">
			<div class="row">
			
				<div class="col-md-12 col-lg-8 pb-50">
					<h3 class="p-title mb-30"><b>Registro</b></h3>
					<form name="registro" action="${pageContext.request.contextPath}/registrar/registro" method="post">

						<div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">
							
							<c:if test="${not empty requestScope.erroresArriba}">
								<div class="col-10 offset-1 mb-30 alert alert-danger">
									<ul>
										<c:forEach items="${requestScope.erroresArriba}" var="error">
										    <li><i class="ion-close color-red pr-10 pb-5"></i>${error}</li>
										</c:forEach>
									</ul>
								</div>
							</c:if>

							<div class="col-sm-12 col-md-6">
								<label for="alias">Alias</label>
								<c:if test="${not empty requestScope.errores.get('alias')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('alias')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="text" name="alias" placeholder="Alias"
									value="<c:out value="${param.alias}"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="nacimiento">Fecha de nacimiento</label>
								<c:if test="${not empty requestScope.errores.get('nacimiento')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('nacimiento')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="date" name="nacimiento"
									value="<c:out value="${param.nacimiento}"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="nombre">Nombre</label>
								<c:if test="${not empty requestScope.errores.get('nombre')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('nombre')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="text" name="nombre" placeholder="Nombre"
									value="<c:out value="${param.nombre}"/>"/>
							</div>

							<div class="col-sm-12 col-md-6">
								<label for="apellidos">Apellidos</label>
								<c:if test="${not empty requestScope.errores.get('apellidos')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('apellidos')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="text" name="apellidos" placeholder="Apellidos"
									value="<c:out value="${param.apellidos}"/>"/>
							</div>
							
							<div class="col-12">
								<label for="email">Email</label>
								<c:if test="${not empty requestScope.errores.get('email')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('email')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="email" name="email" placeholder="Email"
									value="<c:out value="${param.email}"/>"/>
							</div>
							
							<div class="col-sm-12 col-md-6">
								<label for="clave">Contraseña</label>
								<c:if test="${not empty requestScope.errores.get('clave')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('clave')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="password" name="clave" placeholder="Contraseña"/>
							</div>
							
							<div class="col-sm-12 col-md-6">
								<label for="reclave">Repite la contraseña</label>
								<c:if test="${not empty requestScope.errores.get('reclave')}">
									<i class="ml-10 ion-close color-red"></i><span class="pl-5 font-10 color-red">
									<c:out value="${requestScope.errores.get('reclave')}"/>
									</span>
								</c:if>
								<input class="mt-5" type="password" name="reclave" placeholder="Repite la contraseña"/>
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
					<h3 class="mb-30">
						<b>¿Ya tienes una cuenta?</b>
					</h3>
					<a class="w-100 btn-fill-primary"
						href="${pageContext.request.contextPath}/iniciar-sesion"><b>
						Iniciar sesión
					</b></a>
				</div>

			</div>

		</div><!-- container -->
	</section>
	
	<%@ include file="/jsp/include/footer.jsp" %>
	
	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>
	
</body>
</html>