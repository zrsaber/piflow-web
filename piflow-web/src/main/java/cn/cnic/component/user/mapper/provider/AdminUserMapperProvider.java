package cn.cnic.component.user.mapper.provider;

import cn.cnic.base.utils.DateUtils;
import cn.cnic.base.utils.SqlUtils;
import cn.cnic.base.vo.UserVo;
import cn.cnic.common.Eunm.SysRoleType;
import cn.cnic.component.schedule.entity.Schedule;
import cn.cnic.component.system.entity.SysRole;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.user.mapper.AdminUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.directory.shared.kerberos.messages.TgsRep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;


public class AdminUserMapperProvider {

    private String id;
    private String username;
    private String password;
    private String name;
    private int age;
    private String lastUpdateUser;
    private String lastUpdateDttmStr;
    private long version;
    private int enableFlag;
    private String sex;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sysUser")
    private List<SysRole> roles;

    private boolean preventSQLInjectionUser(SysUser user) {
        if (null == user || StringUtils.isBlank(user.getLastUpdateUser())) {
            return false;
        }
        // Mandatory Field
        String lastUpdateDttm = DateUtils.dateTimesToStr(null != user.getLastUpdateDttm() ? user.getLastUpdateDttm() : new Date());
        this.username = SqlUtils.preventSQLInjection(user.getUsername());
        this.password = SqlUtils.preventSQLInjection(user.getPassword());
        this.name = SqlUtils.preventSQLInjection(user.getName());
        this.sex = SqlUtils.preventSQLInjection(user.getSex());

        this.enableFlag = ((null != user.getEnableFlag() && user.getEnableFlag()) ? 1 : 0);
        this.version = (null != user.getVersion() ? user.getVersion() : 0L);
        this.lastUpdateDttmStr = SqlUtils.preventSQLInjection(lastUpdateDttm);
        this.lastUpdateUser = SqlUtils.preventSQLInjection(user.getLastUpdateUser());


        /**
         * <p>
         *     role的获取需要解决
         * </p>
         */
//        List<SysRole> roles = user.getRoles();
//        this.roles = SqlUtils.preventSQLInjection(String.valueOf(roles))

//        // Selection field
//        String planStartTime = (null != schedule.getPlanStartTime() ? DateUtils.dateTimesToStr(schedule.getPlanStartTime()) : null);
//        String planEndTime = (null != schedule.getPlanEndTime() ? DateUtils.dateTimesToStr(schedule.getPlanEndTime()) : null);
//        this.scheduleId = SqlUtils.preventSQLInjection(schedule.getScheduleId());
//        this.type = SqlUtils.preventSQLInjection(schedule.getType());
//        this.statusStr = SqlUtils.preventSQLInjection(null != schedule.getStatus() ? schedule.getStatus().name() : "INIT");
//        this.cronExpression = SqlUtils.preventSQLInjection(schedule.getCronExpression());
//        this.planStartTimeStr = SqlUtils.preventSQLInjection(planStartTime);
//        this.planEndTimeStr = SqlUtils.preventSQLInjection(planEndTime);
//        this.scheduleRunTemplateId = SqlUtils.preventSQLInjection(schedule.getScheduleRunTemplateId());
//        this.scheduleProcessTemplateId = SqlUtils.preventSQLInjection(schedule.getScheduleProcessTemplateId());


        return true;
    }

    private void resetUser() {
//        this.id = null;
        this.username = null;
        this.lastUpdateDttmStr = null;
        this.version = 0L;
        this.enableFlag = 1;
        this.name = null;
        this.age = 0;
        this.sex = null;
    }

    /**
     * insert user
     *
     * @param user schedule
     * @return string sql
     */
    public String insert(SysUser user) {
        if (null == user) {
            return "SELECT 0";
        }
        this.preventSQLInjectionUser(user);

        StringBuffer strBuf = new StringBuffer();
        strBuf.append("INSERT INTO sys_user ");

        strBuf.append("( ");
        strBuf.append(SqlUtils.baseFieldName() + ", ");
        strBuf.append("username, password, name, age, sex ");
        strBuf.append(") ");

        strBuf.append("values ");
        strBuf.append("(");
        strBuf.append(SqlUtils.baseFieldValues(user) + ", ");
        strBuf.append(username + "," + password + "," + name + "," + age + "," + sex);
        strBuf.append(")");
        this.resetUser();
        return strBuf.toString() + ";";
    }


