<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Basic Struts 2 Application - Welcome</title>
</head>

<body>
<h1>Welcome To Struts 2!</h1>
<p><a href="<s:url action='hello'/>">Hello World</a></p>

<s:url action="hello" var="helloLink">
  <s:param name="userName">Bruce Phillips</s:param>
</s:url>
 
<p><a href="${helloLink}">Hello Bruce Phillips</a></p>

<p>Get your own personal hello by filling out and submitting this form.</p>
 
<s:form action="hello">
 
  <s:textfield name="userName" label="Your name" />
     
   <s:submit value="Submit" />
 
</s:form>



<s:url action="registerInput" var="registerInputLink" />
<p><a href="${registerInputLink}">Please register</a> for our prize drawing.</p>

<p><a href="register.jsp">Please register</a> for our prize drawing.</p>


<p><a href='<s:url action="causeexception" />' >Cause Exception</a></p> 

<p><a href='<s:url action="causenullpointerexception" />' >Cause Null Pointer Exception</a></p> 

<p><a href='<s:url action="causesecurityexception" />' >Cause Global Security Exception</a></p> 

<p><a href='<s:url action="actionspecificexception" />' >Cause ActionSpecific Security Exception</a></p>

<a href="<s:url action="index" namespace="config-browser" />">Launch the configuration browser</a>

<br />
<h3>Registro espa�ol</h3>
<s:url action="registerInput" var="registerInputLinkES">
    <s:param name="request_locale">es</s:param>
</s:url>
<p></p><a href="${registerInputLinkES}">Por favor, reg�strese</a> para nuestro sorteo</p>

<hr />
<s:text name="contact" />
</body>

</html>
