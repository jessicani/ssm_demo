$.get("/ssm_demo/note/myNoteList",{},function (result) {

   var noteList= result.data.myNoteList;
   var customer= result.data.customer;
    console.log("noteList...................:"+noteList);
    console.log("customer...................:"+customer);

   $.each(noteList,function (index,item) {
       console.log("noteHeadImg:"+ item.noteHeadImg);
       console.log("noteId:"+ item.noteId);
       console.log("custId:"+ item.custId);
       console.log("noteTitle:"+ item.noteTitle);
       console.log("noteTripDate:"+ item.noteTripDate);
       console.log("noteCity:"+ item.noteCity);
       console.log("============================index:"+index);

       var ele = "  <li style=\"list-style: none;float: left;margin-left: 150px;margin-bottom: 40px\">\n" +
           "                                    <a href=\"javascript:;\" >\n" +
           "                                        <div class=\"noteHeadImg\" style=\"width: 300px;height: 150px;border: 1px solid grey;;background-repeat: no-repeat \"></div>\n" +
           "                                        <span class=\"noteTitle\" style=\"font-weight: bold;font-size: 16px;margin-right: 30px;display: block\"></span>\n" +
           "                                        <span class=\"noteCreateTime\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +
           "                                        <span class=\"custName\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +
           "                                        <span  style=\"font-weight: bold;font-size: 16px\">,城市：</span>\n" +
           "                                        <span class=\"noteCity\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +

           "                                    </a>\n" +
           "           </li>";

       //渲染数据
       $("#mynote_list").append(ele);

       var li =$("#mynote_list").children().eq(index);
       var a=li.children().eq(0);
       a.attr("name",item.noteId);
       console.log("li:" + li);
       console.log("a:" + a);

       a.children().eq(0).css("backgroundImage","url('/ssm_demo/images/mfw/gonglv_list_gl1.jpeg')");
       a.children().eq(1).text(item.noteTitle);
       a.children().eq(2).text(  new Date(item.noteCreateTime).format("Y-m-d"));
       a.children().eq(3).text(customer.custName);
       a.children().eq(5).text( item.noteCity);

   });
});

//事件冒泡

var ul=$("#mynote_list");
console.log("ul:"+ul.html());
ul.click(function (event) {
    alert("cilck:"+event.target);
    var ele=event.target;
    console.log("ele:"+ele.nodeName);
    if (ele.nodeName=="DIV" || ele.nodeName=="SPAN"){
        var a =ele.parentNode;
        alert("typeof a:"+typeof (a));
        alert(a.getAttribute("name"));
        var noteId=a.getAttribute("name");
        alert("noteId:"+noteId);
        console.log("note_Id:"+noteId);
        window.location.href="/ssm_demo/note/goToDetail";
    }
});