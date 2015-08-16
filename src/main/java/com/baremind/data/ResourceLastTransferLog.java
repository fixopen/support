package com.baremind.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="resource_last_transfer_logs")
public class ResourceLastTransferLog {
    @Id
    private Long id;
}
