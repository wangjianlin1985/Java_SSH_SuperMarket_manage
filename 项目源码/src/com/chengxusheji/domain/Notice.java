package com.chengxusheji.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Notice {
    /*公告id*/
    private int noticeId;
    public int getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*公告日期*/
    private String noticeDate;
    public String getNoticeDate() {
        return noticeDate;
    }
    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonNotice=new JSONObject(); 
		jsonNotice.accumulate("noticeId", this.getNoticeId());
		jsonNotice.accumulate("title", this.getTitle());
		jsonNotice.accumulate("content", this.getContent());
		jsonNotice.accumulate("noticeDate", this.getNoticeDate().length()>19?this.getNoticeDate().substring(0,19):this.getNoticeDate());
		return jsonNotice;
    }}