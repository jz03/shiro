package cn.jz.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by 冀州 on 2018/5/1.
 */
public class IniRealmTest {

    @Test
    public void testAutentication(){
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jizhou03","123456");
        subject.login(token);
        System.out.println("认证成功：" +subject.isAuthenticated());
        //角色检查
        subject.checkRoles("admin");
        //权限检查
        subject.checkPermission("user:update");

    }
}
