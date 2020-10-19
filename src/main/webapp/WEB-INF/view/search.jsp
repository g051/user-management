<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search Page</title>
</head>
<body class="my-login-page">
<jsp:include page="templates/header.jsp"/>
<section>
    <div class="container">
        <div class="row justify-content-md-center">
            <form action="/searchSubmit" method="POST">
                <h4 class="text-center">Advanced Search Page</h4>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <tr>
                            <th>Search Keyword</th>
                            <th>Select Search By</th>
                            <th>Action</th>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" class="form-control" name="searchKeyword"
                                       placeholder="type keyword here...">
                            </td>
                            <td>
                                <select id="criteriaId" name="criteria" class="form-control">
                                    <option value="userName">Username</option>
                                    <option value="firstName">First Name</option>
                                    <option value="lastName">Last Name</option>
                                    <option value="email">Email</option>
                                </select>
                            </td>
                            <td>
                                <input class="btn btn-outline-primary my-2 my-sm-0" name="searchButton"
                                       value="Search Now!"
                                       type="submit">
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</section>
<c:if test="${not empty result}">
    <section class="">
        <div class="container">
            <div class="row justify-content-md-center">
                <div class="card">
                    <div class="card-header">
                        <h4 class="float-left">Matched Users</h4>
                    </div>
                    <div class="card card-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>User Id</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Active?</th>
                                <th>Email</th>
                                <th colspan="3">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${result}">
                                <tr>
                                    <td><label>${user.getId()}</label></td>
                                    <td><label>${user.getUserName()}</label></td>
                                    <td><label>${user.getRoleName()}</label></td>
                                    <td><label>${user.isActive()}</label></td>
                                    <td>
                                        <label id="email_${user.getId()}">${user.getEmail()}</label>
                                        <input required type="text" class="form-control" style="display:none;"
                                               name="email" value="${user.getEmail()}" id="text_email_${user.getId()}">
                                    </td>
                                    <td>
                                        <a href="/update" id="update_${user.getId()}" class="updateData"
                                           onclick="event.preventDefault();"><i class="fa fa-edit"></i></a>
                                        <a href="/save" id="save_${user.getId()}" class="saveData"
                                           onclick="event.preventDefault();saveData(${user.getId()});"
                                           style="display: none;"><i class="fa fa-save"></i></a>
                                    </td>
                                    <td><a href="/resetPwd/${user.getId()}" class="resetData">
                                        <i class="fa fa-key"></i>
                                    </a></td>
                                    <td><a href="/delete/${user.getId()}" class="deleteData">
                                        <i class="fa fa-trash"></i>
                                    </a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</c:if>
<jsp:include page="templates/pageScript.jsp"/>
</body>
</html>