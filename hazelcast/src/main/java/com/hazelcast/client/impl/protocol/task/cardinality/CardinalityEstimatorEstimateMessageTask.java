/*
 * Copyright (c) 2008, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.client.impl.protocol.task.cardinality;

import com.hazelcast.cardinality.impl.CardinalityEstimatorService;
import com.hazelcast.cardinality.impl.operations.EstimateOperation;
import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.codec.CardinalityEstimatorEstimateCodec;
import com.hazelcast.client.impl.protocol.task.AbstractPartitionMessageTask;
import com.hazelcast.instance.Node;
import com.hazelcast.nio.Connection;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.security.permission.CardinalityEstimatorPermission;
import com.hazelcast.spi.Operation;

import java.security.Permission;

public class CardinalityEstimatorEstimateMessageTask
        extends AbstractPartitionMessageTask<CardinalityEstimatorEstimateCodec.RequestParameters> {

    public CardinalityEstimatorEstimateMessageTask(ClientMessage clientMessage, Node node, Connection connection) {
        super(clientMessage, node, connection);
    }

    @Override
    protected Operation prepareOperation() {
        return new EstimateOperation(parameters.name);
    }

    @Override
    protected CardinalityEstimatorEstimateCodec.RequestParameters decodeClientMessage(ClientMessage clientMessage) {
        return CardinalityEstimatorEstimateCodec.decodeRequest(clientMessage);
    }

    @Override
    protected ClientMessage encodeResponse(Object response) {
        return CardinalityEstimatorEstimateCodec.encodeResponse((Long) response);
    }

    @Override
    public String getServiceName() {
        return CardinalityEstimatorService.SERVICE_NAME;
    }

    @Override
    public Permission getRequiredPermission() {
        return new CardinalityEstimatorPermission(parameters.name, ActionConstants.ACTION_READ);
    }

    @Override
    public String getDistributedObjectName() {
        return parameters.name;
    }

    @Override
    public String getMethodName() {
        return "estimate";
    }

    @Override
    public Object[] getParameters() {
        return null;
    }
}
