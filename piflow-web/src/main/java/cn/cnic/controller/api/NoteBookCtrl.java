package cn.cnic.controller.api;

import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.component.livy.service.INoteBookService;
import cn.cnic.component.user.service.LogHelper;
import cn.cnic.controller.requestVo.NoteBookVoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "noteBoot api")
@RestController
@RequestMapping(value = "/noteBoot")
public class NoteBookCtrl {

    @Resource
    private INoteBookService noteBookServiceImpl;

    @Resource
    private LogHelper logHelper;

    /**
     * saveOrUpdateNoteBook
     *
     * @param noteBookVo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveOrUpdateNoteBook", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrUpdateNoteBook(@ApiParam(value = "noteBookVo", required = true) NoteBookVoRequest noteBookVo) throws Exception {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("saveOrUpdateNoteBook " + noteBookVo.getName(),currentUsername);
        return noteBookServiceImpl.saveOrUpdateNoteBook(currentUsername, isAdmin, noteBookVo, false);
    }

    /**
     * checkNoteBookName
     *
     * @param noteBookName
     * @return
     */
    @RequestMapping(value = "/checkNoteBookName", method = RequestMethod.POST)
    @ResponseBody
    public String checkNoteBookName(@ApiParam(value = "noteBookName", required = true)String noteBookName) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return noteBookServiceImpl.checkNoteBookName(currentUsername, isAdmin, noteBookName);
    }

    /**
     * deleteNoteBook
     *
     * @param noteBookId
     * @return
     */
    @RequestMapping(value = "/deleteNoteBook", method = RequestMethod.POST)
    @ResponseBody
    public String deleteNoteBook(@ApiParam(value = "noteBookId", required = true)String noteBookId) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("deleteNoteBook " + noteBookId,currentUsername);
        return noteBookServiceImpl.deleteNoteBook(currentUsername, isAdmin, noteBookId);
    }

    /**
     * "noteBook" list Pagination
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping(value = "/noteBookListPage", method = RequestMethod.POST)
    @ResponseBody
    public String noteBookListPage(@ApiParam(value = "page", required = true)Integer page,
                                   @ApiParam(value = "limit", required = true)Integer limit,
                                   @ApiParam(value = "param", required = false)String param) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return noteBookServiceImpl.getNoteBookListPage(currentUsername, isAdmin, page, limit, param);
    }

    /**
     * startNoteBookSession
     *
     * @param noteBookId
     * @return
     */
    @RequestMapping(value = "/startNoteBookSession", method = RequestMethod.POST)
    @ResponseBody
    public String startNoteBookSession(String noteBookId) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("startNoteBookSession " + noteBookId,currentUsername);
        return noteBookServiceImpl.startNoteBookSession(currentUsername, isAdmin, noteBookId);
    }

    /**
     * deleteNoteBookSession
     *
     * @param noteBookId
     * @return
     */
    @RequestMapping(value = "/deleteNoteBookSession", method = RequestMethod.POST)
    @ResponseBody
    public String deleteNoteBookSession(String noteBookId) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("deleteNoteBookSession " + noteBookId,currentUsername);
        return noteBookServiceImpl.delNoteBookSession(currentUsername, isAdmin, noteBookId);
    }

}
