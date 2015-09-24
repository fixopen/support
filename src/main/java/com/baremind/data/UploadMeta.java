package com.baremind.data;

/**
 * Created by fixopen on 16/8/15.
 */
public class UploadMeta {
    private Long id;
    private String textbookNum;
    private String caption;
    private String majorResumeCaption;
    private String subResumeCaption;
    private String authNum;
    private String gradeTerm;
    private String sectionCaption;
    private String editionType;
    private String press;
    private String address;
    private String subjectCaption;
    private String bookKindCaption;
    private String editionCaption;
    private String cursorType;
    private String remark;
    private String fileExt;
    private String price;
    private String pressType;
    private String reVersion;
    private String fileName;
    private String coverFile;
    private String author;
    //{
    // "id":0, -- id
    // "textbookNum":"XB-2011020-15S-02-01", -- no
    // "caption":"自然活动部分二年级第二学期（试用本）", -- name
    // "majorResumeCaption":"", -- chiefEditor
    // "subResumeCaption":"", -- associateEditor
    // "authNum":"XB-2011020", -- useNumber
    // "gradeTerm":"二年级", -- grade
    // "sectionCaption":"小学", -- stage
    // "editionType":"增强型", -- type 类别
    // "press":"音乐社", -- publisher
    // "address":"", -- publisher_address
    // "subjectCaption":"音乐", -- subject 科目
    // "bookKindCaption":"", -- classifier 类型
    // "editionCaption":"", -- version 版本
    // "cursorType":"基础型课程教学用书", -- courseType 课程类型
    // "remark":"", -- remark 注释 intro
    // "fileExt":"txt", -- file_ext
    // "price":"", -- price
    // "pressType":"", -- edition 版别
    // "reVersion":"", -- reversion 修订版
    // "fileName":"XB-2011020-15S-02-01.txt", -- file_path
    // "coverFile":"XB-2011020-15S-02-01.jpg" -- cover
    //}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextbookNum() {
        return textbookNum;
    }

    public void setTextbookNum(String textbookNum) {
        this.textbookNum = textbookNum;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMajorResumeCaption() {
        return majorResumeCaption;
    }

    public void setMajorResumeCaption(String majorResumeCaption) {
        this.majorResumeCaption = majorResumeCaption;
    }

    public String getSubResumeCaption() {
        return subResumeCaption;
    }

    public void setSubResumeCaption(String subResumeCaption) {
        this.subResumeCaption = subResumeCaption;
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    public String getGradeTerm() {
        return gradeTerm;
    }

    public void setGradeTerm(String gradeTerm) {
        this.gradeTerm = gradeTerm;
    }

    public String getSectionCaption() {
        return sectionCaption;
    }

    public void setSectionCaption(String sectionCaption) {
        this.sectionCaption = sectionCaption;
    }

    public String getEditionType() {
        return editionType;
    }

    public void setEditionType(String editionType) {
        this.editionType = editionType;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubjectCaption() {
        return subjectCaption;
    }

    public void setSubjectCaption(String subjectCaption) {
        this.subjectCaption = subjectCaption;
    }

    public String getBookKindCaption() {
        return bookKindCaption;
    }

    public void setBookKindCaption(String bookKindCaption) {
        this.bookKindCaption = bookKindCaption;
    }

    public String getEditionCaption() {
        return editionCaption;
    }

    public void setEditionCaption(String editionCaption) {
        this.editionCaption = editionCaption;
    }

    public String getCursorType() {
        return cursorType;
    }

    public void setCursorType(String cursorType) {
        this.cursorType = cursorType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPressType() {
        return pressType;
    }

    public void setPressType(String pressType) {
        this.pressType = pressType;
    }

    public String getReVersion() {
        return reVersion;
    }

    public void setReVersion(String reVersion) {
        this.reVersion = reVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(String coverFile) {
        this.coverFile = coverFile;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
