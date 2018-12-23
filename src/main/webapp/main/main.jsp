<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>持名法州主页</title>
    <link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="../js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        $(function () {
            <!--菜单处理-->
            $.get(
                "${pageContext.request.contextPath}/menu/queryAll",
                function (data) {
                    $.each(data, function (index, menu) {
                        var a = "";
                        $.each(menu.list, function (index2, menu2) {
                            a +="<p style='text-align: center'><a id=\"btn\" href=\"#\" class=\"easyui-linkbutton\" onclick=\"main_fun1('" + menu.title + "','" + menu.iconcls + "','" + menu.url+ "')\" data-options=\"iconCls:'"+menu2.iconcls+"'\">" + menu2.title + "</a></p>";
                        })
                        $('#aa').accordion('add', {
                            title: menu.title,
                            iconCls: menu.iconCls,
                            content: a,
                            selected: false
                        });
                    })
                },
                "JSON"
            );
        })

        //单击导航栏子菜单，选中或者弹出选者框
        function main_fun1(fun_title, fun_iconcls, fun_url) {
            alert(fun_url);
            var a = $("#tt").tabs("exists", fun_title);
            if (a) {
                $("#tt").tabs("select", fun_title);
            } else {
                $('#tt').tabs('add', {
                    title: fun_title,
                    iconCls: fun_iconcls,
                    href: "${pageContext.request.contextPath}/"+fun_url,
                    selected: true,
                    closable: true

                });
            }
        }
    </script>

</head>
<body class="easyui-layout">
<div data-options="region:'north',split:true" style="height:60px;background-color:  #5C160C">
    <div style="font-size: 24px;color: #FAF7F7;font-family: 楷体;font-weight: 900;width: 500px;float:left;padding-left: 20px;padding-top: 10px">
        持名法州后台管理系统
    </div>
    <div style="font-size: 16px;color: #FAF7F7;font-family: 楷体;width: 300px;float:right;padding-top:15px">
        欢迎您:${sessionScope.userAdmin.name} ${pageContext.session.removeAttribute("loginException")}
        &nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改密码</a>&nbsp;&nbsp;<a href="#"
                                                                                                              class="easyui-linkbutton"
                                                                                                              data-options="iconCls:'icon-01'">退出系统</a>
    </div>
</div>
<div data-options="region:'south',split:true" style="height: 40px;background: #5C160C">
    <div style="text-align: center;font-size:15px; color: #FAF7F7;font-family: 楷体">&copy;百知教育 htf@zparkhr.com.cn</div>
</div>

<div data-options="region:'west',title:'导航菜单',split:true" style="width:220px;">
    <div id="aa" class="easyui-accordion" data-options="fit:true">

    </div>
</div>
<div data-options="region:'center'">
    <div id="tt" class="easyui-tabs" data-options="fit:true,narrow:true,pill:true">
        <div title="主页" data-options="iconCls:'icon-neighbourhood',"
             style="background-image:url(image/shouye.jpg);background-repeat: no-repeat;background-size:100% 100%;"></div>
    </div>
</div>
</body>
</html>