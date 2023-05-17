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

import cn.hippo4j.common.web.exception.IllegalException;
import cn.hippo4j.rpc.client.Client;
import cn.hippo4j.rpc.handler.ClientPoolHandler;
import cn.hippo4j.rpc.model.DefaultRequest;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Add a proxy for the request, {@link Proxy} and {@link InvocationHandler}
 *
 * @since 2.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientContext {

    // cache
    static Map<String, Object> map = new ConcurrentHashMap<>();

    /**
     * A proxy object for PRC is obtained through an interface
     *
     * @param cls     The interface type
     * @param address address
     * @param <T>     Object type
     * @param handler the pool handler  for netty
     * @return Proxy objects
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObj(Class<T> cls, InetSocketAddress address, ClientPoolHandler handler) {
        Client client = ClientSupport.getClient(address, handler);
        String s = address + cls.getName();
        Object o = map.get(s);
        if (o != null) {
            return (T) o;
        }
        return createProxy(client, cls, address);
    }

    /**
     * A proxy object for PRC is obtained through an interface
     *
     * @param cls     The interface type
     * @param address address String
     * @param <T>     Object type
     * @return Proxy objects
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObj(Class<T> cls, String address) {
        InetSocketAddress socketAddress = AddressUtil.getInetAddress(address);
        String s = socketAddress + cls.getName();
        Object o = map.get(s);
        if (o != null) {
            return (T) o;
        }
        Client client = ClientSupport.getClient(socketAddress);
        return createProxy(client, cls, socketAddress);
    }

    /**
     * remove proxy object
     *
     * @param cls     the class
     * @param address address String
     */
    public static void removeObj(Class<?> cls, String address) {
        InetSocketAddress socketAddress = AddressUtil.getInetAddress(address);
        String s = socketAddress + cls.getName();
        ClientSupport.closeClient(socketAddress);
        map.remove(s);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Client client, Class<T> cls, InetSocketAddress address) {
        boolean b = cls.isInterface();
        if (!b) {
            throw new IllegalException(cls.getName() + "is not a Interface");
        }
        String s = address.toString() + cls.getName();
        Object o = map.get(s);
        if (o != null) {
            return (T) o;
        }
        T obj = (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                (proxy, method, args) -> {
                    String clsName = cls.getName();
                    String methodName = method.getName();
                    String key = address + clsName + methodName;
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Request request = new DefaultRequest(key, clsName, methodName, parameterTypes, args);
                    Response response = client.connect(request);
                    if (response == null) {
                        return null;
                    }
                    if (response.isErr()) {
                        throw new IllegalException(response.getErrMsg(), response.getThrowable());
                    }
                    return response.getObj();
                });
        map.put(s, obj);
        return obj;
    }

}
