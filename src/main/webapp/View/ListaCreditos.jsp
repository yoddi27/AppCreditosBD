    <%-- 
        Document   : ListaCreditosjsp
        Created on : 23/05/2018, 07:58:04 PM
        Author     : yypv2
    --%>

    <%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!doctype html>
    <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">
            <link rel="icon" href="../../../../favicon.ico">

            <title>Menu</title>

            <!-- Bootstrap core CSS -->
            <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet">

            <!-- Custom styles for this template -->
            <link href="${pageContext.servletContext.contextPath}/css/jumbotron.css" rel="stylesheet">
        </head>

        <body>

            <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <a class="navbar-brand" href="#">CRÉDITOS</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.servletContext.contextPath}/View/Creditos.jsp">Créditos<span class="sr-only">(current)</span></a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main role="main">

                <!-- Main jumbotron for a primary marketing message or call to action -->
                <div class="jumbotron">
                    <div class="container">
                        <form action="${pageContext.servletContext.contextPath}/GuardarDatosServlet" method="post">
                            <table class="table">
                                Lista de créditos
                                <thead>
                                    <tr>
                                        <th scope="col">Número de Crédito</th>
                                        <th scope="col">Número de Documento</th>
                                        <th scope="col">Nombres</th>
                                        <th scope="col">Apellidos</th>
                                        <th scope="col">Monto a prestar</th>                                
                                        <th scope="col">Tipo Trabajador</th>
                                        <th scope="col">Tipo Crédito</th>
                                        <th scope="col">Trabaja en la Empresa</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="credito" items="${sessionScope.LISTADO}">
                                        <tr>
                                            <td><c:out value="${credito.getNumcredito()}"/></td>
                                            <td><c:out value="${credito.getNumdocumento()}"/></td>
                                            <td><c:out value="${credito.getNombres()}"/></td>
                                            <td><c:out value="${credito.getApellidos()}"/></td>
                                            <td><c:out value="${credito.getMontoprestar()}"/></td>
                                            <td><c:out value="${credito.getDescripcionTipoTrabajador()}"/></td>
                                            
                                            <td><c:out value="${credito.getDescripcionTipoCredito()}"/></td>
                                           
                                            <c:if test="${credito.getTrabajaempresa()==1}">
                                                <td><c:out value="SI"/></td>
                                            </c:if> 
                                            <c:if test="${credito.getTrabajaempresa()==2}">
                                                <td><c:out value="NO"/></td>
                                            </c:if> 
                                        </tr>
                                    </c:forEach>                            
                                </tbody>
                            </table>
                            <div class="form-row">    
                                <div class="col" style="text-align: center;">
                                    <button name="accion" value="creditosMasUsados" type="submit" class="btn btn-primary">Créditos más usados</button>
                                    <button name="accion" value="AcomuladoDePrestamos" type="submit" class="btn btn-primary">Acumulado de Prestamos</button>
                                    <button name="accion" value="quienesPrestan" type="submit" class="btn btn-primary">Quienes Prestan más</button>
                                    <button name="accion" value="listar" type="submit" class="btn btn-primary">Listar</button>
                                </div> 
                            </div>
                            <table class="table">
                                
                                <thead>
                                    <tr>
                                        <th scope="col" ><c:out value="${sessionScope.TIPOINFORME}"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="resultado" items="${sessionScope.INFORME}">
                                        <tr>
                                            <td><c:out value="${resultado}"/></td>                                            
                                        </tr>
                                    </c:forEach>                            
                                </tbody>
                            </table>
                        </form>
                    </div>
                    <div class="container">

                        <!-- Example row of columns -->
                    </div> <!-- /container -->
                </div>
            </main>

            <!-- Bootstrap core JavaScript
            ================================================== -->
            <!-- Placed at the end of the document so the pages load faster -->
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        </body>
    </html>

