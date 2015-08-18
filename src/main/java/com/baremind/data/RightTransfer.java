package com.baremind.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
