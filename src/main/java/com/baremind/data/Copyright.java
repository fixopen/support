package com.baremind.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="copyrights")
public class Copyright {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="no")
    private String no;

    @Column(name="resource_id")
    private Long resourceId;

    @Transient
    private Resource resource;

    @Column(name="owner_id")
    private Long ownerId;

    @Column(name="author_id")
    private Long authorId;

    @Column(name="expiration")
    private Date expiration;

    @Column(name="status")
    private int status;

    @Transient
    private String statusStr;

    public String getStatusStr() {


        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
