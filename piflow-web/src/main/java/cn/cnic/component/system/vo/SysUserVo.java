package cn.cnic.component.system.vo;

import cn.cnic.common.Eunm.SysRoleType;
import cn.cnic.component.system.entity.SysRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;


public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public String id;

    private Date crtDttm;
    private Date lastUpdateDttm;

    private Boolean enableFlag;

    private String username;

    private String password;

    private String name;

    private Integer age;

    private String sex;

    private Byte status;

    @Enumerated(EnumType.STRING)
    private SysRoleType role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public Date getCrtDttm() {
        return crtDttm;
    }

    public void setCrtDttm(Date crtDttm) {
        this.crtDttm = crtDttm;
    }

    public Date getLastUpdateDttm() {
        return lastUpdateDttm;
    }

    public void setLastUpdateDttm(Date lastUpdateDttm) {
        this.lastUpdateDttm = lastUpdateDttm;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public SysRoleType getRole() {
        return role;
    }

    public void setRole(SysRoleType role) {
        this.role = role;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
