package com.yh.system.service.impl;

import java.util.List;

import com.yh.domain.entity.SysUser;
import com.yh.system.mapper.SysUserMapper;
import com.yh.system.service.ISysUserService;
import com.yh.utils.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2021-09-17
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询用户信息
     *
     * @param userId 用户信息ID
     * @return 用户信息
     */
    @Override
    public SysUser selectSysUserById(Long userId)
    {
        return sysUserMapper.selectSysUserById(userId);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    /**
     * 查询用户信息列表
     *
     * @param sysUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<SysUser> selectSysUserList(SysUser sysUser)
    {
        return sysUserMapper.selectSysUserList(sysUser);
    }

    /**
     * 新增用户信息
     *
     * @param sysUser 用户信息
     * @return 结果
     */
    @Override
    public int insertSysUser(SysUser sysUser)
    {
        sysUser.setCreateTime(DateUtils.getNowDate());
        return sysUserMapper.insertSysUser(sysUser);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser 用户信息
     * @return 结果
     */
    @Override
    public int updateSysUser(SysUser sysUser)
    {
        sysUser.setUpdateTime(DateUtils.getNowDate());
        return sysUserMapper.updateSysUser(sysUser);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysUserByIds(Long[] userIds)
    {
        return sysUserMapper.deleteSysUserByIds(userIds);
    }

    /**
     * 删除用户信息信息
     *
     * @param userId 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysUserById(Long userId)
    {
        return sysUserMapper.deleteSysUserById(userId);
    }
}
