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

import cn.hippo4j.common.toolkit.Assert;
import cn.hippo4j.rpc.exception.ConnectionException;
import cn.hippo4j.rpc.model.DefaultResponse;
import cn.hippo4j.rpc.model.KeyRequest;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.Function;

/**
 * netty adaptation layer about {@link KeyRequest}<br><br>
 * Parse the parameters in the request to execute the corresponding method. <br>
 * This is a relatively flexible processor at present, but there are still great defects. <br>
 * <br>For example:<br>
 * <ul>
 *     <li>this processor cannot recognize generics, if there are generics, it will not work;</li>
 *     <li>if you need to pass multiple parameters, please wrap them in an object;</li>
 *     <li>it does not support multiple parameters, if you do not need parameters, please create When
 *     adding null, and ignore this parameter when executing</li>
 * </ul>
 *
 * @since 1.5.1
 */
@SuppressWarnings("unused")
@ChannelHandler.Sharable
public class ServerKeyHandler<T, K> extends AbstractTakeHandler implements ConnectHandler {

    Class<T> cls;

    Function<T, K> fun;

    public ServerKeyHandler(Class<T> cls, Function<T, K> fun) {
        Assert.notNull(cls);
        Assert.notNull(fun);
        this.cls = cls;
        this.fun = fun;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof KeyRequest)) {
            ctx.fireChannelRead(msg);
        }
        Request request = (Request) msg;
        if (!checkType(request)) {
            ctx.fireChannelRead(msg);
        }
        Response response = sendHandler(request);
        ctx.writeAndFlush(response);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response sendHandler(Request request) {
        try {
            Object o = request.getParameters()[0];
            K k = fun.apply((T) o);
            return new DefaultResponse(request.getKey(), k.getClass(), k);
        } catch (Exception e) {
            return new DefaultResponse(request.getKey(), e, e.getMessage());
        }
    }

    private boolean checkType(Request request) {
        Object[] parameters = request.getParameters();
        if (parameters == null || parameters.length != 1) {
            throw new ConnectionException("the request only allows one parameter.");
        }
        Object o = parameters[0];
        return cls.isAssignableFrom(o.getClass());
    }
}
