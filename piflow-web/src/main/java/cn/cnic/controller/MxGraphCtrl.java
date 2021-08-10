package cn.cnic.controller;

import cn.cnic.base.utils.JsonUtils;
import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.component.flow.service.IFlowGroupService;
import cn.cnic.component.mxGraph.service.IMxGraphModelService;
import cn.cnic.component.mxGraph.service.IMxNodeImageService;
import cn.cnic.component.mxGraph.vo.MxGraphVo;
import cn.cnic.component.user.service.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * grapheditorctrl
 */
@Controller
@RequestMapping("/mxGraph")
public class MxGraphCtrl {

    @Autowired
    private IFlowGroupService flowGroupServiceImpl;

    @Autowired
    private IMxGraphModelService mxGraphModelServiceImpl;

    @Autowired
    private IMxNodeImageService mxNodeImageServiceImpl;

    @Autowired
    private LogHelper logHelper;

    /**
     * save data
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/saveDataForTask")
    @ResponseBody
    public String saveDataForTask(HttpServletRequest request, Model model) {
        String imageXML = request.getParameter("imageXML");
        String loadId = request.getParameter("load");
        String operType = request.getParameter("operType");
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("saveDataForTask" + loadId,username);
        return mxGraphModelServiceImpl.saveDataForTask(username, imageXML, loadId, operType);
    }

    /**
     * save data
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/saveDataForGroup")
    @ResponseBody
    public String saveDataForGroup(HttpServletRequest request, Model model) {
        String imageXML = request.getParameter("imageXML");
        String loadId = request.getParameter("load");
        String operType = request.getParameter("operType");
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("saveDataForGroup " + loadId, username);
        return mxGraphModelServiceImpl.saveDataForGroup(username, imageXML, loadId, operType, true);
    }

    @RequestMapping("/addMxCellAndData")
    @ResponseBody
    public String addMxCellAndData(@RequestBody MxGraphVo mxGraphVo) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("addMxCellAndData" + mxGraphVo.getLoadId(), currentUsername);
        return mxGraphModelServiceImpl.addMxCellAndData(mxGraphVo, currentUsername);
    }

    @RequestMapping("/uploadNodeImage")
    @ResponseBody
    public String uploadNodeImage(@RequestParam("file") MultipartFile file, String imageType) {
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("uploadNodeImage " + file.getName(),username);
        return mxNodeImageServiceImpl.uploadNodeImage(username, file, imageType);
    }

    @RequestMapping("/nodeImageList")
    @ResponseBody
    public String nodeImageList(String imageType) {
        String username = SessionUserUtil.getCurrentUsername();
        return mxNodeImageServiceImpl.getMxNodeImageList(username, imageType);
    }

    @RequestMapping("/groupRightRun")
    @ResponseBody
    public String groupRightRun(String pId, String nodeId, String nodeType) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("groupRightRun" + nodeId, username);
        return flowGroupServiceImpl.rightRun(username, isAdmin, pId, nodeId, nodeType);
    }

    @RequestMapping("/eraseRecord")
    @ResponseBody
    public String eraseRecord() {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("code", 200);
        rtnMap.put("flag", true);
        return JsonUtils.toJsonNoException(rtnMap);
    }

}
