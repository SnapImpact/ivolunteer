<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Admin
    Created on : Nov 7, 2008, 1:05:05 AM
    Author     : dave
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page id="page1">
            <webuijsf:html id="html1">
                <webuijsf:head id="head1">
                    <webuijsf:link id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body id="body1" style="-rave-layout: grid">
                    <webuijsf:form id="form1">
                        <webuijsf:radioButtonGroup id="radioButtonGroup1" items="#{Admin.radioButtonGroup1DefaultOptions.options}"
                            onClick="webui.suntheme4_2.common.timeoutSubmitForm(this.form, 'radioButtonGroup1');"
                            style="position: absolute; left: 144px; top: 96px" valueChangeListenerExpression="#{Admin.radioButtonGroup1_processValueChange}"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
