<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Quienes somos - RadTree</title>
	<meta name="description" content="Descripción">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
	<link rel="icon" type="image/jpg" href="images/RadTree_Logo_x32.jpg" />
</head>
<body>

    <%@ include file="include/header.jsp" %>

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="${pageContext.request.contextPath}"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">¿Quienes somos?</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b>¿Quienes somos?</b></h3>
                    <p class="mb-20 text-justify">
                        El departamento de Ingeniería Química y Tecnologías del Medio Ambiente
                        de la Universidad de Zaragoza está realizando un proyecto cuyo objetivo
                        es informar a la población sobre los peligros medioambientales y sus mitos
                        relacionados, y cómo llegar a un mundo sostenible. Además busca recopilar
                        información acerca de la concienciación que tienen las personas sobre la
                        importancia de este tema. Estos objetivos se intentan llevar a cabo mediante
                        tres formas diferentes: noticias, preguntas y retos. 
                    </p>
					<h3 class="mb-30"><b>¿Qué puedo hacer para ayudar?</b></h3>
                    <p class="mb-20 text-justify">
                        El contenido de esta web es creado por los alumnos de la Universidad de Zaragoza.
                        Si estás interesado en subir cualquier contenido, puedes registrarte con tu e-mail
                        de la Universidad de Zaragoza, y al validar tu correo obtendrás permisos para subir
                        tu contenido.
                    </p>
                    <p class="mb-20 text-justify">
                        Si quieres colaborar pero no dispones de un correo universitario, puedes ponerte
                        en contacto con los administradores para obtener permisos de subir contenido.
                    </p>
                </div>
            </div> 
		</div><!-- container -->
	</section>

	<%@ include file="include/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
