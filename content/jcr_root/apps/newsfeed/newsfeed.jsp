<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page session="false" import="org.apache.sling.api.resource.*, javax.jcr.*"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.apache.sling.api.resource.Resource,java.util.Iterator"%>

<sling:defineObjects />

<html xmlns="http://www.w3.org/1999/xhtml" class=" home-minimal-html  ">
<head>

<link href="/assets/css/newsfeed.css" rel="stylesheet" type="text/css" />
<link href="/assets/css/popup.css" rel="stylesheet" type="text/css" />
<link href="/assets/css/style-x.css" rel="stylesheet" type="text/css">
<link href="/assets/css/style-y.css" rel="stylesheet" type="text/css">

<title>Headlines</title>


<script type="text/javascript">
	function toggle_visibility(id) {
		var e = document.getElementById(id);
		if (e.style.display == 'block')
			e.style.display = 'none';
		else
			e.style.display = 'block';
	}
</script>


</head>

<body bgcolor="#E6E6FA">

	<div id="popupBoxOnePosition">
		<div class="popupBoxWrapper">
			<div class="popupBoxContent">
				<p style="background: antiquewhite">
				<span>UPDATE RSS Link</span>
					<a  style="padding-left: 75%;" href="javascript:void(0)"
						onclick="toggle_visibility('popupBoxOnePosition');">X</a>
				</p>
				<form method="POST" name="login" action="/content/newsdata">
					<span>RSS Link </span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" size="10" name="rssLink" />
					<input type="hidden" name=":redirect" value="/content/newsfeed.html" /> 
					<input type="submit" value="Submit" />
				</form>
			</div>
		</div>
	</div>

	<div id="popupBoxTwoPosition">
		<div class="popupBoxWrapper">
			<div class="popupBoxContent">
				<p>			
				</p>
				<p style="background: antiquewhite">
				<span>Create new page</span>
					<a  style="padding-left: 75%;" href="javascript:void(0)"
						onclick="toggle_visibility('popupBoxTwoPosition');">X</a>
				</p>
				<form method="POST" name="title" action="/content/newsfeed-ug/">
					<span>Title</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" size="20" name="jcr:title" id="title" /><br>
					<span>Description</span> &nbsp; 
					<input type="text" size="30" name="jcr:description" id="desc" /> 				
					<input type="hidden" size="30" name="sling:resourceType" value="newspage" id="desc" />
					<input type="hidden" name=":redirect" value="/content/newsfeed.html" /> <input type="submit"
						value="Submit" />
				</form>

			</div>
		</div>
	</div>

	<div class="row blog-hero headroom-topbar">
		<div class="cover"
			style="background-image: url('/assets/images/cover-image.jpg');"></div>

		<div class="hero-bottom-gradient" style="opacity: 1"></div>
		<div class="hero-dot-texture"></div>

		<div class="blog-nav">
			<div class="container">
				<div class="links-container" id="wrapper">
					<ul class="links ">
						<li><a href="javascript:void(0)" onclick="toggle_visibility('popupBoxOnePosition');">RSS LINK</a></li>
						<li><a href="javascript:void(0)" onclick="toggle_visibility('popupBoxTwoPosition');">NEW PAGE</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container post-header">
			<h1>Headlines</h1>
			<div class="author-bar">
				<div class="table-wrapper">
					<div class="table-cell">
						<div class="picture" title="Vicky Cassidy">
							<img
								src="/assets/images/logo.jpg"
								alt="TTND" />
						</div>
					</div>
					<div class="text table-cell vcenter">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="content-base blog" style="background-color: beige;">
		<div class="row">
			<div id="entry" class="container2 	content">
					<ul> 
					<%
						Iterator<Resource> it = resource.listChildren();
						while(it.hasNext()){
							Resource pages = it.next();
							String title = pages.getValueMap().get("jcr:title").toString();
							String path = pages.getPath() + ".html";
					%>
					<li class="dec">
					<a href= '<%= path %>' > <%= title %> </a> 
					<br><br>
					</li>
	<%}%>

	<%
	Resource resource1 = resourceResolver.getResource("/content/newsfeed-ug");
	if(resource1 != null){
		Iterator<Resource> it1 = resource1.listChildren();
		while(it1.hasNext()){
			Resource pages1 = it1.next();
			String title1 = pages1.getValueMap().get("jcr:title").toString();
			String path1 = pages1.getPath() + ".html";		
	%>
		<li class="dec">
			<a href= '<%= path1 %>' > <%= title1 %> </a> 
			<br><br>
	</li>
	<%}}%>
	</ul>
			</div>
		</div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	</div>
</body>
</html>
