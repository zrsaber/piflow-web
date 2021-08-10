package cn.cnic.component.user.mapper.provider;

import cn.cnic.base.utils.DateUtils;
import cn.cnic.base.utils.SqlUtils;
import cn.cnic.component.dataSource.entity.DataSource;
import cn.cnic.component.system.entity.SysLog;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.system.vo.SysLogVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public class AdminLogMapperProvider {

    private String id;

    private String username;

    private String lastLoginIp;

    private String action;

    private Boolean status;

    private String result;

    private String comment;

    private String ctrDttmStr;

    private String lastUpdateDttmStr;

    private Boolean enableFlag;


    private boolean preventSQLInjectionLog(SysLog sysLog) {
        if (null == sysLog ) {
            return false;
        }
        // Mandatory Field
        String lastUpdateDttm = DateUtils.dateTimesToStr(null != sysLog.getLastUpdateDttm() ? sysLog.getLastUpdateDttm() : new Date());
        String ctrDttmStr = DateUtils.dateTimesToStr(null != sysLog.getLastUpdateDttm() ? sysLog.getLastUpdateDttm() : new Date());
        this.username = SqlUtils.preventSQLInjection(sysLog.getUsername());
        //this.id = SqlUtils.preventSQLInjection(sysLog.getId());

        this.action = SqlUtils.preventSQLInjection(sysLog.getAction());
        this.comment = SqlUtils.preventSQLInjection(sysLog.getComment());
        this.result = SqlUtils.preventSQLInjection(sysLog.getResult());
        this.enableFlag = (null != sysLog.getEnableFlag() && sysLog.getEnableFlag());
        this.status = (null != sysLog.getStatus() && sysLog.getStatus());
        this.lastUpdateDttmStr = SqlUtils.preventSQLInjection(lastUpdateDttm);
        this.ctrDttmStr = SqlUtils.preventSQLInjection(ctrDttmStr);
        this.lastLoginIp = SqlUtils.preventSQLInjection(sysLog.getLastLoginIp());

        return true;
    }

    private void resetLog() {
        this.id = null;
        this.username = null;
        this.lastUpdateDttmStr = null;
        this.status = true;
        this.enableFlag = true;
        this.lastLoginIp = null;
        this.result = null;
        this.comment = null;
        this.action = null;
    }

    public SysUser getLogById(boolean isAdmin, String username, @Param("id") String id) {
        return null;
    }


    public String getLogList(boolean isAdmin, String username,String param) {
        String sqlStr = "SELECT 0";
        if (isAdmin && StringUtils.isNotBlank(username)) {
            StringBuffer sqlStrbuf = new StringBuffer();
            sqlStrbuf.append("SELECT * ");
            sqlStrbuf.append("FROM sys_log ");
            sqlStrbuf.append("WHERE enable_flag = 1 ");
            sqlStr = sqlStrbuf.toString();
        }
        return sqlStr;
    }

    public String insertSelective(SysLog record) {
        if (null == record) {
            return "SELECT 0";
        }
        this.preventSQLInjectionLog(record);

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("INSERT INTO sys_log ");

        strBuf.append("( ");
//        strBuf.append(SqlUtils.baseFieldName() + ", ");
        strBuf.append("username, last_login_ip, action, status, result,comment,crt_dttm,last_update_dttm,enable_flag ");
        strBuf.append(") ");

        strBuf.append("values ");
        strBuf.append("(");
//        strBuf.append(SqlUtils.baseFieldValues(record) + ", ");
        strBuf.append(username + "," + lastLoginIp + "," + action + "," + status + "," +
                result + "," + comment +"," + ctrDttmStr +"," + lastUpdateDttmStr +"," + true );
        strBuf.append(")");
        this.resetLog();
        return strBuf.toString() + ";";
    }


}
