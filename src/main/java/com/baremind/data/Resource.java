package com.baremind.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fixopen on 15/8/15.
 */
@Entity
@Table(name="resources")
public class Resource {
    @Id
    private Long id = 42l;

    @Column(name="owner_id")
    private Long ownerId;

    private String name = "dup";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
