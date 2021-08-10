package cn.cnic.controller;

import cn.cnic.component.user.service.LogHelper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.component.flow.entity.Flow;
import cn.cnic.component.flow.service.IFlowService;
import cn.cnic.component.flow.vo.FlowVo;

@Controller
@RequestMapping("/flow")
public class FlowCtrl {

    @Autowired
    private IFlowService flowServiceImpl;

    @Autowired
    private LogHelper logHelper;

    /**
     * flowList page query
     *
     * @param page 
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping("/getFlowListPage")
    @ResponseBody
    public String getFlowListPage(Integer page, Integer limit, String param) {
        boolean isAdmin = SessionUserUtil.isAdmin();
        String username = SessionUserUtil.getCurrentUsername();
        return flowServiceImpl.getFlowListPage(username, isAdmin, page, limit, param);
    }

    /**
     * Enter the front page of the drawing board
     *
     * @return
     */
    @RequestMapping("/drawingBoardData")
    @ResponseBody
    public String drawingBoardData(String load, String parentAccessPath) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return flowServiceImpl.drawingBoardData(username, isAdmin, load, parentAccessPath);
    }

    /**
     * run Flow
     *
     * @return
     */
    @RequestMapping("/runFlow")
    @ResponseBody
    public String runFlow(String flowId, String runMode) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("run flow",username);
        return flowServiceImpl.runFlow(username, isAdmin, flowId, runMode);
    }

    @RequestMapping("/queryFlowData")
    @ResponseBody
    public String queryFlowData(String load) {
        return flowServiceImpl.getFlowVoById(load);
    }

    /**
     * save flow
     *
     * @param flowVo
     * @return
     */
    @RequestMapping("/saveFlowInfo")
    @ResponseBody
    public String saveFlowInfo(FlowVo flowVo) {
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("save flow",username);
        return flowServiceImpl.addFlow(username, flowVo);
    }

    /**
     * update Flow
     *
     * @param flow
     * @return
     */
    @RequestMapping("/updateFlowInfo")
    @ResponseBody
    public String updateFlowInfo(Flow flow) {
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("update flow",username);
        return flowServiceImpl.updateFlow(username, flow);
    }

    /**
     * Delete flow association information according to flowId
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteFlow")
    @ResponseBody
    public String deleteFlow(String id) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("delete flow " + id,username);
        return flowServiceImpl.deleteFLowInfo(username, isAdmin, id);
    }

    @RequestMapping("/updateFlowBaseInfo")
    @ResponseBody
    public String updateFlowBaseInfo(String fId, FlowVo flowVo) {
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("update flow base "+ flowVo.getName(),username);
        return flowServiceImpl.updateFlowBaseInfo(username, fId, flowVo);
    }

}
