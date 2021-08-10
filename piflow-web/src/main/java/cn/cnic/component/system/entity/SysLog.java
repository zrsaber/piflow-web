package cn.cnic.component.system.entity;

import cn.cnic.base.BaseHibernateModelUUIDNoCorpAgentId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "SYS_LOG")
public class SysLog extends BaseHibernateModelUUIDNoCorpAgentId {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String lastLoginIp;

    private String action;

    private Boolean status;

    private String result;

    private String comment;

    private Date ctrDttm;

    private Date lastUpdateDttm;

    private Boolean enableFlag;



}
