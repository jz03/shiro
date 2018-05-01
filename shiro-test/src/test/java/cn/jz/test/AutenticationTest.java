package cn.jz.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

/**
 * shiro认证和授权
 * Created by 冀州 on 2018/5/1.
 */
public class AutenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUserAccount(){
        simpleAccountRealm.addAccount("jizhou03","123456","admin","user");
    }

    @Test
    public void testAutentication(){
        //1.构建环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jizhou03","123456");
        subject.login(token);
        System.out.println("认证成功：" +subject.isAuthenticated());

        //授权检查
        subject.checkRoles("admin","user");
    }
}
