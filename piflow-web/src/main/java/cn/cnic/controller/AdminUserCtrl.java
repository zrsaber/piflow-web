package cn.cnic.controller;

import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.base.vo.UserVo;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.user.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class AdminUserCtrl {

    @Autowired
    AdminUserService adminUserServiceImpl;

    /**
     * userList page query
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping("/getUserListPage")
    @ResponseBody
    public String getUserListPage(Integer page, Integer limit, String param) {
        boolean isAdmin = SessionUserUtil.isAdmin();
        String username = SessionUserUtil.getCurrentUsername();
        return adminUserServiceImpl.getUserListPage(username, isAdmin, page, limit, param);
    }

    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(SysUser user) {
        boolean isAdmin = SessionUserUtil.isAdmin();
        String username = SessionUserUtil.getCurrentUsername();
        return  adminUserServiceImpl.updateUser(isAdmin,username,user);
    }

    @RequestMapping("/delUserInfo")
    @ResponseBody
    public String delUserInfo(String id) {
        boolean isAdmin = SessionUserUtil.isAdmin();
        String username = SessionUserUtil.getCurrentUsername();
        return  adminUserServiceImpl.delUser(isAdmin,username,id);
    }

    @RequestMapping("/addUserInfo")
    @ResponseBody
    public String addUserInfo(UserVo userVo ) {
        String username = SessionUserUtil.getCurrentUsername();
        return  adminUserServiceImpl.addUser(username,userVo);
    }




}
