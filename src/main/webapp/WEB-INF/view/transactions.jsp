<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Transaction Logs</title>
</head>

<body class="my-login-page">
<jsp:include page="templates/header.jsp"/>
<section class="h-100">
    <div class="container h-100">
        <div class="row justify-content-md-center">
            <div class="card">
                <div class="card card-body table-responsive">
                    <c:choose>
                        <c:when test="${audits.totalPages > 0}">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Actor</th>
                                    <th>Action</th>
                                    <th>Status</th>
                                    <th>Target</th>
                                    <th>Time</th>
                                    <th>Details</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="audit" items="${audits.content}">
                                    <tr>
                                        <td><label>${audit.actor}</label></td>
                                        <td><label>${audit.action}</label></td>
                                        <td><label>${audit.status}</label></td>
                                        <td><label>${audit.target}</label></td>
                                        <td><label>${audit.timestamp}</label></td>
                                        <td><label>${audit.info}</label></td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                        </c:when>
                        <c:otherwise>
                            <h5>No transactions Found...</h5>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
            <c:if test="${audits.totalPages > 0}">
                <nav aria-label="Page navigation example" style="margin:auto;">
                    <ul class="pagination">
                        <c:set var="prev" value="0"/>
                        <c:if test="${param.page > 0}">
                            <c:set var="prev" value="${param.page -1}"/>
                        </c:if>
                        <c:if test="${audits.totalPages > 0}">
                            <li class='page-item <c:if test="${empty param.page || param.page eq 0}">disabled</c:if>'>
                                <a class="page-link" href="/audit?page=${prev}&size=${maxTraySize}">Prev</a></li>
                        </c:if>
                        <c:forEach var="i" begin="0" end="${audits.totalPages -1}">
                            <li class='page-item <c:if test="${param.page eq i || (empty param.page && i eq 0)}">active</c:if>'>
                                <a class="page-link" href="/audit?page=${i}&size=${maxTraySize}">${i+1}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${audits.totalPages > 0}">
                            <li class='page-item <c:if test="${audits.totalPages <= (param.page + 1)}">disabled</c:if>'>
                                <a class="page-link" href="/audit?page=${param.page + 1}&size=${maxTraySize}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>

            <input type="hidden" name="currentPage" value="${currentPage}" id="currentPageNo">
        </div>
    </div>
</section>
<jsp:include page="templates/pageScript.jsp"/>
</body>
</html>