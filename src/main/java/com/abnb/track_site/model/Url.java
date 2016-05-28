package com.abnb.track_site.model;

import com.abnb.track_site.utility.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "urls", schema = "public")
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(min = 3, max = 2147483647)
    private String name;
    @Size(min = 5, max = 2147483647)
    @URL
    private String address;
    @Column(name = "last_version_hash")
    @JsonIgnore
    private String lastVersionHash;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Url() {
    }

    public Url(Integer id) {
        this.id = id;
    }

    public Url(Integer id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws IOException, NoSuchAlgorithmException {
        this.address = address;
        calculateLastVersionHash();

    }

    private void calculateLastVersionHash() throws IOException, NoSuchAlgorithmException {
        HttpGet request = new HttpGet(this.address);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String entityContents = EntityUtils.toString(entity);
        this.lastVersionHash = Util.toSHA1(entityContents);
    }

    public String getLastVersionHash() {
        return lastVersionHash;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Url)) {
            return false;
        }
        Url other = (Url) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.abnb.track_site.model.Url[ id=" + id + " ]";
    }

    @PrePersist
    public void prePersiste() {
        this.createdAt = new Date();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }

    public void update(Url sentUrl) {
        this.name = sentUrl.getName();
        this.address = sentUrl.getAddress();
    }
}
