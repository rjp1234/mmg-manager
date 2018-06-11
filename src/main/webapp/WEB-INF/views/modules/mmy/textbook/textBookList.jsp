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
	

	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	function delById(e,id){
		var con=confirm("请确认是否删除该教材？该教材下所有单元、课文信息均会失去关联，需要重新整理");
		if(!con){
			return;
		}
		con=confirm("请进行最终确认");
		if(!con){
			return;
		}
		
		$.ajax({
			url : '${ctx}/operator/textbook/delById',
			type : 'post',
			data : "id=" + id,
			dataType : 'json',
			success : function(res) {
				var flag = res.flag;
				if (!flag) {
					alertx("删除失败，请联系管理员");
					return;
				}else{
					$(e).parent().html("<span>删除成功</span>");
				}

			}});
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
		<li class="active"><a href="${ctx}/operator/textbook/textBookList">教材列表</a></li>
		<li ><a href="${ctx}/operator/textbook/textBookForm">教材添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="textBookInfo"
		action="${ctx}/operator/textbook/textBookList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>教材名称：</label> <form:input path='name'
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
				<th>教材名称</th>
				<th>封面</th>
				<th>组别</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="book" varStatus="status">
				<tr >
					<td>${status.index+1 }</td>
					<td>${book.name}</td>
					<td><img src="${book.image}" style="width: 70px;height: 45px"></td>
					<td>${book.gradeId}</td>
					<td>${book.creater}</td>
					<td>${book.createTime}</td>
					<td><input class="btn" type="button" value="删除"
						onclick="delById(this,'${book.id}')"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>

</body>
</html>