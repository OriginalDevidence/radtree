<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<header>
	<div class="bg-191">
		<div class="container">	
			<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
			
				<ul class="float-left list-a-plr-10 list-a-plr-sm-5 list-a-ptb-10 list-a-ptb-sm-5">
					<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
					<li><a href="#"><i class="ion-social-twitter"></i></a></li>
					<li><a href="#"><i class="ion-social-instagram"></i></a></li>
				</ul>

				<%-- El usuario NO ha iniciado sesion --%>
				<c:if test="${empty sessionScope.usuario}">
					<ul
						class="float-right list-a-plr-10 list-a-plr-sm-5 mtb-5 mtb-sm-0">
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5"
							href="${pageContext.request.contextPath}/registrar">Regístrate</a></li>
						<li><span class="plr-10 plr-sm-5">o</span></li>
						<li><a class="btn-fill-primary btn-b-sm plr-20 plr-sm-5"
							href="${pageContext.request.contextPath}/iniciar-sesion">Inicia
								sesión</a></li>
					</ul>
				</c:if>
				<%-- El usuario SI ha iniciado sesion --%>
				<c:if test="${not empty sessionScope.usuario}">
					<ul
						class="float-right list-a-plr-10 list-a-plr-sm-5 ptb-5 mtb-5 mtb-sm-0">
						<li><a href="${pageContext.request.contextPath}/perfil">
							Bienvenido,
							<c:out value="${sessionScope.usuario.alias}" />
							<i class="pl-10 ion-arrow-down-b"></i>
						</a></li>
					</ul>
				</c:if>

			</div><!-- top-menu -->
		</div><!-- container -->
	</div><!-- bg-191 -->
	
	<div class="container">
		<a class="logo" href="."><img src="${pageContext.request.contextPath}/images/logo-black.png" alt="Logo"></a>
		
		<a class="right-area src-btn" href="#" >
			<i class="active src-icn ion-search"></i>
			<i class="close-icn ion-close"></i>
		</a>
		<div class="src-form">
			<form>
				<input type="text" placeholder="Search here">
				<button type="submit"><i class="ion-search"></i></button>
			</form>
		</div><!-- src-form -->
		
		<a class="menu-nav-icon" data-menu="#main-menu" href="#"><i class="ion-navicon"></i></a>
		
		<ul class="main-menu" id="main-menu">
			<li class="drop-down"><a href="#">CARTELES<i class="ion-arrow-down-b"></i></a>
				<ul class="drop-down-menu drop-down-inner">
					<li><a href="${pageContext.request.contextPath}/noticias">NOTICIAS</a></li>
					<li><a href="${pageContext.request.contextPath}/preguntas">PREGUNTAS</a></li>
					<li><a href="${pageContext.request.contextPath}/retos">RETOS</a></li>
				</ul>
			</li>
			<li><a href="${pageContext.request.contextPath}/clasificacion">CLASIFICACIÓN</a></li>
			<li><a href="${pageContext.request.contextPath}/quienes-somos">QUIENES SOMOS</a></li>
			<c:if test="${not empty sessionScope.usuario and sessionScope.usuario.tipoUsuario != 'PARTICIPANTE'}">
				<li><a class="btn-b-md btn-fill-primary lh-30" href="${pageContext.request.contextPath}/gestion-contenido">GESTIONAR CONTENIDO</a></li>
			</c:if>
		</ul>
		<div class="clearfix"></div>
	</div><!-- container -->
</header>