<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
    <head>
        <title>Мини-игра battle city с помощью HTML5 | Демонстрация для сайта RUDEBOX</title>
        <spring:url value="resources/game" var="RESOURCES" />
        <link href="${RESOURCES}/main.css" rel="stylesheet" type="text/css" />
        <script src="${RESOURCES}/js/jquery-1.5.2.min.js"></script>
        <script src="${RESOURCES}/js/script.js"></script>
    </head>
    <body>
        
    <br><br><br><br><br><br><br><br><br>
        <div class="container">
            <div align=center><canvas id="scene" width="800" height="600"></canvas>
        </div>
		<script>
        (function(){
            var sound = new Audio("${RESOURCES}/loop.mp3");
            sound.volume = 0.9;
            sound.addEventListener('ended', function() { // loop sound
                
                
            }, false);
            sound.play();
        })();
        </script>
    </body>
</html>
