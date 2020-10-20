<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Activation and set password</title>
</head>

<body class="my-login-page">
<jsp:include page="templates/header.jsp"/>
<section class="h-100">
    <div class="container h-100">
        <div class="row justify-content-md-center h-100">
            <div class="card-wrapper">
                <div class="card fat">
                    <div class="card-body">
                        <h4 class="card-title">Activation and set password<br></h4>
                        <c:choose>
                            <c:when test="${not empty param.error}">
                                <label id="error" class="alert alert-danger">${param.error}</label>
                            </c:when>
                            <c:otherwise>
                                <form action="/setNewPwd" method="POST">
                                    <div class="form-group">
                                        <label for="username">Username</label>
                                        <input id="username" type="text" name="username" value="${username}"
                                               readonly class="form-control" style="font-weight: bold;">
                                    </div>

                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input id="password" type="password" class="form-control" name="password" required
                                               data-eye>
                                    </div>

                                    <div class="form-group no-margin">
                                        <button type="submit" class="btn btn-primary btn-block">
                                            Activate Account
                                        </button>
                                    </div>
                                </form>
                            </c:otherwise>
                        </c:choose>
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