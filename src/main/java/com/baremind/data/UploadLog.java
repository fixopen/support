package com.baremind.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fixopen on 17/8/15.
 */
@Entity
@Table(name="upload_logs")
public class UploadLog {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="resource_no")
    private String resourceNo;

    @Column(name="time")
    private Date time;

    @Transient
    private String timeStr;

    @Column(name="uploader_id")
    private Long uploaderId;

    @Column(name="file_path")
    private String filePath;

    @Column(name="state")
    private int state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(String resourceNo) {
        this.resourceNo = resourceNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
