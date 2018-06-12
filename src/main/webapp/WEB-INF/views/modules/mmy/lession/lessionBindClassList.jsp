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
	function cancel(classId,className) {
		if(!confirm('请确定是否取消下发班级:'+className)){
			return ;
		}
		var lessionId = '${lessionInfo.id}';
		console.log(classId);
		$.ajax({
			url : '${ctx}/operator/lession/cancelIssue',
			type : 'post',
			data : "lessionId=" + lessionId+"&classId="+classId,
			dataType : 'json',
			success : function(data) {
					alertx(data.msg);
					if(data.flag){
						$("#cancelIssue").html("<span style='color:green'>已移除</span>");
					}
				}
		});
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
		<li><a href="${ctx}/operator/lession/lessionList">课文列表</a></li>
		<li  class="active"><a href="${ctx}/operator/lession/getClassInfoListByLession?id=${lessionInfo.id}">课文下发班级列表</a></li>
	</ul>
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
			<c:forEach items="${classList}" var="classInfo" varStatus="status">
				<tr id="tr${status.index }">
					<td>${status.index+1 }</td>
					<td >${classInfo.name}</td>
					<td>${classInfo.gradeId}</td>
					<td>${classInfo.creater}</td>
					<td>${classInfo.createTime}</td>
					<td  id="cancelIssue"><input type="button" class="btn" value="移除下发" onclick="cancel('${classInfo.id}','${classInfo.name }')"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination"><a onclick="history.go(-1)">返回</a></div>

</body>
</html>