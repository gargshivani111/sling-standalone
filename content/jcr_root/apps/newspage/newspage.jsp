<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page session="false"
	import="org.apache.sling.api.resource.*, javax.jcr.*,java.text.SimpleDateFormat"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling"%>

<sling:defineObjects />

<% 
ValueMap properties = resource.adaptTo(ValueMap.class);
String path = resource.getPath();
String title = properties.get("jcr:title", String.class);
String description = properties.get("jcr:description", String.class);
String date = properties.get("published", String.class);

if(date == null){
	date = properties.get("jcr:created", String.class);
}
%>

<html xmlns="http://www.w3.org/1999/xhtml" class=" home-minimal-html  ">
<head>

<script src="/assets/js/jquery-1.9.1.min.js"></script>
<script src="/assets/js/update-script.js"></script>

<link href="/assets/css/newsfeed.css" rel="stylesheet" type="text/css" />
<link href="/assets/css/style-x.css" rel="stylesheet" type="text/css">
<link href="/assets/css/style-y.css" rel="stylesheet" type="text/css">

<title><%= title %></title>
<body>
	<div class="row blog-hero headroom-topbar">
		<div class="cover"
			style="background-image: url('/assets/images/cover-image.jpg');"></div>

		<div class="hero-bottom-gradient" style="opacity: 1"></div>
		<div class="hero-dot-texture"></div>

		<div class="blog-nav">
			<div class="container">
				<div class="links-container">
					<ul class="links">
						<li><a href="/content/newsfeed.html">News Home</a></li>
						<li><a class="delete_news" href="#">Delete</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container post-header">
			<div class="changeable_title" id="edit" contenteditable="true">
			<h1><%= title %></h1>
			</div>

			<div class="author-bar">
				<div class="table-wrapper">
					<div class="table-cell">
						<div class="picture" title="Vicky Cassidy">
							<img src="/assets/images/logo.jpg" alt="TTND" />
						</div>
					</div>
					<div class="text table-cell vcenter">
						<%= date %>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="content-base blog" style="background-color: beige;">
		<div class="row">
			<div id="entry" class="container content">
				<div class="changeable_desc" id="edit" contenteditable="true">
					<p ><%= description %>
					</p>
					<br><br><br><br><br><br><br>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
