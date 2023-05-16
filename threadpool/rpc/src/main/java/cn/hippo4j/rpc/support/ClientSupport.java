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

import cn.hippo4j.common.toolkit.IdUtil;
import cn.hippo4j.common.web.exception.IllegalException;
import cn.hippo4j.rpc.client.Client;
import cn.hippo4j.rpc.client.ClientConnection;
import cn.hippo4j.rpc.client.SimpleClientConnection;
import cn.hippo4j.rpc.client.RPCClient;
import cn.hippo4j.rpc.handler.HandlerManager;
import cn.hippo4j.rpc.handler.ClientPoolHandler;
import cn.hippo4j.rpc.handler.ClientTakeHandler;
import cn.hippo4j.rpc.model.KeyRequest;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import io.netty.channel.ChannelHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Different from the management of the server side, in order not to waste resources, we pool the
 * connections of different addresses and turn the client into a one-time resource. If there is no
 * support from the container, the client is a resource that can be recovered after use. This is
 * similar to {@link WeakReference}, but the client needs the user to set the life cycle.<br>
 * <p>
 * Typically, the client is just a front for the direct connection between the client and the server,
 * and for any call to succeed, only the {@link ClientConnection} connection is required. In the
 * presence of a container, it is necessary to keep the client active for a long time, when the
 * client should be a specific resource in the container, following the resource lifecycle specified
 * by the container
 *
 * @see cn.hippo4j.rpc.client.RPCClient
 * @see SimpleClientConnection
 * @see ServerSupport
 * @see ClientFactoryBean
 * @since 1.5.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientSupport {

    /**
     * the cache for client
     */
    private static final Map<InetSocketAddress, Client> CLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * Obtain the client connected to the server through the server address. If the client does not exist, create one
     *
     * @param address        the address
     * @param handlerManager the handlerManager
     * @return Client
     */
    public static Client getClient(InetSocketAddress address, HandlerManager<ChannelHandler> handlerManager) {
        return CLIENT_MAP.computeIfAbsent(address, a -> {
            ClientPoolHandler handler = (handlerManager instanceof ClientPoolHandler)
                    ? (ClientPoolHandler) handlerManager
                    : new ClientPoolHandler();
            if (handler.isEmpty()) {
                handler.addFirst(null, new ClientTakeHandler());
            }
            SimpleClientConnection connection = new SimpleClientConnection(address, handler);
            return new RPCClient(connection);
        });
    }

    /**
     * Obtain the client connected to the server through the server address. If the client does not exist, create one by default
     *
     * @param address the address
     * @return Client
     */
    public static Client getClient(InetSocketAddress address) {
        return getClient(address, new ClientPoolHandler());
    }

    /**
     * calling the server directly through reflection instead of using a proxy can lead to situations where
     * the class or method does not exist. If this occurs, an exception will be thrown.
     *
     * @param address address
     * @return result
     */
    @SuppressWarnings("unchecked")
    public static <R, P> R clientSend(InetSocketAddress address, P param) {
        Client client = getClient(address);
        Request request = new KeyRequest(IdUtil.simpleUUID(), param.getClass(), param);
        Response response = client.connect(request);
        return (R) response.getObj();
    }

    /**
     * calling the server directly through reflection instead of using a proxy can lead to situations where
     * the class or method does not exist. If this occurs, an exception will be thrown.
     *
     * @param address address
     * @return result
     */
    public static <R, P> R clientSend(String address, P param) {
        InetSocketAddress socketAddress = AddressUtil.getInetAddress(address);
        return clientSend(socketAddress, param);
    }

    /**
     * Close a client connected to a server address. The client may have been closed
     *
     * @param address the address
     */
    public static void closeClient(InetSocketAddress address) {
        Client client = CLIENT_MAP.remove(address);
        Optional.ofNullable(client)
                .ifPresent(c -> {
                    try {
                        c.close();
                    } catch (IOException e) {
                        throw new IllegalException(e);
                    }
                });
    }
}
