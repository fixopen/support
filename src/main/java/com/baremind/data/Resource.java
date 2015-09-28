package com.baremind.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fixopen on 15/8/15.
 */
@Entity
@Table(name="resources")
public class Resource implements AutoCloseable {
    public Resource() {
    }
    public Resource(UploadMeta meta) {
        id = meta.getId();
        no = meta.getTextbookNum();
        name = meta.getCaption();
        chiefEditor = meta.getSubResumeCaption();
        associateEditor = meta.getMajorResumeCaption();
        quasiUseNo = meta.getAuthNum();
        grade = meta.getGradeTerm();
        stage = meta.getSectionCaption();
        type = meta.getEditionType();
        publisher = meta.getPress();
        publisherAddress = meta.getAddress();
        subject = meta.getSubjectCaption();
        classifier = meta.getBookKindCaption();
        version = meta.getEditionCaption();
        courseType = meta.getCursorType();
        intro = meta.getRemark();
        fileExt = meta.getFileExt();
        price = meta.getPrice();
        edition = meta.getPrice();
        reversion = meta.getReVersion();
        filePath = meta.getFileName();
        cover = meta.getCoverFile();
        author = meta.getAuthor();

    }

    @Id
    @Column(name="id")
    private Long id = 42l;

    @Column(name="no")
    private String no;

    @Column(name="name")
    private String name ;

    @Column(name="chief_editor")
    private String chiefEditor ;

    @Column(name="associate_editor")
    private String associateEditor ;

    @Column(name="quasi_use_no")
    private String quasiUseNo ;

    @Column(name="grade")
    private String grade ;

    @Column(name="stage")
    private String stage ;

    @Column(name="type")
    private String type ;

    @Column(name="type_id")
    private Long typeId ;

    @Column(name="publisher")
    private String publisher ;

    @Column(name="publisher_address")
    private String publisherAddress ;

    @Column(name="subject")
    private String subject ;

    @Column(name="subject_id")
    private Long subjectId ;

    @Column(name="classifier")
    private String classifier ;

    @Column(name="version")
    private String version ;

    @Column(name="course_type")
    private String courseType ;

    @Column(name="intro")
    private String intro ;

    @Column(name="file_path")
    private String filePath ;

    @Column(name="file_ext")
    private String fileExt ;

    @Column(name="cover")
    private String cover ;

    @Column(name="price")
    private String price ;

    @Column(name="edition")
    private String edition ;

    @Column(name="reversion")
    private String reversion ;

    @Column(name="digest")
    private String digest ;

    @Column(name="owner_type")
    private String ownerType ;

    @Column(name="owner_id")
    private Long ownerId ;

    @Column(name="author_id")
    private Long authorId ;

    @Column(name="isbn")
    private String isbn ;

    @Column(name="language")
    private Long language ;

    @Column(name="author")
    private String author = "无";

    @Transient
    private UploadLog uploadLog;

    @Column(name="time")
    private Date time;

    @Transient
    private String timeStr;

    @Transient
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(String chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public String getAssociateEditor() {
        return associateEditor;
    }

    public void setAssociateEditor(String associateEditor) {
        this.associateEditor = associateEditor;
    }

    public String getQuasiUseNo() {
        return quasiUseNo;
    }

    public void setQuasiUseNo(String quasiUseNo) {
        this.quasiUseNo = quasiUseNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getReversion() {
        return reversion;
    }

    public void setReversion(String reversion) {
        this.reversion = reversion;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getLanguage() {
        return language;
    }

    public void setLanguage(Long language) {
        this.language = language;
    }

    @Override
    public void close() throws Exception {
        //do nothing
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public UploadLog getUploadLog() {
        return uploadLog;
    }

    public void setUploadLog(UploadLog uploadLog) {
        this.uploadLog = uploadLog;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
