package org.cloud.activiti.util;

import org.apache.shiro.SecurityUtils;
import org.cloud.activiti.entity.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {
    
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * ?取当前登?用??象
     * 
     * @return {ShiroUser}
     */
    public String getShiroUser() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * redirect跳?
     * 
     * @param url 目?url
     */
    protected String redirect(String url) {
        return new StringBuilder("redirect:").append(url).toString();
    }

    /**
     * ajax失?
     * 
     * @param msg 失?的消息
     * @return {Object}
     */
    public QueryResult renderError(String msg) {
        QueryResult result = new QueryResult();
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * 
     * @return {Object}
     */
    public QueryResult renderSuccess() {
        QueryResult result = new QueryResult();
        result.setSuccess(true);
        return result;
    }

    /**
     * ajax成功
     * 
     * @param msg 消息
     * @return {Object}
     */
    public QueryResult renderSuccess(String msg) {
        QueryResult result = new QueryResult();
        result.setSuccess(true);
        result.setMsg(msg);
        return result;
    }

    /**
     * ajax成功
     * 
     * @param obj 成功?的?象
     * @return {Object}
     */
    public QueryResult renderSuccess(Object obj) {
        QueryResult result = new QueryResult();
        result.setSuccess(true);
        result.setObj(obj);
        return result;
    }
}
