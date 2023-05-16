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

package cn.hippo4j.rpc.client;

import cn.hippo4j.common.toolkit.ThreadUtil;
import cn.hippo4j.rpc.discovery.ClassRegistry;
import cn.hippo4j.rpc.discovery.DefaultInstance;
import cn.hippo4j.rpc.discovery.Instance;
import cn.hippo4j.rpc.discovery.ServerPort;
import cn.hippo4j.rpc.handler.ClientPoolHandler;
import cn.hippo4j.rpc.handler.ClientTakeHandler;
import cn.hippo4j.rpc.handler.ServerTakeHandler;
import cn.hippo4j.rpc.model.DefaultRequest;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import cn.hippo4j.rpc.server.SimpleServerConnection;
import cn.hippo4j.rpc.server.RPCServer;
import cn.hippo4j.rpc.server.ServerConnection;
import io.netty.channel.pool.ChannelPoolHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCClientTest {

    String host = "localhost";
    ServerPort port = new TestServerPort();
    ServerPort portTest = new TestPortServerPort();

    /**
     * This test case can be overridden under the handler and coder packages
     */
    @Test
    public void connection() throws IOException {
        Class<CallManager> cls = CallManager.class;
        String className = cls.getName();
        ClassRegistry.put(className, cls);
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, portTest);
        rpcServer.bind();
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, portTest.getPort());
        ChannelPoolHandler channelPoolHandler = new ClientPoolHandler(new ClientTakeHandler());
        SimpleClientConnection clientConnection = new SimpleClientConnection(address, channelPoolHandler);
        RPCClient rpcClient = new RPCClient(clientConnection);
        Class<?>[] classes = new Class<?>[2];
        classes[0] = Integer.class;
        classes[1] = Integer.class;
        Object[] objects = new Object[2];
        objects[0] = 1;
        objects[1] = 2;
        Request request = new DefaultRequest("127.0.0.18889", className, "callTest", classes, objects);
        Response response = rpcClient.connect(request);
        Assert.assertEquals(response.getObj(), 3);
        rpcClient.close();
        rpcServer.close();
    }

    @Test
    public void connectionTest() throws IOException {
        Class<CallManager> cls = CallManager.class;
        String className = cls.getName();
        ClassRegistry.put(className, cls);
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, port.getPort());
        ChannelPoolHandler channelPoolHandler = new ClientPoolHandler(new ClientTakeHandler());
        ClientConnection clientConnection = new SimpleClientConnection(address, channelPoolHandler);
        RPCClient rpcClient = new RPCClient(clientConnection);
        Request request = new DefaultRequest("127.0.0.18888", className, "call", null, null);
        for (int i = 0; i < 50; i++) {
            Response response = rpcClient.connect(request);
            Assert.assertEquals(response.getObj(), 1);
        }
        rpcClient.close();
        rpcServer.close();
    }

    @Test(expected = Exception.class)
    public void responseNullExceptionTest() throws IOException {
        Class<CallManager> cls = CallManager.class;
        String className = cls.getName();
        ClassRegistry.put(className, cls);
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, port.getPort());
        ChannelPoolHandler channelPoolHandler = new ClientPoolHandler(new ClientTakeHandler());
        ClientConnection clientConnection = new SimpleClientConnection(address, channelPoolHandler);
        clientConnection.setTimeout(300L);
        try (RPCClient rpcClient = new RPCClient(clientConnection)) {
            Request request = new DefaultRequest("127.0.0.18888", className, "callTestTimeout", null, null);
            Response response = rpcClient.connect(request);
            Assert.assertNotNull(response.getErrMsg());
            Assert.assertNotNull(response.getThrowable());
        } catch (IOException e) {
            // no something
        } finally {
            rpcServer.close();
        }
    }

    static class TestServerPort implements ServerPort {

        int port = RandomPort.getSafeRandomPort();

        @Override
        public int getPort() {
            return port;
        }
    }

    static class TestPortServerPort implements ServerPort {

        int port = RandomPort.getSafeRandomPort();

        @Override
        public int getPort() {
            return port;
        }
    }
}