<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="<c:url value="/resources/img/favicon.png"/>"/>

    <title>Home-Helper</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/css/bootstrap.min.css"/>"
          rel="stylesheet"/>
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/adminTemplate/vendors/font-awesome/css/font-awesome.min.css"/>"
          rel="stylesheet"/>
    <!-- NProgress --
        <link href="<c:url value="/resources/adminTemplate/vendors/nprogress/nprogress.css"/>" rel="stylesheet">-->

    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/css/clientNavbarStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/generalStyles.css" />" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/createSProviderStyles.css" />" rel="stylesheet"/>
</head>
<body>

<div class="main_container">
    <!-- Fixed navbar -->
    <jsp:include page="clientMenu.jsp"/>

    <!-- main content -->
    <div class="main-content">
        <div class="container">
            <div class="title">
                <h2>Register as Service Provider</h2>
            </div>
            <div class="row">
                <section>
                    <div class="wizard">
                        <div class="wizard-inner">
                            <div class="connecting-line"></div>
                            <ul class="nav nav-tabs" role="tablist">

                                <li role="presentation" class="active">
                                    <a href="#step1" data-toggle="tab" aria-controls="step1" role="tab" title="User details">
                                        <span class="round-tab">
                                            <i class="fa fa-user"></i>
                                        </span>
                                    </a>
                                </li>

                                <li role="presentation" class="disabled">
                                    <a href="#step2" data-toggle="tab" aria-controls="step2" role="tab" title="Add Provider Description">
                                        <span class="round-tab">
                                            <i class="fa fa-pencil"></i>
                                        </span>
                                    </a>
                                </li>
                                <li role="presentation" class="disabled">
                                    <a href="#step3" data-toggle="tab" aria-controls="step3" role="tab" title="Add Apptitudes">
                                        <span class="round-tab">
                                            <i class="fa fa-list"></i>
                                        </span>
                                    </a>
                                </li>

                                <li role="presentation" class="disabled">
                                    <a href="#complete" data-toggle="tab" aria-controls="complete" role="tab" title="Done">
                                        <span class="round-tab">
                                            <i class="fa fa-check"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>

                        <c:url value="/client/createSProvider" var="postPath"/>
                        <form:form modelAttribute="createSProviderForm" action="${postPath}" method="Post">
                        <div class="tab-content">
                            <div class="tab-pane active" role="tabpanel" id="step1">
                                <div class="step1">
                                    <div class="form-container">
                                        <c:if test="${hasError == 1}">
                                            <div class="alert alert-danger">There are invalid fields in the form. Please fix them.</div>
                                        </c:if>
                                        <div class="subtitle">
                                            <h4>User details</h4>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6">
                                                <div class="form-group">
                                                    <form:label path="firstname">First Name:</form:label>
                                                    <form:input path="firstname" type="text" cssClass="form-control" placeholder="First name..." />
                                                    <form:errors path="firstname" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="lastname">Last Name:</form:label>
                                                    <form:input path="lastname" type="text" cssClass="form-control" placeholder="Last name..." />
                                                    <form:errors path="lastname" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6">
                                                <div class="form-group">
                                                    <form:label path="email">Email:</form:label>
                                                    <form:input path="email" type="text" cssClass="form-control" placeholder="Email..." />
                                                    <form:errors path="email" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="phone">First Name:</form:label>
                                                    <form:input path="phone" type="text" cssClass="form-control" placeholder="Phone..." />
                                                    <form:errors path="phone" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="pull-right btn-container">
                                            <button type="button" class="btn btn-success next-step">Save and continue</button>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="step2">
                                <div class="step2">
                                    <div class="form-container">
                                        <div class="subtitle">
                                            <h4>Provider Description</h4>
                                        </div>
                                        <div>
                                            <div class="form-group">
                                                <form:label path="profileDesc">Tell us about your job:</form:label>
                                                <form:textarea path="profileDesc" cssClass="form-control resize-vertical" placeholder="Write some description..."></form:textarea>
                                                <form:errors path="profileDesc" element="p" cssClass="form-error" />
                                            </div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step">Previous</button>
                                            </div>
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-success next-step">Save and continue</button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="step3">
                                <div class="step3">
                                    <div class="form-container">
                                        <div class="subtitle">
                                            <h4>Add your first apptitude</h4><h6>(You can add more later)</h6>
                                        </div>
                                        <div>
                                            <div class="apptitude-row">
                                                <div class="form-group">
                                                    <form:label path="serviceType">Service Type:</form:label>
                                                    <form:select path="serviceType" cssClass="form-control">
                                                        <form:option value="">Select a service type</form:option>
                                                        <c:forEach items="${serviceTypes}" var="st">
                                                            <form:option value="${st.serviceTypeId}"><c:out value="${st.name}" /></form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="serviceType" element="p" cssClass="form-error" />
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="aptDesc">Description:</form:label>
                                                    <form:textarea path="aptDesc" cssClass="form-control resize-vertical" placeholder="Write about your skill..."></form:textarea>
                                                    <form:errors path="aptDesc" element="p" cssClass="form-error" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step">Previous</button>
                                            </div>
                                            <div class="pull-right">
                                                <button type="button" class="btn btn-success next-step">Save and continue</button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" role="tabpanel" id="complete">
                                <div class="step4">
                                    <div class="form-container">
                                        <div class="finish">
                                            <h3>You are ready to start using as service provider</h3>
                                            <div class="img"></div>
                                        </div>
                                        <div class="btn-container">
                                            <div class="pull-left">
                                                <button type="button" class="btn btn-default prev-step">Previous</button>
                                            </div>
                                            <div class="pull-right">
                                                <form:button type="submit" class="btn btn-success">Save and use as provider</form:button>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>
                            </div>
                    </form:form>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="pull-right">
            © 2018 All rights reserved Home-Helper.com
        </div>
        <div class="clearfix"></div>
    </footer><!-- /footer content -->
</div>

<!-- jQuery -->
<script src="<c:url value="/resources/adminTemplate/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/resources/adminTemplate/vendors/bootstrap/dist/js/bootstrap.min.js"/>"></script>

<script src="<c:url value="/resources/js/createSProviderJs.js"/>"></script>


</body>
</html>