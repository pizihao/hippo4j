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
import cn.hippo4j.common.toolkit.ReflectUtil;
import cn.hippo4j.rpc.discovery.ClassRegistry;
import cn.hippo4j.rpc.discovery.Instance;
import cn.hippo4j.rpc.model.DefaultRequest;
import cn.hippo4j.rpc.model.DefaultResponse;
import cn.hippo4j.rpc.model.Request;
import cn.hippo4j.rpc.model.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * netty adaptation layer
 *
 * @since 1.5.1
 */
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ServerTakeHandler extends AbstractTakeHandler implements ConnectHandler {

    final Instance instance;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof DefaultRequest)) {
            ctx.fireChannelRead(msg);
        }
        Request request = (Request) msg;
        Response response = sendHandler(request);
        ctx.writeAndFlush(response);
    }

    @Override
    public Response sendHandler(Request request) {
        try {
            Class<?> cls = ClassRegistry.get(request.getClassName());
            Class<?>[] parameterTypes = request.getParameterTypes();
            Method method = ReflectUtil.getMethodByName(cls, request.getMethodName(), parameterTypes);
            Assert.notNull(method);
            Object invoke = ReflectUtil.invoke(instance.getInstance(cls), method, request.getParameters());
            return new DefaultResponse(request.getKey(), invoke.getClass(), invoke);
        } catch (Exception e) {
            return new DefaultResponse(request.getKey(), e, e.getMessage());
        }
    }

}
