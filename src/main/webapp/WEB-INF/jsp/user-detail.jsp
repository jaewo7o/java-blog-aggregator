<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<br>
<br>

<script type="text/javascript">
	$(document).ready(function() {
		$('.nav-tabs a:first').tab('show'); // Select first tab 
	});
</script>


<!-- Nav tabs -->
<ul class="nav nav-tabs">
    <c:forEach items="${user.blogs}" var="blog">    
        <li><a href="#blog_${blog.id}" data-toggle="tab">${blog.name}</a></li>
    </c:forEach>
</ul>
<!-- Tab panes -->
<div class="tab-content">
    <c:forEach items="${user.blogs}" var="blog">    
        <div class="tab-pane" id="blog_${blog.id}">
            <h1>${blog.name}</h1>
            <p>${blog.url}</p>
            
            <a href="<spring:url value="/blog/remove/${blog.id}.html"/>" class="btn btn-danger">Remove Blog</a>
            
            <table class="table table-bordered table-hover table-striped">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Link</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${blog.items}" var="item">
                        <tr>
                            <td>${item.title}</td>
                            <td>${item.link}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>            
        </div>                
    </c:forEach>
</div>