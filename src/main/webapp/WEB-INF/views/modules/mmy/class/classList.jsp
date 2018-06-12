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

function doubleCheck(msg){
	var flag=confirm(msg);
	if(flag){
		 flag=confirm('请再次确认是否进行删除操作');
		return flag;
	}
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
		<li><a href="${ctx}/operator/class/classForm?id=${classInfo.id}">班级${not empty classInfo.id?'编辑':'添加'}</a></li>
		<li class="active"><a href="${ctx}/operator/class/classList">班级列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="classInfo"
		action="${ctx}/operator/class/classList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<!-- 检索条件：组名 -->
			<li><label>班级名：</label> <form:input id="name" path="name"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			
			<li><label>组名：</label> <form:select path="gradeId" width="20%">
					<form:option value="">不限</form:option>
					<c:forEach items="${gradeList}" var="grade" >
						<form:option value="${grade.id}">${grade.name}</form:option>
					</c:forEach>
			</form:select></li>
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
				<th>班级名称</th>
				<th>组名</th>
				<th>创建人</th>
				<th>创建日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="classInfo" varStatus="status">
				<tr id="tr${status.index }">
					<td>${status.index+1 }</td>
					<td id="classname${classInfo.id}">${classInfo.name}</td>
					<td>${classInfo.gradeId}</td>
					<td>${classInfo.creater}</td>
					<td>${classInfo.createTime}</td>
					<td><a
						href="${ctx}/operator/class/classForm?id=${classInfo.id}" type="button"
						class="btn btn-primary" >编辑</a>&nbsp;<a
						class="btn btn-primary"
						onclick="return doubleCheck('请确认是否删除该班级？该班级下所有学生信息将会失去关联需要重新设置')"
						href="${ctx}/operator/class/delClassById?id=${classInfo.id}">删除</a>
						<a href="${ctx}/operator/class/getLessionByClassId?id=${classInfo.id}">查看下发课文</a>
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>

</body>
</html>