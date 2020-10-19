<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Users</title>
</head>

<body class="my-login-page">
<jsp:include page="templates/userHeader.jsp"/>
<section class="">
    <div class="container ">
        <div class="row justify-content-md-center">
            <div class="card">
                <div class="card card-body table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>User Id</th>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Active?</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><label>${currentUser.id}</label></td>
                            <td><label>${currentUser.userName}</label></td>
                            <td><label>${currentUser.roleName}</label></td>
                            <td><label>${currentUser.active}</label></td>
                            <td>
                                <label id="email_${currentUser.id}">${currentUser.email}</label>
                                <input required type="text" class="form-control" style="display:none;"
                                       name="email" value="${currentUser.email}" id="text_email_${currentUser.id}">
                            </td>
                            <td>
                                <a href="/update" id="update_${currentUser.id}" class="updateData"
                                   onclick="event.preventDefault();"><i class="fa fa-edit"></i></a>
                                <a href="/save" id="save_${currentUser.id}" class="saveData"
                                   onclick="event.preventDefault();saveData(${currentUser.id});"
                                   style="display: none;"><i class="fa fa-save"></i></a>
                            </td>
                        </tr>
                        </tbody>

                    </table>

                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="templates/pageScript.jsp"/>
</body>
</html>