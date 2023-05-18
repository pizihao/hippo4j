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

package cn.hippo4j.rpc.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DefaultRequestTest {

    @Test
    public void testReadObject() throws IOException, ClassNotFoundException, NoSuchMethodException {
        String key = "name";
        String rid = "rid";
        Object[] parameters = new Object[1];
        parameters[0] = "hippo4j";
        Request request = new DefaultRequest(rid, key, parameters);
        byte[] bytes;
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            outputStream.writeObject(request);
            outputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        }
        Request request1;
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            request1 = (Request) objectInputStream.readObject();
        }
        Assert.assertEquals(request1.hashCode(), request1.hashCode());
        Assert.assertEquals(key, request1.getKey());
        Assert.assertEquals(rid, request1.getRID());
        Assert.assertArrayEquals(parameters, request1.getParameters());
        Assert.assertEquals(request1, request);
    }

    @Test
    public void testEquals() throws NoSuchMethodException {
        String rid = "rid";
        String key = "name";
        Request request = new DefaultRequest(rid, key);
        Assert.assertTrue(request.equals(request));
        Assert.assertFalse(request.equals(null));
    }

}