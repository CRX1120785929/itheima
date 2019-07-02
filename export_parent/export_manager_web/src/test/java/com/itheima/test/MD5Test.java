package com.itheima.test;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class MD5Test {

    /**
     * MD5哈希
     *    它是在md5的基础上，在进行散列算法。它是apache中shiro提供的
     * 散列算法：
     *    使用一个密钥，对密码进行加密，且打散。
     *
     * md5哈希不仅能够加盐，还能控制撒几把盐
     *
     * @param args
     *
     * 账户：cgx@export.com
     * 加密后的密码：d244cf08243d25b2855665d8f833916f
     *
     */
    public static void main(String[] args) {
        //1.创建对象并加密
        Md5Hash md5Hash = new Md5Hash("123456","cgx@export.com",2);
        //2.输出加密结果
        System.out.println(md5Hash.toString());
    }











    /**
     * md5加密
     *      使用md5把字符串1234加密后的内容：R�M� 6��1>�U�ܛ�
     *      加密后的内容是由任意字符组成的
     *      我们要把任意字符组成的字符串转换成由可见字符组成的字符串
     *      可见字符：
     *          指的是使用键盘可以直接输入的字符
     *      转换的方式：
     *          Base64编码，它是可逆的。可以编码可以解码
     *          涉及的类：
     *              BASE64Encoder   编码类
     *              BASE64Decoder   解码类
     * md5加密：
     *      它是单向的不可逆的
     *      它的运行结果每次都是一样的。所以我们只要有足够的时间和足够大的硬盘就可以把所有结果穷举完。
     * @param args

    public static void main(String[] args)throws Exception {
        //1.创建加密对象
        MessageDigest md5 = MessageDigest.getInstance("md5");
        //2.创建一个密码对象
        String password = "1234";
        //3.对密码加密
        byte[] bytes = md5.digest(password.getBytes());
        //4.对加完密的字节数组进行base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode(bytes);
        //5.输出加密后且编码的内容
        System.out.println(str);
    }*/
}
