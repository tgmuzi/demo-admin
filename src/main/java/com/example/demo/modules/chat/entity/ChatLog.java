package com.example.demo.modules.chat.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author muzi
 * @since 2022-08-30
 */
@TableName("chat_log")
public class ChatLog extends Model<ChatLog> {

    private static final long serialVersionUID = 1L;

    @TableId("chat_ID")
    private Long chatId;
    /**
     * 发送方
     */
    @TableField("FROM_USER")
    private Long fromUser;
    /**
     * 接收方
     */
    @TableField("TO_USER")
    private Long toUser;
    /**
     * 加密
     */
    @TableField("MSG")
    private String msg;
    /**
     * 聊天类型
     */
    @TableField("TYPE")
    private int type;
    /**
     * 消息类型
     */
    @TableField("CODE")
    private int code;
    /**
     * 发送时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 发送时间戳
     */
    @TableField("CREATE_TIME_TS")
    private Long createTimeTs;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTimeTs() {
        return createTimeTs;
    }

    public void setCreateTimeTs(Long createTimeTs) {
        this.createTimeTs = createTimeTs;
    }

    @Override
    protected Serializable pkVal() {
        return this.chatId;
    }

    @Override
    public String toString() {
        return "ChatLog{" +
                ", chatId=" + chatId +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", msg=" + msg +
                ", type=" + type +
                ", code=" + code +
                ", createTime=" + createTime +
                ", createTimeTs=" + createTimeTs +
                "}";
    }
}
