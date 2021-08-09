package cn.cnic.component.user.service.impl;

import cn.cnic.base.BaseHibernateModelNoId;
import cn.cnic.base.BaseHibernateModelNoIdUtils;
import cn.cnic.base.utils.*;
import cn.cnic.base.vo.UserVo;
import cn.cnic.common.Eunm.ScheduleState;
import cn.cnic.common.Eunm.SysRoleType;
import cn.cnic.component.schedule.entity.Schedule;
import cn.cnic.component.schedule.vo.ScheduleVo;
import cn.cnic.component.system.entity.SysRole;
import cn.cnic.component.system.entity.SysSchedule;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.system.mapper.SysRoleMapper;
import cn.cnic.component.system.mapper.SysScheduleMapper;
import cn.cnic.component.system.mapper.SysUserMapper;
import cn.cnic.component.system.vo.SysUserVo;
import cn.cnic.component.user.mapper.AdminUserMapper;
import cn.cnic.component.user.service.AdminUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * Introducing logs, note that they are all packaged under "org.slf4j"
     */
    private Logger logger = LoggerUtil.getLogger();


    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private SysRoleMapper roleMapper;

    /**
     * 进行分页
     *
     * @param isAdmin  is admin
     * @param username username
     * @param offset   Number of pages
     * @param limit    Number each page
     * @param param    Search content
     * @return
     */
    @Override
    public String getUserListPage(String username, boolean isAdmin, Integer offset, Integer limit, String param) {
        if (null == offset || null == limit) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr(ReturnMapUtils.ERROR_MSG);
        }
        Page<SysUserVo> page = PageHelper.startPage(offset, limit, "crt_dttm desc");
        userMapper.getUserList(isAdmin, username, param);
        String id = roleMapper.getIdByUserName(username);
        List<SysRole> roleListBySysUserId = roleMapper.getSysRoleListBySysUserId(id);
        for (SysRole sysRole : roleListBySysUserId) {
            SysRoleType role = sysRole.getRole();
            String roleValue = role.getValue();

        }
        Map<String, Object> rtnMap = ReturnMapUtils.setSucceededMsg(ReturnMapUtils.SUCCEEDED_MSG);
        rtnMap = PageHelperUtils.setLayTableParam(page, rtnMap);
        return JsonUtils.toJsonNoException(rtnMap);
    }

    @Override
    public String addUser(String username, UserVo userVo) {
        // Judge whether the 'username' is empty
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("illegal user");
        }

        // Judge whether the 'userVo' is empty
        if (userVo == null) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("param is null");
        }
        SysUser user = new SysUser();
        // Copy userVo to user
        BeanUtils.copyProperties(userVo, user);
        // Initialization base field value does not include ID
        BaseHibernateModelNoId baseHibernateModelNoId = BaseHibernateModelNoIdUtils.newBaseHibernateModelNoId(username);
        //Copy base field value to schedule
        BeanUtils.copyProperties(baseHibernateModelNoId, user);
        //set uuid
        user.setId(UUIDUtils.getUUID32());

        int insert = userMapper.insert(user);

        if (insert <= 0) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("add failed");
        }
        return ReturnMapUtils.setSucceededMsgRtnJsonStr(ReturnMapUtils.SUCCEEDED_MSG);
    }

    @Override
    public String getUserById(boolean isAdmin, String username, String userId) {
       SysUser sysUser = userMapper.getUserById(isAdmin,username,userId);
        List<SysRole> roleListBySysUserId = roleMapper.getSysRoleListBySysUserId(userId);
        for (SysRole sysRole : roleListBySysUserId) {
            SysRoleType role = sysRole.getRole();
            sysUser.setRole(role);
        }
        if (null == sysUser) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("no data");
        }
        return ReturnMapUtils.setSucceededCustomParamRtnJsonStr("sysUserVo", sysUser);
    }

    @Override
    public String update(boolean isAdmin, String username, SysUserVo sysUserVo) {
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("Illegal users");
        }
        if (null == sysUserVo) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("Parameter is empty");
        }
        String id = sysUserVo.getId();
        if (StringUtils.isBlank(id)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("id is empty");
        }
        SysUser sysUserById = userMapper.getUserById(isAdmin, username, id);
        if (null == sysUserById) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("The task for which the current Id does not exist");
        }
        try {
            sysUserById.setName(sysUserVo.getName());
            sysUserById.setUsername(sysUserVo.getUsername());
            sysUserById.setPassword(sysUserVo.getPassword());
            sysUserById.setRole(sysUserVo.getRole());
            sysUserById.setStatus(sysUserVo.getStatus());
            int update = userMapper.update(sysUserById);
            if (update <= 0) {
                return ReturnMapUtils.setFailedMsgRtnJsonStr(ReturnMapUtils.ERROR_MSG);
            }
            return ReturnMapUtils.setSucceededMsgRtnJsonStr(ReturnMapUtils.SUCCEEDED_MSG);
        } catch (Exception e) {
            logger.error("update failed", e);
            return ReturnMapUtils.setFailedMsgRtnJsonStr("update failed");
        }
    }

    @Override
    public String delUser(boolean isAdmin, String username, String sysUserId) {
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("Illegal users");
        }
        if (StringUtils.isBlank(sysUserId)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("id is empty");
        }
        SysUser sysUserById = userMapper.getUserById(isAdmin,username,sysUserId);
        if (null == sysUserById) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("The task for which the current Id does not exist");
        }
        try {

            sysUserById.setEnableFlag(false);
            sysUserById.setLastUpdateDttm(new Date());
            sysUserById.setLastUpdateUser(username);
            int update = userMapper.update(sysUserById);
            if (update <= 0) {
                return ReturnMapUtils.setFailedMsgRtnJsonStr(ReturnMapUtils.ERROR_MSG);
            }
            return ReturnMapUtils.setSucceededMsgRtnJsonStr("Started successfully");
        } catch (Exception e) {
            logger.error("delete failed", e);
            return ReturnMapUtils.setFailedMsgRtnJsonStr("delete failed");
        }
    }
}
