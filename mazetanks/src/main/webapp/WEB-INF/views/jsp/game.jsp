<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
    <head>
        <title>Мини-игра battle city с помощью HTML5 | Демонстрация для сайта RUDEBOX</title>
        <spring:url value="resources/game" var="RESOURCES" />
        <link href="${RESOURCES}/css/main.css" rel="stylesheet" type="text/css" />
        <script src="${RESOURCES}/js/jquery-1.5.2.min.js"></script>
        <!--script src="${RESOURCES}/js/script.js"></script-->
        <script src="${RESOURCES}/js/websocket.js"></script>
    </head>
    <body>        
    <br/>
    <center>
    <span id="server-message" style="border: 2px solid blue; padding: 2px">???</span>
    </center>
    <br/>
        <div class="container">
            <div align=center><canvas id="scene" width="800" height="600"></canvas>
        </div>
<!--

	<script>
        (function(){
            var sound = new Audio("${RESOURCES}/loop.mp3");
            sound.volume = 0.9;
            sound.addEventListener('ended', function() { // loop sound
                
                
            }, false);
            sound.play();
        })();
        </script>
-->
    </body>
</html>
