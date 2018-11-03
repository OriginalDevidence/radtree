<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="sistinfo.utils.CookieManager" %>
<%--
	Almacena datos de usuario (UsuarioVO) en la request para que luego pueda ser usada por la bean
	Orden de comprobaciones:
	- Si ya hay un UsuarioVO en la request, no hacer nada
	- Si no hay un UsuarioVO en la request, intentar cargar los datos del usuario con el alias incluido en los parametros
		- Si no lo encuentra, intentar cargar los datos del usuario de las cookies
--%>
<%! @SuppressWarnings("unchecked") %>
<%
	if (request.getAttribute("errores") instanceof HashMap) {
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
      <a class="mt-10" href="30_gestionContenido.html">Gestionar contenido<i class="mlr-10 ion-chevron-right"></i></a>
      <a class="mt-10 color-ash" href="">Crear reto</a>
    </div><!-- container -->
  </section>


  <section>
    <div class="container">

      <div class="row">
        <div class="col-md-12 col-lg-8">
          <h3 class="mb-30"><b>Crear reto</b></h3>
          <form name="crearReto" action="TODO.do" method="post">

            <div class="row form-block form-plr-15 form-h-45 form-mb-20 form-brdr-lite-white">

              <div class="col-sm-8">
                <label for="titulo">Título del reto</label>
								<c:out value="${requestScope.errores.get('titulo')}" escapeXml="false"/>
                <input type="text" name="titulo" placeholder="Título"
									value="<c:out value="${param.titulo}"/>"/>
              </div>

              <div class="col-sm-12">
                <label for="reto">Planteamiento del reto</label>
								<c:out value="${requestScope.errores.get('cuerpo')}" escapeXml="false"/>
                <textarea class="p-10" type="text" name="reto" rows=4
                  value="<c:out value="${param.titulo}"/>"></textarea>
              </div>

            </div>

            <div class="row">
              <div class="col-sm-12 mt-30">
                <button class="w-100 btn-fill-primary" type="submit"><b>Enviar reto</b></button>
              </div>
            </div>

          </form>
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
