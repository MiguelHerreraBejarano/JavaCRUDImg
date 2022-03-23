<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="cabecera.jsp" %>
        <H1 style="clear:both"> &nbsp;</H1>
        <div class="content">
            <div class="card border-info">               
                    <h1>Agregar una venta</h1>                    
                <div>
                    <form:form commandName="venta" method="post">
                         <form:errors path="*"></form:errors><br><br>
                        <form:label path="Cod_venta" cssClass="input-group-addon">Codigo de la venta: </form:label>
                        <form:input path="Cod_venta" cssClass="form-control"></form:input>

                        <form:label path="id_cliente" cssClass="input-group-addon">Codigo del cliente</form:label>
                        <form:select path="id_cliente" cssClass="form-control">
                            <c:forEach var="dato" items="${idClientes}" >
                                <option value="${dato.id}">${dato.nombre}</OPTION>
                            </c:forEach>
                        </form:select>
                        <form:label path="fecha_venta" cssClass="input-group-addon">Fecha Venta: </form:label>
                        <form:input path="fecha_venta" cssClass="form-control"></form:input>
                        <form:label path="cod_producto" cssClass="input-group-addon">Codigo Producto: </form:label>
                        <form:select path="cod_producto" cssClass="form-control">
                            <c:forEach var="prod" items="${idProductos}" >
                                <option value="${prod.cod_producto}">${prod.nombre}</OPTION>
                            </c:forEach>                            
                        </form:select>
                        <form:button value="Enviar_datos" class="btn btn-success" style ="background-color: #339BFF ">
                            Enviar Datos
                        </form:button>
                            <input type="reset" value="Limpiar..." class="btn btn-success" style ="background-color: #339BFF "/>
                            <a href="formVenta.htm" class="btn btn-success" style ="background-color: #339BFF "> Regresar<a/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <div class="footer" >
        <p>Derechos Sena </p>
    </div>
    </body>
</html>
