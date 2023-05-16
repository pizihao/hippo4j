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

package cn.hippo4j.rpc.support;

import cn.hippo4j.common.toolkit.ThreadUtil;
import cn.hippo4j.rpc.client.RandomPort;
import cn.hippo4j.rpc.discovery.DefaultInstance;
import cn.hippo4j.rpc.discovery.Instance;
import cn.hippo4j.rpc.discovery.ServerPort;
import cn.hippo4j.rpc.handler.ClientPoolHandler;
import cn.hippo4j.rpc.handler.ClientTakeHandler;
import cn.hippo4j.rpc.handler.ServerTakeHandler;
import cn.hippo4j.rpc.server.SimpleServerConnection;
import cn.hippo4j.rpc.server.RPCServer;
import cn.hippo4j.rpc.server.ServerConnection;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleConnectPoolTest {

    String host = "127.0.0.1";
    ServerPort port = new TestServerPort();
    int maxCount = 64;
    int timeout = 5000;
    EventLoopGroup group = new NioEventLoopGroup();
    Class<? extends Channel> cls = NioSocketChannel.class;

    @Test
    public void acquire() throws IOException {
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        // Given the delay in starting the server, wait here
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, port.getPort());
        ClientPoolHandler poolHandler = new ClientPoolHandler(new ClientTakeHandler());
        SimpleConnectPool pool = new SimpleConnectPool(address, maxCount, timeout, group, cls, poolHandler);
        Channel acquire = pool.acquire(timeout);
        Assert.assertNotNull(acquire);
        pool.release(acquire);
        rpcServer.close();
    }

    @Test
    public void testAcquire() throws IOException {
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        // Given the delay in starting the server, wait here
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, port.getPort());
        ClientPoolHandler poolHandler = new ClientPoolHandler(new ClientTakeHandler());
        SimpleConnectPool pool = new SimpleConnectPool(address, maxCount, timeout, group, cls, poolHandler);
        Future<Channel> acquire = pool.acquire();
        Assert.assertNotNull(acquire);
        rpcServer.close();
    }

    @Test
    public void close() throws IOException {
        // The mode connection was denied when the server was started on the specified port
        Instance instance = new DefaultInstance();
        ServerTakeHandler handler = new ServerTakeHandler(instance);
        ServerConnection connection = new SimpleServerConnection(handler);
        RPCServer rpcServer = new RPCServer(connection, port);
        rpcServer.bind();
        // Given the delay in starting the server, wait here
        while (!rpcServer.isActive()) {
            ThreadUtil.sleep(100L);
        }
        InetSocketAddress address = InetSocketAddress.createUnresolved(host, port.getPort());
        ClientPoolHandler poolHandler = new ClientPoolHandler(new ClientTakeHandler());
        SimpleConnectPool pool = new SimpleConnectPool(address, maxCount, timeout, group, cls, poolHandler);
        Channel acquire = pool.acquire(timeout);
        Assert.assertNotNull(acquire);
        pool.release(acquire);
        pool.close();
        rpcServer.close();
    }

    static class TestServerPort implements ServerPort {

        int port = RandomPort.getSafeRandomPort();
        @Override
        public int getPort() {
            return port;
        }
    }
}