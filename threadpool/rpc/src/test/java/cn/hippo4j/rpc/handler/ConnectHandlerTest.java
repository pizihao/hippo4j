/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hippo4j.rpc.handler;

import cn.hippo4j.common.toolkit.ThreadUtil;
import cn.hippo4j.rpc.client.SimpleClientConnection;
import cn.hippo4j.rpc.client.RPCClient;
import cn.hippo4j.rpc.client.RandomPort;
import cn.hippo4j.rpc.discovery.ClassRegistry;
import cn.hippo4j.rpc.discovery.DefaultInstance;
import cn.hippo4j.rpc.discovery.Instance;
import cn.hippo4j.rpc.discovery.InstanceServerLoader;
import cn.hippo4j.rpc.discovery.ServerPort;
import cn.hippo4j.rpc.model.DefaultRequest;
import cn.hippo4j.rpc.model.DefaultResponse;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import cn.hippo4j.rpc.server.SimpleServerConnection;
import cn.hippo4j.rpc.server.RPCServer;
import cn.hippo4j.rpc.support.ClientContext;
import io.netty.channel.pool.ChannelPoolHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ConnectHandlerTest {

    @Test
    public void handlerTest() throws IOException {
        // server
        Class<InstanceServerLoader> cls = InstanceServerLoader.class;
        ClassRegistry.put(cls.getName(), cls);
        ServerPort port = new TestServerPort();
        Instance instance = new DefaultInstance();
        ServerTakeHandler serverHandler = new ServerTakeHandler(instance);
        SimpleServerConnection connection = new SimpleServerConnection(serverHandler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", port.getPort());
        ChannelPoolHandler channelPoolHandler = new ClientPoolHandler(new ClientTakeHandler());
        SimpleClientConnection clientConnection = new SimpleClientConnection(address, channelPoolHandler);
        RPCClient rpcClient = new RPCClient(clientConnection);

        InstanceServerLoader loader = ClientContext.createProxy(rpcClient, cls, address);
        String name = loader.getName();
        Assert.assertEquals("name", name);
        rpcClient.close();
        rpcServer.close();
    }

    @Test
    public void testConnectHandlerDefault() {
        ConnectHandler handler = new TestConnectHandler();

        Request request = new DefaultRequest("key", "className", "methodName", new Class[0], new Object[0]);
        Response response = handler.sendHandler(request);
        Assert.assertNull(response);
        Response response1 = new DefaultResponse("key", this.getClass(), handler);
        String key = response1.getKey();
        Class<?> cls = response1.getCls();
        Object obj = response1.getObj();
        handler.handler(response1);
        Assert.assertEquals(key, response1.getKey());
        Assert.assertEquals(cls, response1.getCls());
        Assert.assertEquals(obj, response1.getObj());

    }

    static class TestConnectHandler implements ConnectHandler {

    }

    static class TestServerPort implements ServerPort {

        int port = RandomPort.getSafeRandomPort();

        @Override
        public int getPort() {
            return port;
        }
    }

}