    public String getUserById(boolean isAdmin, String username, String id) {
        if (StringUtils.isBlank(id)) {
            return "SELECT 0";
        }
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("select * ");
        strBuf.append("from sys_user ");
        strBuf.append("where ");
        strBuf.append("enable_flag = 1 ");
        strBuf.append("and id = " + SqlUtils.preventSQLInjection(id) + " ");
        if (!isAdmin) {
            strBuf.append("and crt_user = " + SqlUtils.preventSQLInjection(username));
        }
        String sqlStr = strBuf.toString();
        return sqlStr;
    }


    public List<SysUser> getUserListByStatus(boolean isAdmin, SysRoleType status) {

        return null;
    }


    public String getUserList(boolean isAdmin, String username, String param) {
        String sqlStr = "SELECT 0";
        if (isAdmin) {
            StringBuffer sqlStrbuf = new StringBuffer();
            sqlStrbuf.append("SELECT * ");
            sqlStrbuf.append("FROM sys_user ");
            sqlStrbuf.append("WHERE enable_flag = 1 ");
            if (StringUtils.isNotBlank(param)) {
                sqlStrbuf.append("AND ");
                sqlStrbuf.append("( ");
                sqlStrbuf.append("name like CONCAT('%'," + SqlUtils.preventSQLInjection(param) + ",'%') OR ");
                sqlStrbuf.append("username like CONCAT('%'," + SqlUtils.preventSQLInjection(param) + ",'%') OR ");
                sqlStrbuf.append("crt_dttm like CONCAT('%'," + SqlUtils.preventSQLInjection(param) + ",'%')");
//                sqlStrbuf.append(" like CONCAT('%'," + SqlUtils.preventSQLInjection(param) + ",'%') ");
                sqlStrbuf.append(") ");
            }
            sqlStrbuf.append("ORDER BY crt_dttm asc,last_update_dttm DESC");
            sqlStr = sqlStrbuf.toString();
        }
        return sqlStr;
    }




    /**
     * update user
     *
     * @param user user
     * @return string sql
     */

    public String update(SysUser user) {

        String sqlStr = "SELECT 0";
        boolean flag = this.preventSQLInjectionUser(user);
        if (flag && StringUtils.isNotBlank(this.username)) {
            SQL sql = new SQL();
            // INSERT_INTO brackets is table name
            sql.UPDATE("sys_user");
            // The first string in the SET is the name of the field corresponding to the table in the database
            sql.SET("last_update_dttm = " + lastUpdateDttmStr);
            sql.SET("last_update_user = " + lastUpdateUser);
            sql.SET("version = " + (version + 1));

            // handle other fields
            sql.SET("enable_flag = " + enableFlag);
            sql.SET("id = " + this.id);
            sql.SET("sex = " + this.sex);
            sql.SET("username = " + this.username);
            sql.SET("name = " + this.name);
            sql.SET("password = " + this.password);
            sql.SET("age = " + this.age);
            sql.SET("lastUpdateUser = " + this.lastUpdateUser);
            sql.SET("lastUpdateDttmStr = " + this.lastUpdateDttmStr);
            sql.WHERE("version = " + this.version);
            sql.WHERE("id = " + this.id);
            sqlStr = sql.toString();
        }
        this.resetUser();
        return sqlStr;
    }

    public String delUserById(boolean isAdmin, String username, String id){
        if (StringUtils.isBlank(id)) {
            return "SELECT 0";
        }
        if (StringUtils.isBlank(username)) {
            return "SELECT 0";
        }
        StringBuffer strBuf = new StringBuffer();
        String lastUpdateDttm = DateUtils.dateTimesToStr(new Date());
        strBuf.append("update sys_user set ");
        strBuf.append("enable_flag = 0 , ");
        strBuf.append("last_update_user = " + SqlUtils.preventSQLInjection(username) + " , ");
        strBuf.append("last_update_dttm = " + SqlUtils.preventSQLInjection(lastUpdateDttm) + " ");
        strBuf.append("where ");
        strBuf.append("enable_flag = 1 ");
        strBuf.append("and id = " + SqlUtils.preventSQLInjection(id) + " ");
        if (!isAdmin) {
            strBuf.append("and crt_user = " + SqlUtils.preventSQLInjection(username));
        }
        String sqlStr = strBuf.toString();
        return sqlStr;
    }

}
