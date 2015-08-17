package com.baremind.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fixopen on 17/8/15.
 */
@Entity
@Table(name="upload_logs")
public class UploadLog {
    @Id
    @Column(name="id")
    private Long id;
}
