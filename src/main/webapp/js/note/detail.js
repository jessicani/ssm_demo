//加载我的最近的游记的详情
$.get("/ssm_demo/note/myLastNote",{},function (result) {
    var note=result.data.myLastNote;
    console.log("note.noteHeadImg"+note.noteHeadImg);
    console.log("note.noteCreateTime"+note.noteCreateTime);
    console.log("note.noteCity"+note.noteCity);
    console.log("note.noteTitle"+note.noteTitle);
    console.log("note.noteContent"+note.noteContent);

    $(".set-note-bg").css("backgroundImage",note.noteHeadImg);

    $(".noteTripDate").html(new Date(note.noteTripDate).format("Y-m-d"));//日期获得的是毫秒数，所以要转换成日期格式显示在页面中
    $(".noteTripDays").html(note.noteTripDays);
    $(".notePartner").html(note.notePartner);
    $(".noteAvgCost").html(note.noteAvgCost);
    $(".note_content").html(note.noteContent);

});