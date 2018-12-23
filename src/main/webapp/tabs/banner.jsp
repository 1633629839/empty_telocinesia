<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script type="text/javascript">
    $(function () {
        var toolbar = [{
            iconCls: 'icon-add',
            text: "添加",
            handler: function () {
                $("#banner_dialog").dialog("open");
            }
        }, '-', {
            text: "修改",
            iconCls: 'icon-edit',
            handler: function () {
                //获取选中行
                var row = $("#dg").edatagrid("getSelected");
                if (row != null) {
                    //编辑指定行
                    var index = $("#dg").edatagrid("getRowIndex", row);
                    $("#dg").edatagrid("editRow", index);
                } else {
                    alert("请先选中行")
                }


            }
        }, '-', {
            text: "删除",
            iconCls: 'icon-remove',
            handler: function () {
                var row = $("#dg").edatagrid("getSelected");
                if (row != null) {
                    //删除指定行
                    var index = $("#dg").edatagrid("getRowIndex", row);
                    $("#dg").edatagrid("destroyRow", index);
                } else {
                    alert("请先选中行")
                }
            }
        }, '-', {
            text: "保存",
            iconCls: 'icon-save',
            handler: function () {
                $("#dg").edatagrid("saveRow")

            }
        }]

        $('#dg').edatagrid({
            updateUrl: "${pageContext.request.contextPath}/banner/update",
            destroyUrl:"${pageContext.request.contextPath}/banner/delete",
            url:"${pageContext.request.contextPath}/banner/queryPage",

            columns: [[
                {field: 'title', title: '名称', width: 100},
                {
                    field: 'status', title: '状态', width: 100, editor: {
                        type: "text",
                        options: {required:true}
                    }
                },
                {field: 'pubDate', title: '时间', width: 100, align: 'right'}
            ]],
            fitColumns: true,
            fit: true,
            pagination: true,
            pageList: [1, 2, 3, 5, 10],
            pageSize: 5,
            pagePosition:'top',
            toolbar: toolbar,
            view: detailview,
            detailFormatter: function (rowIndex, rowData) {
                return '<table><tr>' +
                    '<td rowspan=2 style="border:0"><img src="${pageContext.request.contextPath}/img/banner' + rowData.imgPath + '" style="height:200px;"></td>' +
                    '<td style="border:0">' +
                    '<p>描述: ' + rowData.description + '</p>' +
                    '<p>日期: ' + rowData.pubDate + '</p>' +
                    '</td>' +
                    '</tr></table>';
            },
            destroyMsg:{
                norecord:{    // 在没有记录选择的时候执行
                    title:'Warning',
                    msg:'No record is selected.'
                },
                confirm:{       // 在选择一行的时候执行
                    title:'删除',
                    msg:'你确认删除本条数据吗?'
                }
            }
        });

        $("#banner_dialog").dialog({
			title:"添加",
			width:150,
			height:300,
			closed:true,
			//modal:true,
            onOpen:function () {
				$("#banner_textbox1").textbox({
                    required:true
				});
                $("#banner_textbox2").textbox({
                    required:true
                });
               /* $("#banner_textbox3").textbox({
                    required:true
                });*/
            }
//reload
		})

		$("#banner＿linkbutton").linkbutton({
			onClick:function(){
			    $("#banner_form").form("submit",{
			        url:"${pageContext.request.contextPath}/banner/insert",
					onSubmit:function(){
			           return $("#banner_form").form("validate");
					},
					success:function(){
                        $("#banner_dialog").dialog("close");
                        $('#dg').edatagrid("reload");
                        $.messager.show({
							title:"提示",
							msg:"添加成功",
							timeout:2000
						})
					}
				});
			}
		})

    })

</script>

<table id="dg"></table>
<div id="banner_dialog">
	<form id="banner_form" method="post" enctype="multipart/form-data">
		标题:<input id="banner_textbox1" type="text" name="title" ><br>
		描述:<input id="banner_textbox2" type="text" name="description" ><br>
		选者图片:<input id="banner_textbox3" type="file" name="file1" ><br>
		<a id="banner＿linkbutton">保存</a>
	</form>

</div>
