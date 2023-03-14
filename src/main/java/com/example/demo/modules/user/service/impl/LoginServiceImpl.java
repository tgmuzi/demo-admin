package com.example.demo.modules.user.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.modules.oAuth.OAuth2Token;
import com.example.demo.modules.user.dao.SysUserMapper;
import com.example.demo.modules.user.dao.SysUserTokenMapper;
import com.example.demo.modules.user.entity.SysUser;
import com.example.demo.modules.user.entity.SysUserToken;
import com.example.demo.modules.user.service.LoginService;
import com.example.demo.utils.AjaxObject;
import com.example.demo.utils.TokenGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements LoginService {

    // 12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByName(String getMapByName) {
        return sysUserMapper.queryByUserId(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userId 用户名
     * @return User
     */
    @Override
    public SysUser getMapById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public AjaxObject createToken(HttpServletRequest request, HttpServletResponse response, SysUser user) {
        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("==" + formatter.format(now));
        System.out.println("==" + expireTime);
        // 判断是否生成过token
        SysUserToken tokenEntity = queryByUserId(user.getUserId());
        if (tokenEntity == null) {
            tokenEntity = new SysUserToken();
            tokenEntity.setUserId(user.getUserId());
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 保存token
            save1(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 更新token
            update1(tokenEntity);
        }
        // 用户认证信息
        Subject subject = SecurityUtils.getSubject();
        try {
            // 进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(new OAuth2Token(token));
            // subject.checkRole("admin");
            // subject.checkPermissions("query", "add");
            subject.getSession().setTimeout(3600 * 24L * 1000);
        } catch (UnknownAccountException e) {
            return AjaxObject.error("用户名不存在！");
        } catch (AuthenticationException e) {
            return AjaxObject.error("账号或密码错误！");
        } catch (AuthorizationException e) {
            return AjaxObject.error("没有权限");
        }
        // 查询用户角色
        return AjaxObject.ok("登录成功").data(user);
    }

    @Override
    public void save1(SysUserToken token) {
        sysUserTokenMapper.insert(token);
    }

    @Override
    public void update1(SysUserToken token) {
        sysUserTokenMapper.updateById(token);
    }

    @Override
    public SysUserToken queryByUserId(Long userId) {
        return sysUserTokenMapper.queryByUserId(userId);
    }
}
