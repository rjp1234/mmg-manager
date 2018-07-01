<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>班级管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table-zh-CN.js"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/table/bootstrap-table.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	window.onload=function(){
	}

	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	
	
</script>
<style type="text/css">
.windowsClass {
	top: 15%;
	position: absolute;
	left: 20%;
	background-color: #fff;
	border: 1px solid #000;
	width: 500px;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operator/userOperation/userList">学生列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userOperationInfo"
		action="${ctx}/operator/userOperation/userList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<!-- 检索条件：组名 -->
				<li id="classLi"><label>班级名：</label> <form:select id="classId"
						path="classId"  width="20%">
						<form:option value="">不限</form:option>
						<c:forEach items="${classList}" var="classInfo">
							<form:option value="${classInfo.id}">${classInfo.name}</form:option>
						</c:forEach>
					</form:select></li>
			<li><label>学生姓名：</label> <form:input path='userName'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th style="width:10px">序号</th>
				<th>学生名称</th>
				<th>班级</th>
				<th>作业完成状况</th>
				<th>成绩分布</th>
				<th>平均值</th>
				<th>班级排名</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="user" varStatus="status">
				<tr id="tr${status.index}">
					<td>${status.index+1 }</td>
					<td>${user.userName}</td>
					<td>${user.className}</td>
					<td><span style="color:red"><c:if test="${user.compelteWork==user.totalWork}"><span style="color:green"></c:if>${user.compelteWork}/${user.totalWork}<c:if test="${user.compelteWork==user.totalWork}"></span></c:if></span></td>
					<td>
						<span style='color:green'>A-${user.numA}</span>&nbsp;
						<span style='color:	#CD8500'>P-${user.numP}</span>&nbsp;
						<span style='color:red'>E-${user.numE}</span>
				</td>
					<td>${user.avg }</td>
					<td>${user.ranking+1 }</td>
					<td><a href="${ctx}/operator/userOperation/userStudioList?userId=${user.userId}&classId=${userOperationInfo.classId}&userName=${userOperationInfo.userName}">查看详细</a>&nbsp;<a onclick="alertx('功能待开发')">短信发送</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>

</body>
</html>