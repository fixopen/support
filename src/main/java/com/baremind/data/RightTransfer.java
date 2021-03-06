package com.baremind.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="right_transfers")
public class RightTransfer {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="no")
    private String no;

    @Column(name="time")
    private Date time;

    @Transient
    private String bookNo;

    @Transient
    private String expirationTime;

    @Transient
    private String fromName;

    @Transient
    private String toName;

    @Transient
    private String resourceName;

    @Transient
    private String timeStr;

    @Column(name="copyright_id")
    private Long copyrightId;

    @Column(name="right_type_id")
    private Long rightTypeId;

    @Column(name="from_id")
    private Long fromId;

    @Column(name="to_id")
    private Long toId;

    @Column(name="amount")
    private int amount;

    @Column(name="expiration")
    private Date expiration;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getRightTypeId() {
        return rightTypeId;
    }

    public void setRightTypeId(Long rightTypeId) {
        this.rightTypeId = rightTypeId;
    }

    public Long getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(Long copyrightId) {
        this.copyrightId = copyrightId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
