<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>教材信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	var globalFlag = true;

	function submitCheck() {
		checkLessionName(document.getElementById("name"));
		var flag=${ lessionInfo.id==null ? true : false};
		if(!$("#name").val()||$("#name").val().length==0){
				alertx("课文名不得为空");
				return false;
		}
		
		//为新建
		if (!globalFlag) {
			alertx("课文名称有误");
			return false;
		}
		
		
		var len = $("#content").val().length;
		if (len == 0) {
			alertx("课文文本内容不得为空");
			return false;
		}
		len = $("#textId").val().length;
		if (len == 0) {
			alertx("请选择教材");
			return false;
		}
		len = $("#unit").val();
		if (len < 1) {
			alertx("请选择单元");
			return false;
		}
		len = $("#imageUrl").val().length;
		if (len == 0) {
			alertx("请选择封面");
			return false;
		}
		len = $("#exampleUrl").val().length;
		if (len == 0) {
			alertx("请选择示范录音");
			return false;
		}
		len = $("#tStudioUrl").val().length;
		if (len == 0) {
			alertx("请选择教师录音");
			return false;
		}
		len = $("#tContent").val().length;
		if (len == 0) {
			alertx("请选择教师录音");
			return false;
		}
		loading("正在上传中");
		return true;

	}
	function checkLessionName(e) {
		var lessionName = e.value;
		if (lessionName.length <=1) {
			$("#msg").html("<span style='color:red'>课文名称不得为空</span>");
			globalFlag=false;
			return;
		}
		var pattern = new RegExp("[`~!@#$^&*()=|{}'',\\[\\].<>《》/&*（）——|{}']");
		console.log(pattern.test(lessionName))
		  if(pattern.test(lessionName)){
        	$("#msg").html("<span style='color:red'>含标点符号等特殊字符，不允许提交</span>");
           	globalFlag=false;
           	return;
        }
		
		$.ajax({
			url : '${ctx}/operator/lession/checkLessionName',
			type : 'post',
			data : "name=" + lessionName,
			dataType : 'json',
			success : function(data) {
				$("#msg").html(data.msg);
				console.log(data);
				globalFlag = data.flag;
			}
		});
	}
	function textChange(e) {
		var id = e.value;
		$.ajax({
			url : '${ctx}/operator/textbook/getUnitInfoList',
			type : 'post',
			data : "id=" + id,
			dataType : 'json',
			success : function(res) {
				var flag = res.flag;
				if (!flag) {
					alertx("获取教材数据失败，请刷新重试");
					return;
				}
				var data = res.data;
				var element = $("#unit");
				var str = "<option value=''>请选择</option>";
				for (var i = 0; i < data.length; i++) {
					str = str + "<option value='" + data[i].unit + "'>" + data[i].name + "</option>";
				}
				element.html(str);
			}
		});
	}
	window.onload=function(){
	
	var unit=${ lessionInfo.unit};
	if($("#textId").val().length>0){
		$.ajax({
			url : '${ctx}/operator/textbook/getUnitInfoList',
			type : 'post',
			data : "id=" +$("#textId").val(),
			dataType : 'json',
			success : function(res) {
				var flag = res.flag;
				if (!flag) {
					alertx("获取教材数据失败，请刷新重试");
					return;
				}
				var data = res.data;
				var element = $("#unit");
				var str = "<option value=''>请选择</option>";
				for (var i = 0; i < data.length; i++) {
				if(data[i].unit==unit){
					str = str + "<option selected='true' value='" + data[i].unit + "'>" + data[i].name + "</option>";
					
				}else{
				str = str + "<option value='" + data[i].unit + "'>" + data[i].name + "</option>";
				}
				
				}
				element.html(str);
				$("#s2id_unit .select2-chosen").html("第"+unit+"单元");
			}
		});
	}
	}
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operator/lession/lessionList">课文列表</a></li>
		<li class="active"><a
			href="${ctx}/operator/lession/lessionForm?id= ${lessionInfo.id}">课文${ lessionInfo.id==null ? '添加' : '编辑'}</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="lessionInfo"
		action="${ctx}/operator/lession/${ lessionInfo.id==null ? 'createLession' : 'modifyLession'}"
		method="post" class="form-horizontal">
		<sys:message content="${message}" />
		<form:hidden path="id" />
		<div class="control-group">
			<label class="control-label">课文名称:</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" maxlength="50"
					oninput="checkLessionName(this)" />
				&nbsp;&nbsp;<span id="msg"></span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">教材:</label>
			<div class="controls">
				<form:select style="width: 20%" onchange="textChange(this)"
					path="textId" id="textId">
					<option value="">请选择</option>
					<c:forEach items="${textList}" var='text'>
						<form:option value="${text.id}">${text.name}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单元:</label>
			<div class="controls">
				<form:select path="unit" style="width:20%" id="unit">
					<form:option value='0'>请选择</form:option>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课文封面:</label>
			<div class="controls">
				<form:input type="hidden" id="imageUrl" path="image" />
				<sys:ckfinder input="imageUrl" type="images" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">示范录音:</label>
			<div class="controls">
				<form:input type="hidden" path="exampleUrl" id="exampleUrl" />
				<sys:ckfinder input="exampleUrl" type="studio" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">教师的话（录音）:</label>
			<div class="controls">
				<form:input type="hidden" path="tStudioUrl" id="tStudioUrl" />
				<sys:ckfinder input="tStudioUrl" type="studio" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">教师的话（文本）:</label>
			<div class="controls">
				<form:textarea style="width:600px;height:300px;" path="tContent"
					htmlEscape="false" rows="3" class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课文内容:</label>
			<div class="controls">
				<form:textarea style="width:600px;height:300px;" path="content" id="content"
					htmlEscape="false" rows="3" class="input-xlarge" />
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				onclick="return submitCheck();" value="保 存" />
		</div>
	</form:form>
</body>
</html>