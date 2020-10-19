<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Update Password</title>
</head>

<body class="my-login-page">
<jsp:include page="templates/header.jsp"/>
<section class="h-100">
    <div class="container h-100">
        <div class="row justify-content-md-center h-100">
            <div class="card-wrapper">
                <div class="card fat">
                    <div class="card-body">
                        <h4 class="card-title">Update Password</h4>
                        <c:if test="${not empty param.error}">
                            <label id="error" class="alert alert-danger">${param.error}</label>
                        </c:if>
                        <form action="/setNewPassword" method="POST">

                            <div class="form-group">
                                <label for="UserName">Username</label>
                                <input id="userName" type="text" name="userName" value="${currentUser.userName}"
                                       readonly class="form-control" style="font-weight: bold;">
                            </div>

                            <div class="form-group">
                                <label for="oldPwd">Old Password</label>
                                <input id="oldPwd" type="password" class="form-control" name="oldPwd"
                                       required data-eye autofocus>
                            </div>

                            <div class="form-group">
                                <label for="newPwd">New Password</label>
                                <input id="newPwd" type="password" class="form-control" name="newPwd"
                                       required data-eye>
                            </div>

                            <div class="form-group no-margin">
                                <button type="submit" class="btn btn-primary btn-block">
                                    Update Password
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <jsp:include page="templates/copyright.jsp"/>
            </div>
        </div>
    </div>
</section>
<jsp:include page="templates/footer.jsp"/>
</body>
</html>