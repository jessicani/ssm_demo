package com.chixing.common;

import lombok.Data;
/**
 * 控制层返回的统一结果类型
 */
import java.util.Map;
@Data
public class  JsonResult {

    /**
     * 返回结果的状态
     */
    private ResultStatus status;
    /**
     * 返回结果的类型
     */
    private Map<String,Object> data;
    /**
     * 返回数据的描述
     */
    private String message;

    public JsonResult(){}

    public JsonResult(ResultStatus status, Map<String, Object> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static JsonResult createSuccessJsonResult(Map<String, Object> data){

        return new JsonResult(ResultStatus.SUCCESS,data,"操作成功");
    }
    public static JsonResult createFailJsonResult(Map<String, Object> data){

        return new JsonResult(ResultStatus.FAIL,data,"操作失败");
    }
    public static JsonResult createErrorJsonResult(Map<String, Object> data){

        return new JsonResult(ResultStatus.ERROR,data,"操作错误");
    }
    public static JsonResult needLoginJsonResult(Map<String, Object> data){

        return new JsonResult(ResultStatus.NEED_LOGIN,data,"用户需要先登录");
    }
    public static JsonResult hasLoginJsonResult(Map<String, Object> data){

        return new JsonResult(ResultStatus.HAS_LOGIN,data,"用户已登录");
    }
}
