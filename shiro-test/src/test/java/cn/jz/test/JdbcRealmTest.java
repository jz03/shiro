package cn.jz.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * Created by 冀州 on 2018/5/1.
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/ibase4j");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testAutentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //打开jdbcRealm的权限开关：默认是关
        jdbcRealm.setPermissionsLookupEnabled(true);
        String userSql = "select password_ from sys_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(userSql);

        //1.构建环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jizhou","123123");
        subject.login(token);
        System.out.println("认证成功：" +subject.isAuthenticated());

        subject.checkRole("1");
        subject.checkPermission("read");

    }
}
