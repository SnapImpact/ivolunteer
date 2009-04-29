<?xml version="1.0" encoding="UTF-8"?>
<!--
/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
 -->
<!-- 
    Document   : Admin
    Created on : Nov 7, 2008, 1:05:05 AM
    Author     : Dave Angulo
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
                    <webuijsf:form id="form2">
                        <webuijsf:radioButtonGroup id="radioButtonGroup2" items="#{Admin.radioButtonGroup2DefaultOptions.options}"
                            onClick="webui.suntheme4_2.common.timeoutSubmitForm(this.form, 'radioButtonGroup2');"
                            style="position: absolute; left: 144px; top: 296px" valueChangeListenerExpression="#{Admin.radioButtonGroup2_processValueChange}"/>
                    </webuijsf:form>
                    <webuijsf:form id="form3">
                        <webuijsf:staticText>Address:</webuijsf:staticText>
                        <webuijsf:textField label="addr"/>
                        <webuijsf:staticText>Latitude: </webuijsf:staticText>
                        <webuijsf:staticText binding="#{Admin.latitude}"/>
                        <webuijsf:staticText>Longitude:</webuijsf:staticText>
                        <webuijsf:staticText binding="#{Admin.longitude}"/>
                        <webuijsf:button actionExpression="#{Admin.encodeAddress_processValueChange}"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
