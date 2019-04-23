package com.chixing.controller;

import com.chixing.common.JsonResult;
import com.chixing.entity.Customer;
import com.chixing.entity.Note;
import com.chixing.service.CustomerService;
import com.chixing.service.NoteService;
import com.chixing.util.PageHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CustomerService customerService;

    //首页中获得最近的前十条游记对象
    @RequestMapping("/getLast")
    @ResponseBody
    public JsonResult getall(){
       List<Note> noteList= noteService.getLastTen();
        Map<String,Object> data= new HashMap<>();
        data.put("noteList",noteList);
        return JsonResult.createSuccessJsonResult(data);
    }
    @RequestMapping("/goToAdd")
    public String goToAdd(){
        return "note/add";
    }

    //游记头图上传
    @RequestMapping("headImgUpload")
    @ResponseBody
    //文件上传操作处理
    public JsonResult upload(HttpServletRequest request, MultipartFile upload){
      String path=  request.getServletContext().getRealPath("/upload");
       File file= new File(path);
       if(!file.exists()){
           file.mkdirs();
       }
       String fileName=upload.getOriginalFilename();
       String uuid= UUID.randomUUID().toString();

       fileName=uuid+"_"+fileName;
       try {
            File uploadFile= new File(file,fileName);
            upload.transferTo(uploadFile);
            Map<String,Object> data= new HashMap<>();
            data.put("filePath","upload/"+fileName);
            return JsonResult.createSuccessJsonResult(data);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.createFailJsonResult(null);
        }


    }

    //添加游记
    @RequestMapping("add")
    @ResponseBody
    public JsonResult save(Note note,HttpServletRequest request){
        //注意添加游记要关联游记的作者id -----当前登陆的用户id还有当前游记创建的时间

        int custId= (int) request.getSession().getAttribute("customerId");
        note.setCustId(custId);
        note.setNoteCreateTime(new Date());
        if (this.noteService.save(note)){
            return JsonResult.createSuccessJsonResult(null);
        }else {
           Map<String,Object> data= new HashMap<>();
           data.put("reason","添加游记失败");
            return JsonResult.createFailJsonResult(data);
        }

    }

    //查看刚写的游记
    @RequestMapping("myLastNote")
    @ResponseBody
    public JsonResult getMyLastNote(HttpServletRequest request){
     int custId= (int) request.getSession().getAttribute("customerId");
     Note myLastNote=  this.noteService.getMyLastNote(custId);
    Map<String,Object> data= new HashMap<>();
    data.put("myLastNote",myLastNote);
        return JsonResult.createSuccessJsonResult(data);
    }

    //我的游记列表
    @RequestMapping("myNoteList")
    @ResponseBody
    public JsonResult myNoteList(HttpServletRequest request) {
        int custId = (int) request.getSession().getAttribute("customerId");
        List<Note> myNoteList = this.noteService.getMyList(custId);
        Map<String, Object> data = new HashMap<>();
        if (myNoteList!=null && myNoteList.size()>0) {
           Customer customer= this.customerService.getById(custId);
            data.put("myNoteList", myNoteList);
            data.put("customer",customer);
            return JsonResult.createSuccessJsonResult(data);
        }else {
            data.put("reason","您暂无游记");
            return JsonResult.createFailJsonResult(data);
        }
    }


    //查看我最近的游记（刚写的游记）
    @RequestMapping("getById/{noteId}")
    @ResponseBody
    public  JsonResult  getById(@PathVariable("noteId") int noteId ){

        Note note =  this.noteService.getById(noteId);
        Map<String,Object> data = new HashMap<>();
        data.put("note",note);
        return   JsonResult.createSuccessJsonResult(data);

    }




    //分页查询游记
    @RequestMapping("getall/{pageNum}")
    @ResponseBody
    public  JsonResult getByPage(@PathVariable("pageNum")int pageNum){
        // 5条游记（cust_id）
        List<Note> noteList =  this.noteService.getAll(pageNum); // 当前页码的游记集合对象
        int totalCount = this.noteService.getCount();//游记总记录数
        int totalPage =  (int)  Math.ceil(totalCount% PageHelperUtil.PAGE_SIZE==0?totalCount/ PageHelperUtil.PAGE_SIZE:totalCount/ PageHelperUtil.PAGE_SIZE+1);//共几页
        //
        //游记作者的信息


        for(Note note:noteList){
            Customer customer =  this.customerService.getById(note.getCustId());
            note.setOthers1(customer.getCustName());
        }



        Map<String,Object> data = new HashMap<>();
        data.put("noteList",noteList);        // 当前页码的游记作者对象
        data.put("totalCount",totalCount);    //游记总记录数
        data.put("totalPage",totalPage);      //共几页
        return   JsonResult.createSuccessJsonResult(data);
    }

    //跳转到游记详情页面
    @RequestMapping("goToDetail")
    public String goToDetail(){
        return "note/detail";
    }

    //跳转到游记列表页面
    @RequestMapping("goToMyNoteList")
    public String goToMyNoteList(){
        return "note/mylist";
    }

    @RequestMapping("goToDetailById/{noteId}")
    public String goToDetail(@PathVariable("noteId") int noteId){
        System.out.println("noteId:"+noteId);
        return "note/detail.html?noteid="+noteId;
    }


}


