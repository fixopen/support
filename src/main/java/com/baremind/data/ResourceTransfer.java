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
@Table(name="resource_transfers")
public class ResourceTransfer {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="resource_id")
    private Long resourceId;

    @Column(name="right_transfer_id")
    private Long rightTransferId;

    @Column(name="time")
    private Date time;

    @Column(name="sender_id")
    private Long senderId;

    @Column(name="sender_device_id")
    private Long senderDeviceId;

    @Column(name="sender_signatrue")
    private String senderSignatrue;

    @Column(name="receiver_id")
    private Long receiverId;

    @Column(name="receiver_device_id")
    private Long receiverDeviceId;

    @Column(name="receiver_signatrue")
    private String receiverSignatrue;

    @Column(name="key")
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getRightTransferId() {
        return rightTransferId;
    }

    public void setRightTransferId(Long rightTransferId) {
        this.rightTransferId = rightTransferId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getSenderDeviceId() {
        return senderDeviceId;
    }

    public void setSenderDeviceId(Long senderDeviceId) {
        this.senderDeviceId = senderDeviceId;
    }

    public String getSenderSignatrue() {
        return senderSignatrue;
    }

    public void setSenderSignatrue(String senderSignatrue) {
        this.senderSignatrue = senderSignatrue;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverDeviceId() {
        return receiverDeviceId;
    }

    public void setReceiverDeviceId(Long receiverDeviceId) {
        this.receiverDeviceId = receiverDeviceId;
    }

    public String getReceiverSignatrue() {
        return receiverSignatrue;
    }

    public void setReceiverSignatrue(String receiverSignatrue) {
        this.receiverSignatrue = receiverSignatrue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
