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

package cn.hippo4j.starter.monitor.collect;

import cn.hippo4j.common.model.PoolRunStateInfo;
import cn.hippo4j.common.monitor.AbstractMessage;
import cn.hippo4j.common.monitor.Message;
import cn.hippo4j.common.monitor.MessageTypeEnum;
import cn.hippo4j.common.monitor.RuntimeMessage;
import cn.hippo4j.starter.config.BootstrapProperties;
import cn.hippo4j.core.executor.manage.GlobalThreadPoolManage;
import cn.hippo4j.core.executor.state.AbstractThreadPoolRuntime;
import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.util.List;

import static cn.hippo4j.core.toolkit.IdentifyUtil.getThreadPoolIdentify;

/**
 * Thread pool runtime data collection.
 *
 * @author chen.ma
 * @date 2021/12/16 19:46
 */
@AllArgsConstructor
public class RunTimeInfoCollector extends AbstractThreadPoolRuntime implements Collector {

    private final BootstrapProperties properties;

    @Override
    public Message collectMessage() {
        AbstractMessage message = new RuntimeMessage();
        List<Message> runtimeMessages = Lists.newArrayList();
        List<String> listThreadPoolId = GlobalThreadPoolManage.listThreadPoolId();
        for (String each : listThreadPoolId) {
            PoolRunStateInfo poolRunState = getPoolRunState(each);
            RuntimeMessage runtimeMessage = BeanUtil.toBean(poolRunState, RuntimeMessage.class);
            runtimeMessage.setGroupKey(getThreadPoolIdentify(each, properties.getItemId(), properties.getNamespace()));
            runtimeMessages.add(runtimeMessage);
        }
        message.setMessageType(MessageTypeEnum.RUNTIME);
        message.setMessages(runtimeMessages);
        return message;
    }

    @Override
    protected PoolRunStateInfo supplement(PoolRunStateInfo basePoolRunStateInfo) {
        return basePoolRunStateInfo;
    }
}
