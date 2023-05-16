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

package cn.hippo4j.springboot.starter.config;

import cn.hippo4j.adapter.base.ClientThreadPoolAdapterApi;
import cn.hippo4j.common.api.WebThreadPoolApi;
import cn.hippo4j.common.api.WebThreadPoolRunStateApi;
import cn.hippo4j.rpc.discovery.ServerPort;
import cn.hippo4j.rpc.discovery.SpringContextInstance;
import cn.hippo4j.rpc.handler.ServerTakeHandler;
import cn.hippo4j.rpc.server.SimpleServerConnection;
import cn.hippo4j.rpc.server.Server;
import cn.hippo4j.rpc.support.ServerSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
@RequiredArgsConstructor
public class NettyServerConfiguration {

    ServerPort serverPort;

    final BootstrapProperties properties;

    private static final Class<?>[] classes = {
            WebThreadPoolApi.class,
            ClientThreadPoolAdapterApi.class,
            WebThreadPoolRunStateApi.class
    };

    @PostConstruct
    public void nettyServerPort() throws IOException {
        if (Boolean.FALSE.equals(properties.getEnableRpc())) {
            return;
        }
        this.serverPort = new ServerRandomPort();
        ServerTakeHandler handler = new ServerTakeHandler(new SpringContextInstance());
        try (
                SimpleServerConnection connection = new SimpleServerConnection(handler);
                Server server = new ServerSupport(serverPort, connection, classes)) {
            if (log.isInfoEnabled()) {
                log.info("Netty server started, binding to port {}", serverPort.getPort());
            }
            server.bind();
        }
    }

    public int getServerPort() {
        return serverPort == null ? 0 : serverPort.getPort();
    }

    /**
     * Safely create random ports
     */
    static class ServerRandomPort implements ServerPort {

        final int port = getRandomPort();

        @Override
        public int getPort() {
            return port;
        }

        private int getRandomPort() {
            try (ServerSocket socket = new ServerSocket(0)) {
                return socket.getLocalPort();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
