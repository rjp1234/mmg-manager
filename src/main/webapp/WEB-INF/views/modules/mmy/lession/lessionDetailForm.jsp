<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>教材信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	top.$.jBox.tip.mess = null;
	var globalFlag = false;
	function submitCheck() {
		if (!globalFlag) {
			alertx("课文名称不得为空");
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
		return true;

	}
	function checkLessionName(e) {
		var lessionName = e.value;
		if (lessionName.length <=1) {
			$("#msg").html("<span style='color:red'>课文名称不得为空</span>");
			globalFlag=false;
			return;
		}
	 	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
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
					alertx("获取班级数据失败，请刷新重试");
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
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operator/lession/lessionList">课文列表</a></li>
		<li class="active"><a href="${ctx}/operator/lession/detailsForm?id=${lessionInfo.id}">课文详情</a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="lessionInfo"
		action="${ctx}/operator/lession/createLession" method="post"
		class="form-horizontal">
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">课文名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50"
					oninput="checkLessionName(this)" readonly="true"/>	<input class='btn' value="查看学生完成情况" onclick="alertx('当前没有完成信息')">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">教材:</label>
			<div class="controls">
				<form:input path="textId" htmlEscape="false" maxlength="50"
					 readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单元:</label>
			<div class="controls">
			<input value="${unit}"  maxlength="50"
					 readonly="readonly"/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<form:input type="hidden" id="imageUrl" path="image" />
				<sys:ckfinder input="imageUrl" type="images" uploadPath="/files"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
			<br> <br>
		</div>
		<div class="control-group">
			<label class="control-label">示范录音:</label>
			<div class="controls" id="ex">
				<audio controls="controls" src="${lessionInfo.exampleUrl}"></audio>
			</div>
	</div>
		<div class="control-group">
			<label class="control-label">教师的话（录音）:</label>
			<div class="controls" id="tstudio">
				<audio controls="controls" src="${lessionInfo.tStudioUrl}"></audio>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">教师的话（文本）:</label>
			<div class="controls">
				<form:textarea style="width:600px" path="tContent"
					htmlEscape="false" rows="3" class="input-xlarge" readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课文内容:</label>
			<div class="controls">
				<form:textarea style="width:600px;" path="content"
					id="content" htmlEscape="false" rows="3" class="input-xlarge" readonly="true"/>
			</div>
		</div>
	</form:form>
</body>
</html>