<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>图片上传</title>
    <style>
        h1{
            color:chartreuse;
            text-align: center;
        }
        .msg1{
            color: red;
        }
        .msg2{
            color: green;
        }
        .image{
            align-content: center;
            border-radius:25px;
            box-shadow: 20px 20px 20px lightsalmon;
            width: 800px;
            height: 750px;
        }
    </style>
</head>
<body>
<h1>图片上传</h1>
<form action="/fileUpload" method="post" enctype="multipart/form-data">
    <h4>选择要上传的文件：<input type="file" name="fileName" /></h4>
    <h4><input type="submit" name="提交" /></h4>
</form>
<#--判断是否上传成功-->
<#if msg??>
    <span class="msg1">${msg}</span><br>
<#else >
    <span class="msg2">${msg !("文件未上传")}</span>
</#if>

<#--显示上传的图片， 一定要在img中的src发请求给controller，否则直接跳转是乱码-->
<#if fileName??>
    <img class="image" src="/show?fileName=${fileName}" />
</#if>
</body>
</html>