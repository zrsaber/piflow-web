package cn.cnic.controller;

import javax.servlet.http.HttpServletRequest;

import cn.cnic.component.user.service.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.component.process.service.IProcessAndProcessGroupService;
import cn.cnic.component.process.service.IProcessGroupService;
import cn.cnic.component.process.service.IProcessService;


@Controller
@RequestMapping("/processAndProcessGroup")
public class ProcessAndProcessGroupCtrl {

    @Autowired
    private IProcessService processServiceImpl;

    @Autowired
    private IProcessGroupService processGroupServiceImpl;

    @Autowired
    private IProcessAndProcessGroupService processAndProcessGroupServiceImpl;

    @Autowired
    private LogHelper logHelper;


    /**
     * Query and enter the process list
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping("/processAndProcessGroupListPage")
    @ResponseBody
    public String processAndProcessGroupListPage(Integer page, Integer limit, String param) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return processAndProcessGroupServiceImpl.getProcessAndProcessGroupListPage(username, isAdmin, page, limit, param);
    }

    /**
     * Start Process Or ProcessGroup
     *
     * @param request
     * @return
     */
    @RequestMapping("/runProcessOrProcessGroup")
    @ResponseBody
    public String runProcessOrProcessGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        String runMode = request.getParameter("runMode");
        String processType = request.getParameter("processType");
        String checkpoint = request.getParameter("checkpointStr");
        String username = SessionUserUtil.getCurrentUsername();
        if ("PROCESS".equals(processType)) {
            logHelper.logAuthSucceed("startProcess " + runMode,username);
            return processServiceImpl.startProcess(username, id, checkpoint, runMode);
        } else {
            logHelper.logAuthSucceed("startProcessGroup " + runMode,username);
            return processGroupServiceImpl.startProcessGroup(username, id, checkpoint, runMode);
        }
    }

    /**
     * Stop Process Group
     *
     * @param request
     * @return
     */
    @RequestMapping("/stopProcessOrProcessGroup")
    @ResponseBody
    public String stopProcessOrProcessGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        String processType = request.getParameter("processType");
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        if ("PROCESS".equals(processType)) {
            logHelper.logAuthSucceed("stopProcess " + id,username);
            return processServiceImpl.stopProcess(username, isAdmin, id);
        } else {
            logHelper.logAuthSucceed("stopProcessGroup " + id,username);
            return processGroupServiceImpl.stopProcessGroup(SessionUserUtil.getCurrentUsername(), SessionUserUtil.isAdmin(), id);
        }
    }

    /**
     * Delete Process
     *
     * @param request
     * @return
     */
    @RequestMapping("/delProcessOrProcessGroup")
    @ResponseBody
    public String delProcessGroup(HttpServletRequest request) {
        String id = request.getParameter("id");
        String processType = request.getParameter("processType");
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        if ("PROCESS".equals(processType)) {
            logHelper.logAuthSucceed("delProcess"+ processType, username);
            return processServiceImpl.delProcess(username, id);
        } else {
            logHelper.logAuthSucceed("delProcessGroup" + processType,username);
            return processGroupServiceImpl.delProcessGroup(username, isAdmin, id);
        }
    }

    @RequestMapping("/getAppInfoList")
    @ResponseBody
    public String getAppInfoList(HttpServletRequest request) {
        String[] taskAppIds = request.getParameterValues("taskAppIds");
        String[] groupAppIds = request.getParameterValues("groupAppIds");
        return processAndProcessGroupServiceImpl.getAppInfoList(taskAppIds, groupAppIds);
    }

}
