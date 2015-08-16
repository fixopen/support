package com.baremind.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="certificate_revoke_logs")
public class CertificateRevokeLog {
    @Id
    private Long id;
}
