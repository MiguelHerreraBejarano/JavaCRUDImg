<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="cabecera.jsp" %>
        <h1 style="clear:both">Clients Form</h1>
        <div class="content">
            <form:form cssClass="navbar-form " method="post" commandName="cliente" >
                <div>
                     Administrar Clientes
                </div>
                <br>
                <form:button name="Enviar" class="btn btn-success btn-lg" style ="background-color: yellow ">Consultar</form:button>
                <a class="btn btn-success btn-lg" href="index.htm" role="button" style ="background-color: purple ">Regresar...</a>

             </form:form>
            </div>
    </body>
</html>
