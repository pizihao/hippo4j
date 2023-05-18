package cn.hippo4j.rpc.support;

import cn.hippo4j.rpc.exception.ConnectionException;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;

public class AddressUtilTest {

    String address1 = "http://hippo4j.cn/login:8080";
    String address2 = "https://hippo4j.cn/login:8080";
    String address3 = "https://hippo4j.cn/login";
    String addressHostName = "hippo4j.cn/login";
    int addressPort = 8080;

    @Test
    public void test(){
        InetSocketAddress address = AddressUtil.getInetAddress(address1);
        Assert.assertEquals(addressHostName,address.getHostName());
        Assert.assertEquals(addressPort,address.getPort());
    }

    @Test
    public void testAddress2(){
        InetSocketAddress address = AddressUtil.getInetAddress(address2);
        Assert.assertEquals(addressHostName,address.getHostName());
        Assert.assertEquals(addressPort,address.getPort());
    }

    @Test(expected = ConnectionException.class)
    public void testAddress3(){
        AddressUtil.getInetAddress(address3);
    }
}
