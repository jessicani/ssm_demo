//上传游记头图
$("#myfile").change(function () {
    $("#headImgForm").ajaxSubmit({
        type:"POST",
        url:"/ssm_demo/note/headImgUpload",
        dataType:"json",
        success:function (result) {
           var filePath = result.data.filePath;
           console.log("file upload result:"+JSON.stringify(filePath));
           var imgUrl="url(/ssm_demo/"+filePath+")";
           console.log("imgUrl"+imgUrl);
           $(".set-note-bg").css("backgroundImage",imgUrl);
           $(".setNoteHeaderImgForm").hide();
           $(".setNoteHeaderImgTip").hide();
           $("#headImgPath").val(imgUrl);
        }

        });
});

//提交添加的游记
$("#submitNoteBtn").click(function () {
    $.ajax({
        type:"POST",
        url:"/ssm_demo/note/add",
        data:$("#noteForm").serialize(),
        success:function (result) {
            if (result.status=="SUCCESS"){
                //添加成功跳转到游记详情页
                window.location.href="/ssm_demo/note/goToDetail"
            } else {
                //添加失败
                $("#note_tip").text(result,data.reason);
            }
        }

    });
});

