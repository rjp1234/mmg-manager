<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>课文批改</title>
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
	function page(n, s) {
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
		<li class="active"><a href="${ctx}/operator/lessionOperation/lessionList">课文列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="lessionClassOperationInfo"
		action="${ctx}/operator/lessionOperation/lessionList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>课文名称：</label> <form:input path='lessionName'
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li><label>班级名称：</label> <form:select path='classId'
					htmlEscape="false" maxlength="50" class="input-medium">
				<form:option value=''>请选择</form:option>
					<c:forEach items="${classList}" var='cla'>
						<form:option value="${cla.id}">${cla.name}</form:option>
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
				<th>课文名称</th>
				<th>下发班级</th>
				<th>组别</th>
				<th>完成人数/总人数</th>
				<th>待批改人数</th>
				<th>分数分布</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="lession" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${lession.lessionName}</td>
					<td>${lession.className}</td>
					<td>${lession.gradeName}</td>
					<td>${lession.completeNum}/${lession.totalNum}</td>
					<td>${lession.completeNum-lession.pointedNum }</td>
					<td>
						<span style='color:green'>A-${lession.numA}</span>&nbsp;
						<span style='color:	#CD8500'>P-${lession.numP}</span>&nbsp;
						<span style='color:red'>E-${lession.numE}</span>
					</td>
					<td>
						<a href="${ctx}/operator/studio/studioList?lessionId=${lession.lessionId}&classId=${lession.classId}">点击批改</a>
					&nbsp;<a href="${ctx}/operator/studio/studioList?lessionId=${lession.lessionId}&classId=${lession.classId}&isPointed=1">查看已评分列表</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>
	
</body>
</html>