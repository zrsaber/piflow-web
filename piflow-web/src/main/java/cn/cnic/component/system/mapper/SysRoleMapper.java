package cn.cnic.component.system.mapper;

import cn.cnic.component.system.mapper.provider.SysRoleMapperProvider;
import cn.cnic.component.system.entity.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    @Select("select Max(id) from sys_role")
    public long getMaxId();

    @Select("select b.id from sys_role a,sys_user b where a.fk_sys_user_id=b.id and b.username=#{username}")
    public String getIdByUserName(String username);

    /**
     * getSysRoleListBySysUserId
     *
     * @param sysUserId
     * @return
     */
    @SelectProvider(type = SysRoleMapperProvider.class, method = "getSysRoleListBySysUserId")
    public List<SysRole> getSysRoleListBySysUserId(@Param("sysUserId") String sysUserId);

    @InsertProvider(type = SysRoleMapperProvider.class, method = "insertSysRoleList")
    public int insertSysRoleList(@Param("userId") String userId, @Param("roles") List<SysRole> roles);

}
