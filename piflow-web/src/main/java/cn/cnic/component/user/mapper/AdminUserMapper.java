package cn.cnic.component.user.mapper;

import cn.cnic.base.vo.UserVo;
import cn.cnic.common.Eunm.ScheduleState;
import cn.cnic.common.Eunm.SysRoleType;
import cn.cnic.component.schedule.entity.Schedule;
import cn.cnic.component.schedule.mapper.provider.ScheduleMapperProvider;
import cn.cnic.component.schedule.vo.ScheduleVo;
import cn.cnic.component.system.entity.SysSchedule;
import cn.cnic.component.system.entity.SysUser;
import cn.cnic.component.system.mapper.provider.SysScheduleMapperProvider;
import cn.cnic.component.system.vo.SysScheduleVo;
import cn.cnic.component.system.vo.SysUserVo;
import cn.cnic.component.user.mapper.provider.AdminUserMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;

@Mapper

public interface AdminUserMapper {

    @InsertProvider(type = AdminUserMapperProvider.class, method = "insert")
    public int insert(SysUser user);

    @InsertProvider(type = AdminUserMapperProvider.class, method = "update")
    public int update(SysUser user);

    @SelectProvider(type = AdminUserMapperProvider.class, method = "getUserById")
    public SysUser getUserById(boolean isAdmin,String username, @Param("id") String id);

//    /**
//     * getSysScheduleListByStatus
//     *
//     * @param isAdmin
//     * @param status
//     * @return
//     */
//    @SelectProvider(type = AdminUserMapperProvider.class, method = "getUserVoListByStatus")
//    public List<SysUser> getUserListByStatus(@Param("isAdmin") boolean isAdmin, @Param("status") SysRoleType status);

    /**
     * getSysScheduleList
     *
     * @param param
     * @return
     */
    @SelectProvider(type = AdminUserMapperProvider.class, method = "getUserList")
    public List<SysUserVo> getUserList(@Param("isAdmin") boolean isAdmin,@Param("username") String username, @Param("param") String param);


    @DeleteProvider(type = AdminUserMapperProvider.class,method = "delUserById")
    int delUserById(boolean isAdmin, String username, String id);

}
