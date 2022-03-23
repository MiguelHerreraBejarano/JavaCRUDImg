<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="cabecera.jsp" %>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.23/js/dataTables.bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        <link href="https://cdn.datatables.net/1.10.23/css/dataTables.bootstrap.min.css" />

        
        
            <div class="card border-info">          
                <br>
                <br>
                    <a href="formRegistrarVentas.htm" class="btn btn-success">Registrar venta</a>
                <br>
                <br>
                <br>
                </div>
                <div class="card-body ">
                    <table id="listadoTabla" class="table table-bordered table-striped table-hover">
                        <thead>
                            <th>venta</th>
                             <th>cliente</th>
                            
                            <th>producto</th>
                            <th>fecha venta</th>
                            
                        </thead>
                        <tbody>
                            <c:forEach items="${venta}" var="datos">
                            <tr>
                                <td><c:out value="${datos.Cod_venta}"></c:out></td>
                                <td><c:out value="${datos.id_cliente }"></c:out></td>
                                
                                <td><c:out value="${datos.cod_producto}"></c:out></td>
                                <td><c:out value="${datos.fecha_venta}"></c:out></td>
                                
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
