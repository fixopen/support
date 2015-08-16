package com.baremind.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fixopen on 16/8/15.
 */
@Entity
@Table(name="account_certificate_maps")
public class AccountCertificateMap {
    @Id
    private Long id;
}
