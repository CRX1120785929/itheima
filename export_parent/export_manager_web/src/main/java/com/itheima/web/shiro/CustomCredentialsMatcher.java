package com.itheima.web.shiro;

import com.itheima.commons.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码比较器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{
    /**
     * 比较密码：
     *     明文密码和密文密码比较
     *     结论：永远不会一样
     *     所以：
     *       要么把密文解密（不可以的，加密都是单向的）
     *       要么把明文加密
     *
     * 此方法要做的事情：
     *     1、取出明文密码
     *     2、取出密文密码
     *     3、把明文密码加密
     *     4、和密文密码比较
     * @param token  令牌中存着明文密码
     * @param info   认证信息中存着密文密码
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.取出明文密码
        UsernamePasswordToken uToken = (UsernamePasswordToken)token;
        String email = uToken.getUsername();
        String password = new String(uToken.getPassword(),0,uToken.getPassword().length);
        //2.取出密文密码
        String dbPassword = (String)info.getCredentials();
        //3.把明文密码加密
        String md5Password = Encrypt.md5(password,email);
        //4.比较两个密码，并返回比较结果。当返回false时，会抛出异常。返回true表示密码校验成功。
        return md5Password.equals(dbPassword);
    }
}
