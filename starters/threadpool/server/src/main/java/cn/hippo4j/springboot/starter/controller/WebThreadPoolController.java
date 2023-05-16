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

package cn.hippo4j.springboot.starter.controller;

import cn.hippo4j.adapter.web.WebThreadPoolHandlerChoose;
import cn.hippo4j.adapter.web.WebThreadPoolService;
import cn.hippo4j.common.api.WebThreadPoolApi;
import cn.hippo4j.common.model.ThreadPoolBaseInfo;
import cn.hippo4j.common.model.ThreadPoolParameterInfo;
import cn.hippo4j.common.model.ThreadPoolRunStateInfo;
import cn.hippo4j.common.web.base.Result;
import cn.hippo4j.common.web.base.Results;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web thread pool controller.
 *
 * <p> At present, only Tomcat is well supported, and other web containers need to be improved.
 */
@CrossOrigin
@RestController
@AllArgsConstructor
public class WebThreadPoolController implements WebThreadPoolApi {

    private final WebThreadPoolHandlerChoose webThreadPoolServiceChoose;

    @Override
    @GetMapping("/web/base/info")
    public Result<ThreadPoolBaseInfo> getPoolBaseState(@RequestParam(value = "mark") String mark) {
        WebThreadPoolService webThreadPoolService = webThreadPoolServiceChoose.choose();
        if (webThreadPoolService != null && webThreadPoolService.getClass().getSimpleName().contains(mark)) {
            return Results.success(webThreadPoolService.simpleInfo());
        }
        return Results.success(null);
    }

    @Override
    @GetMapping("/web/run/state")
    public Result<ThreadPoolRunStateInfo> getPoolRunState() {
        ThreadPoolRunStateInfo result = webThreadPoolServiceChoose.choose().getWebRunStateInfo();
        return Results.success(result);
    }

    @Override
    @PostMapping("/web/update/pool")
    public Result<Void> updateWebThreadPool(@RequestBody ThreadPoolParameterInfo threadPoolParameterInfo) {
        webThreadPoolServiceChoose.choose().updateWebThreadPool(threadPoolParameterInfo);
        return Results.success();
    }
}
