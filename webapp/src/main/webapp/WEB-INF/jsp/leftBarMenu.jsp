<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="<c:url value="/"/> " class="site_title">
                <div class="logo floatingL">
                    <img src="<c:url value="/resources/img/HHLogo.png"/>" alt="Logo" />
                </div>
                <div class="floatingL">Home-Helper</div>
            </a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src="<c:url value="/resources/img/img.jpg"/>" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span><spring:message code="leftBarMenu.welcome" /></span>
                <h2><c:out value="${providerName}"/></h2>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- /menu profile quick info -->

        <br />

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <h3><spring:message code="general.general"/></h3>
                <ul class="nav side-menu">
                    <li><a href="<c:url value="/sprovider" />"><i class="fa fa-home"></i> <spring:message code="general.home"/></a></li>
                    <li><a><i class="fa fa-user"></i><spring:message code="general.profile"/> <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <li><a href="<c:url value="/sprovider/editProfile" /> "><spring:message code="leftBarMenu.edit-profile"/></a></li>
                            <li><a href="<c:url value="/profile/${providerId}?ownerview"/> "><spring:message code="leftBarMenu.profile-preview"/></a></li>
                        </ul>
                    </li>
                    <li><a href="<c:url value="/sprovider/messages"/> "><i class="fa fa-comments"></i> <spring:message code="general.messages"/></a></li>
                    <li><a href="<c:url value="/sprovider/appointments"/> "><i class="fa fa-table"></i> <spring:message code="general.appointments"/></a></li>
                    <li><a href="<c:url value="/sprovider/reviews" /> "><i class="fa fa-edit"></i> <spring:message code="general.reviews"/></a></li>
                </ul>
            </div>
        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons --
        <div class="sidebar-footer hidden-small">
            <a href="/"><spring:message code="leftBarMenu.use-client"/></a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>