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
top.$.jBox.tip.mess = null;	
function changeGradeName (gradeId) {
	var flag=confirm('请确认是否更改组别名称');
	if(!flag){
		return;
	}
	var name=$("#name"+gradeId).val();

	$.ajax({
		url : '${ctx}/operator/grade/updateGradeName',
		type : 'post',
		data : "id=" + gradeId+"&name="+name,
		dataType : 'json',
		success : function(data) {
			if(data.flag==1){
				$("#gradename"+gradeId).html(name);
				$("#name"+gradeId).val('');
			}else if(data.flag==-1){
				alertx('更改失败，已存在相同名称的组')
			}else{
				alertx('更改失败，请联系管理员')
			}
		}
	});
	
}

function insertGrade () {
	var gradeName=$("#gradename").val();
	var flag=confirm('请确认是否新增"'+gradeName+'"作为新的分组?');
	if(!flag){
		return;
	}

	$.ajax({
		url : '${ctx}/operator/grade/createGrade',
		type : 'post',
		data : "name="+gradeName,
		dataType : 'json',
		success : function(res) {
			if(res.flag==1){
				var data=res.data;
				var id=data.id;
				var name=data.name;
				var creater=data.creater;
				var createTime=data.createTime;
				var i=0;
				while($("#tr"+i).length>0){
					console.log($("#tr"+i));
					i++;
				}
			/**	<td>${status.index+1 }</td>
				<td  id="gradename${grade.id}">${grade.name}</td>
				<td>${grade.id}</td>
				<td>${grade.creater}</td>
				<td>${grade.createTime}</td>
				<td
				><input id="name${grade.id}"  class="input-medium" >&nbsp;<input
					onclick="changeGradeName('${grade.id}')" type="button"
					class="btn btn-primary"  value="更改"></td>*/
				var index=i+1;
				var str="<tr id='#tr"+i+"'><td>"+index+"</td><td id='gradename"+id+"'>"+name+"</td><td>"+id+"</td><td>"+creater+"</td><td>"+createTime+"</td>"+
				"<td><input id='name"+id+"'  class=\"input-medium\" >&nbsp;<input onclick=\"changeGradeName('"+id+"')\" type=\"button\" class=\"btn btn-primary\"  value=\"更改\"></td>"+"</tr>";
				
				$("#tbody").append(str);
				
				
			}else if(res.flag==-1){
				alertx('新增失败，已存在相同名称的组')
			}else{
				alertx('新增失败，请联系管理员')
			}
		}
	});
	
}

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
		<li class="active"> <a href="${ctx}/operator/grade/gradeList">组名列表</a></li>
		<li ><a
			href="${ctx}/operate/gatheringPlace/noteShowList"></a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="gradeInfo"
		action="${ctx}/operator/grade/gradeList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<!-- 检索条件：组名 -->
			<li><label>组名：</label> <form:input id="title" path="name"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li><label>新增组名：</label> <input id="gradename" 
					 maxlength="50" class="input-medium" /></li>
			<li class="btns"><input class="btn btn-primary"
				type="button" value="新增" onclick="insertGrade()"/></li>
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
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tbody">
			<c:forEach items="${page.list}" var="grade" varStatus="status">
				<tr id="tr${status.index }">
					<td>${status.index+1 }</td>
					<td  id="gradename${grade.id}">${grade.name}</td>
					<td>${grade.id}</td>
					<td>${grade.creater}</td>
					<td>${grade.createTime}</td>
					<td
					><input id="name${grade.id}"  class="input-medium" >&nbsp;<input
						onclick="changeGradeName('${grade.id}')" type="button"
						class="btn btn-primary"  value="更改">&nbsp;<a class="btn btn-primary" onclick="return doubleCheck('请确认是否删除该组？该组下所有信息将会失去关联需要重新设置')" href="${ctx}/operator/grade/delete?id=${grade.id}">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<div class="pagination">${page}</div>
	
</body>
</html>