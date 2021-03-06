package com.yeahmobi.loadbalance_manager.model;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.google.code.morphia.annotations.Id;
import com.yeahmobi.loadbalance_manager.annotation.DefaultValue;

public class Upstream extends BaseEntity {

    @Id
    @NotBlank
    private String                    name;

    @DefaultValue(value = "32")
    private Integer                   keepalive;

    @DefaultValue(value = "false")
    private Boolean                   ipHash;

    private Map<String, List<Member>> regions;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKeepalive() {
        return this.keepalive;
    }

    public void setKeepalive(Integer keepalive) {
        this.keepalive = keepalive;
    }

    public Boolean getIpHash() {
        return this.ipHash;
    }

    public void setIpHash(Boolean ipHash) {
        this.ipHash = ipHash;
    }

    public Map<String, List<Member>> getRegions() {
        return this.regions;
    }

    public void setRegions(Map<String, List<Member>> regions) {
        this.regions = regions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.ipHash == null) ? 0 : this.ipHash.hashCode());
        result = (prime * result) + ((this.keepalive == null) ? 0 : this.keepalive.hashCode());
        result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
        result = (prime * result) + ((this.regions == null) ? 0 : this.regions.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Upstream other = (Upstream) obj;
        if (this.ipHash == null) {
            if (other.ipHash != null) return false;
        } else if (!this.ipHash.equals(other.ipHash)) return false;
        if (this.keepalive == null) {
            if (other.keepalive != null) return false;
        } else if (!this.keepalive.equals(other.keepalive)) return false;
        if (this.name == null) {
            if (other.name != null) return false;
        } else if (!this.name.equals(other.name)) return false;
        if (this.regions == null) {
            if (other.regions != null) return false;
        } else if (!this.regions.equals(other.regions)) return false;
        return true;
    }

}
