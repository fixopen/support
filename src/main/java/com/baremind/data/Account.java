package com.baremind.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="subject_type")
    private String subjectType;

    @Column(name="subject_id")
    private Long subjectId;

    @Column(name="active")
    private Integer active;

    @Column(name="type")
    private Integer type = 0;   //9版权审核人员，2版权登记(出版社)，-1管理员，0普通

    @Transient
    private String typeStr;

    @Column(name="begin")
    private Date begin;

    @Column(name="\"end\"")
    private Date end;

    @Column(name="login_name")
    private String loginName;

    @Column(name="password")
    private String password;

    @Column(name="can_change_password")
    private Integer canChangePassword = 0;

    @Column(name="password_question")
    private String passwordQuestion;

    @Column(name="password_answer")
    private String passwordAnswer;

    @Column(name="ip_address_created")
    private String ipAddressCreated;

    @Column(name="ip_address_last")
    private String ipAddressLast;

    @Column(name="last_login_time")
    private Date lastLoginTime;

    @Column(name="login_count")
    private Integer loginCount = 0;

    @Column(name="password_failed_count")
    private Integer passwordFailedCount = 0;

    @Column(name="current_certificate")
    private String currentCertificate;

    @Column(name="session_id")
    private String sessionId;

    @Column(name="last_opereation_time")
    private Date lastOpereationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

//    public String getSubjectTypeString() {
//        return subjectTypeString;
//    }
//
//    public void setSubjectTypeString(String subjectTypeString) {
//        this.subjectTypeString = subjectTypeString;
//    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }


    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(Integer canChangePassword) {
        this.canChangePassword = canChangePassword;
    }

    public String getPasswordQuestion() {
        return passwordQuestion;
    }

    public void setPasswordQuestion(String passwordQuestion) {
        this.passwordQuestion = passwordQuestion;
    }

    public String getPasswordAnswer() {
        return passwordAnswer;
    }

    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }

    public String getIpAddressCreated() {
        return ipAddressCreated;
    }

    public void setIpAddressCreated(String ipAddressCreated) {
        this.ipAddressCreated = ipAddressCreated;
    }

    public String getIpAddressLast() {
        return ipAddressLast;
    }

    public void setIpAddressLast(String ipAddressLast) {
        this.ipAddressLast = ipAddressLast;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getPasswordFailedCount() {
        return passwordFailedCount;
    }

    public void setPasswordFailedCount(Integer passwordFailedCount) {
        this.passwordFailedCount = passwordFailedCount;
    }

    public String getCurrentCertificate() {
        return currentCertificate;
    }

    public void setCurrentCertificate(String currentCertificate) {
        this.currentCertificate = currentCertificate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLastOpereationTime() {
        return lastOpereationTime;
    }

    public void setLastOpereationTime(Date lastOpereationTime) {
        this.lastOpereationTime = lastOpereationTime;
    }
}
