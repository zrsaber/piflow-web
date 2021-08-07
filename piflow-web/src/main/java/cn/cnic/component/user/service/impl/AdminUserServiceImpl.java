package cn.cnic.component.user.service.impl;

import cn.cnic.base.BaseHibernateModelNoId;
import cn.cnic.base.BaseHibernateModelNoIdUtils;
import cn.cnic.base.utils.*;
import cn.cnic.base.vo.UserVo;
import cn.cnic.component.schedule.entity.Schedule;
import cn.cnic.component.schedule.vo.ScheduleVo;
import cn.cnic.component.system.entity.SysSchedule;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.system.mapper.SysScheduleMapper;
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
import java.util.Map;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * Introducing logs, note that they are all packaged under "org.slf4j"
     */
    private Logger logger = LoggerUtil.getLogger();


    @Resource
    private AdminUserMapper userMapper;

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
    public String getUserById(boolean isAdmin, String username, String id) {
        // Judge whether the 'username' is empty
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("illegal user");
        }
        // Judge whether the 'id' is empty
        if (StringUtils.isBlank(id)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("id is null");
        }
        // search
        SysUser userVoById = userMapper.getUserById(isAdmin, username, id);
        // Judge whether the query result is empty
        if (null == userVoById) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("data is null");
        }
        return ReturnMapUtils.setSucceededCustomParamRtnJsonStr("userVo", userVoById);
    }

    @Override
    public String updateUser(boolean isAdmin, String username, SysUser user) {
        // Judge whether the 'username' is empty
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("illegal user");
        }
        // Judge whether the 'userVo' is empty
        if (null == user) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("param is null");
        }
        // Judge whether the 'userVo Id' is empty
        if (StringUtils.isBlank(user.getId())) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("id is null");
        }
        // query
        SysUser userVoById = userMapper.getUserById(isAdmin, username, user.getId());
        // Judge whether the query result is empty
        if (null == userVoById) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("No data with ID " + user.getId());
        }
        // Copy scheduleVo data to userById
        BeanUtils.copyProperties(user, userVoById);
//        //set Operator information
//        userVoById.setLastUpdateDttm(new Date());
//        scheduleById.setLastUpdateUser(username);
        int update = userMapper.update(userVoById);
        if (update <= 0) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("update failed");
        }
        return ReturnMapUtils.setSucceededMsgRtnJsonStr(ReturnMapUtils.SUCCEEDED_MSG);
    }

    @Override
    public String delUser(boolean isAdmin, String username, String id) {
        // Judge whether the 'username' is empty
        if (StringUtils.isBlank(username)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("illegal user");
        }
        // Judge whether the 'id' is empty
        if (StringUtils.isBlank(id)) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("id is null");
        }
        // query
        SysUser userVoById = userMapper.getUserById(isAdmin, username, id);
        // Judge whether the query result is empty
        if (null == userVoById) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("Data does not exist");
        }
        // delete
        int delUserById = userMapper.delUserById(isAdmin, username, id);
        // Judge whether it is successful or not
        if (delUserById <= 0) {
            return ReturnMapUtils.setFailedMsgRtnJsonStr("del failed");
        }
        return ReturnMapUtils.setSucceededMsgRtnJsonStr(ReturnMapUtils.SUCCEEDED_MSG);
    }
}
