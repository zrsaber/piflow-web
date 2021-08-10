package cn.cnic.controller;

import cn.cnic.base.utils.SessionUserUtil;
import cn.cnic.component.sparkJar.service.ISparkJarService;
import cn.cnic.component.user.service.LogHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/sparkJar")
public class SparkJarCtrl {

    @Resource
    private ISparkJarService sparkJarServiceImpl;

    @Resource
    private LogHelper logHelper;


    /**
     * Query and enter the spark jar list
     *
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @RequestMapping("/sparkJarListPage")
    @ResponseBody
    public String sparkJarListPage(Integer page, Integer limit, String param) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return sparkJarServiceImpl.sparkJarListPage(username, isAdmin, page, limit, param);
    }


    /**
     * Upload spark jar file and save spark jar
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadSparkJarFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadSparkJarFile(@RequestParam("file") MultipartFile file) {
        String username = SessionUserUtil.getCurrentUsername();
        logHelper.logAuthSucceed("uploadSparkJarFile " + file.getName(),username);
        return sparkJarServiceImpl.uploadSparkJarFile(username, file);
    }

    /**
     * Mount spark jar
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/mountSparkJar", method = RequestMethod.POST)
    @ResponseBody
    public String mountSparkJar(HttpServletRequest request, String id) {
        String username = SessionUserUtil.getCurrentUsername();
        Boolean isAdmin = SessionUserUtil.isAdmin();
        return sparkJarServiceImpl.mountSparkJar(username, isAdmin, id);
    }

    /**
     * unmount spark jar
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/unmountSparkJar", method = RequestMethod.POST)
    @ResponseBody
    public String unmountSparkJar(HttpServletRequest request, String id) {
        String username = SessionUserUtil.getCurrentUsername();
        Boolean isAdmin = SessionUserUtil.isAdmin();
        return sparkJarServiceImpl.unmountSparkJar(username, isAdmin, id);
    }

    /**
     * del spark jar
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delSparkJar", method = RequestMethod.POST)
    @ResponseBody
    public String delSparkJar(HttpServletRequest request, String id) {
        String username = SessionUserUtil.getCurrentUsername();
        Boolean isAdmin = SessionUserUtil.isAdmin();
        logHelper.logAuthSucceed("del SparkJar " + id,username);
        return sparkJarServiceImpl.delSparkJar(username, isAdmin, id);
    }
}
