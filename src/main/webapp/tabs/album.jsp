<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script type="text/javascript">
    var toolbar = [{
        iconCls: 'icon-add',
        text: "专辑详情",
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if (row != null) {
                if(row.id<10000){
                    //显示详情
                    $.messager.alert(
                        row.title+"的详细信息",
                        "章节数量:"+row.count+
                        "<br><img src=\"${pageContext.request.contextPath}/img/album"+row.coverImg+" \" style=\"height:150px;\">"+
                        "<br>评分:"+row.score+
                        "分<br>作者:"+row.book+
                        "<br>作者:"+row.book+
                        "<br>播音:"+row.broadcast+
                        "<br>简介:"+row.brief+
                        "<br>上架时间:"+row.pubDate,
                        "info"
                    )
                }else{
                    alert("选者错误");
                }
            } else {
                alert("请先选中行");
            }
        }
    }, '-', {
        text: "添加专辑",
        iconCls: 'icon-edit',
        handler: function () {
            //打开添加对话框
            $("#album_dialog1").dialog("open");
        }
    }, '-', {
        text: "添加音频",
        iconCls: 'icon-add',
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if(row != null){
                $("#album_dialog2").dialog("open");
            }else {
                alert("请先选择对话框");
            }
        }
    }, '-', {
        text: "音频下载",
        iconCls: 'icon-save',
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if(row.id>10000 && row!=null){
                $.get(
                    "${pageContext.request.contextPath}/album/download",
                    "url="+row.url,
                    function(data){
                        alert(data);
                    }
                );
            }else{
                alert("请选择音频后下载");
            }

        }
    }, '-', {
        text: "删除",
        iconCls: 'icon-save',
        handler: function () {
            var row = $("#album").treegrid("getSelected");
            if(row!=null){
                $.get(
                    "${pageContext.request.contextPath}/album/delete",
                    "id="+row.id,
                    function(data){
                        $('#album').treegrid("reload");
                        $.messager.show({
                            title:"提示",
                            msg:"删除成功；删除"+data+"行",
                            timeout:2000
                        })
                    },
                    "JSON"
                );

            }else{
                alert("请选择行")
            }
        }
    }]
    $(function () {
        $('#album').treegrid({
            method:"get",
            url:'${pageContext.request.contextPath}/album/queryPage',
            idField:'id',
            treeField:'title',
            columns:[[
                {field:'title',title:'名字',width:60},
                {field:'size',title:'时长',width:80},
                {field:'duration',title:'大小/M',width:80},
                {field:'uploadDate',title:'上架时间',width:80},
                {field:'album_1',title:'播放',formatter:album_fun2,width:80}
            ]],
            fit:true,
            fitColumns:true,
            toolbar:toolbar,
            pagination: true,
            pageList: [1, 2, 3, 5, 10],
            pageSize: 5,
            pagePosition:'top'

        });
        //添加专辑对话框 懒加载
        $("#album_dialog1").dialog({
            title:"添加专辑",
            width:300,
            height:250,
            closed:true,
            modal:true,
            onOpen:function () {
                $("#album_textbox1").textbox({
                    required:true
                });
                $("#album_textbox2").filebox({
                    required:true,
                    buttonText:"选择图片",
                    buttonAlign:"left"
                });
                $("#album_textbox3").numberspinner({
                    required:true,
                    editable:false,
                    max:10,
                    min:0
                 });
                $("#album_textbox4").textbox({
                    required:true
                });
                $("#album_textbox5").textbox({
                    required:true
                });
                $("#album_textbox6").textbox({
                    required:true
                });
               $("#album_textbox7").datetimebox({
                    required:true,
                    editable:false,
                   showSeconds:false
                });
            }
//reload
        })

        //添加专辑按钮
        $("#album＿linkbutton1").linkbutton({
            onClick:function(){
                $("#album_form1").form("submit",{
                    url:"${pageContext.request.contextPath}/album/insert",
                    onSubmit:function(){
                        //alert($("#album_form1").form("validate"));
                        return $("#album_form1").form("validate");
                        //return true;
                    },
                    success:function(){
                        $("#album_dialog1").dialog("close");
                        //$('#album').treegrid("reload");
                        $.messager.show({
                            title:"提示",
                            msg:"添加成功",
                            timeout:2000
                        })
                    }
                });
            }
        })

        //添加音频对话框 懒加载
        $("#album_dialog2").dialog({
            title:"添加",
            width:300,
            height:250,
            closed:true,
            modal:true,
            onOpen:function () {
                $("#album_textbox11").textbox({
                    required:true
                });
                $("#album_textbox12").timespinner({
                    required:true,
                    editable:false,
                    showSeconds:true
                });
                $("#album_textbox13").numberspinner({
                    required:true,
                    precision:2,
                    min:0
                });
                $("#album_textbox14").filebox({
                    required:true,
                    buttonText:"选择音频",
                    buttonAlign:"left"
                });
                $("#album_textbox15").textbox({
                    required:true,
                    editable:false
                });

                var row = $("#album").treegrid("getSelected");
                if(row.id<10000){
                    $("#album_textbox15").textbox("setValue",row.id);
                }else{
                    $("#album_textbox15").textbox("setValue",row.idAlbum);
                }
            }
        })

        //发送音频添加请求
        $("#album＿linkbutton2").linkbutton({
            onClick:function(){
                $("#album_form2").form("submit",{
                    url:"${pageContext.request.contextPath}/album/insertChapter",
                    onSubmit:function(){
                        return $("#album_form2").form("validate");
                    },
                    success:function(){
                        $("#album_dialog2").dialog("close");
                        //$('#album').treegrid("reload");
                        $.messager.show({
                            title:"提示",
                            msg:"添加成功",
                            timeout:2000
                        })
                    }
                });
            }
        })

        function album_fun2(value,row,index){
            if(row.id>10000) return "<audio src='${pageContext.request.contextPath}/img/album"+row.url+"' controls='controls'>播放</audio>";
        }
    })

</script>

<table id="album"></table>
<%--添加专辑--%>
<div id="album_dialog1">
    <form id="album_form1" method="post" enctype="multipart/form-data">
        名字:<input id="album_textbox1" type="text" name="title" ><br>
        选图:<input id="album_textbox2" type="text" name="file1" ><br>
        评分:<input id="album_textbox3" type="number" name="score" ><br>
        作者:<input id="album_textbox4" type="text" name="book" ><br>
        播音:<input id="album_textbox5" type="text" name="broadcast" ><br>
        简介:<input id="album_textbox6" type="text" name="brief" ><br>
        时间:<input id="album_textbox7" type="text" name="pubDate" ><br>
        <a id="album＿linkbutton1">保存</a>
    </form>

</div>

<div id="album_dialog2">
    <form id="album_form2" method="post" enctype="multipart/form-data">
        名字:<input id="album_textbox11" type="text" name="title" ><br>
        时长:<input id="album_textbox12" type="text" name="size" ><br>
        大小/M:<input id="album_textbox13" type="text" name="duration" ><br>
        音频:<input id="album_textbox14" type="text" name="file2" ><br>
        <input id="album_textbox15" type="hidden" name="idAlbum" >
        <a id="album＿linkbutton2">保存</a>
    </form>

</div>


<%--
<object height="100" width="100" data="${pageContext.request.contextPath}/img/album/1.mp3">fff</object>
function clickA(){

        if(a.paused){

            a.play();

        }else{

            a.pause();

        }

    }
--%>