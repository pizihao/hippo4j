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

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * The request is processed when the request arrives. Unlike the implementation of {@link ConnectHandler},
 * this connection will be closed on the server side
 *
 * @param <T>
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleServerTakeHandler<T> extends SimpleChannelInboundHandler<T> {

    final Consumer<T> consumer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        try {
            consumer.accept(msg);
        } finally {
            ctx.channel().close();
            ctx.close();
        }
    }

    /**
     * Manual disconnection is used here in case the server and client are disconnected due to a sudden exception
     *
     * @param ctx   the context
     * @param cause the throwable
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
        Optional.ofNullable(cause)
                .ifPresent(t -> {
                    if (log.isWarnEnabled()) {
                        log.warn(cause.getMessage());
                    }
                });
    }
}
