<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>组名管理</title>
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
		<li class="active"> <a href="${ctx}/operator/grade">组名列表</a></li>
		<li ><a
			href="${ctx}/operate/gatheringPlace/noteShowList"></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gatheringInfo2back"
		action="${ctx}/operate/gatheringPlace/noteShowList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<!-- 检索条件：组名 -->
			<li><label>组名：</label> <form:input id="title" path="title"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th style="width:10px">序号</th>
				<th>组名</th>
				<th>id</th>
				<th>创建人</th>
				<th>创建日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="grade" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${grade.name}</td>
					<td>${grade.id}</td>
					<td>${grade.creater}</td>
					<td>${grade.createTime}</td>
					<td
						ondblclick="noteSortChangeBegin('${note.sort}','${status.index}')"><input
						class="input-medium" id="sort${status.index}" value='${note.sort}'>&nbsp;<a
						onclick="return sortChange('${status.index}','${note.id}',this)"
						class="btn btn-primary" href="">更改</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>
	
</body>
</html>