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

package com.hazelcast.security.permission;

/**
 * To be able to map-reduce from a client in a secure environment
 */
public class MapReducePermission extends InstancePermission {

    private static final int ALL = CREATE | DESTROY;

    public MapReducePermission(String name, String... actions) {
        super(name, actions);
    }

    @Override
    protected int initMask(String[] actions) {
        int mask = NONE;
        for (String action : actions) {
            if (ActionConstants.ACTION_ALL.equals(action)) {
                return ALL;
            }

            if (ActionConstants.ACTION_CREATE.equals(action)) {
                mask |= CREATE;
            } else if (ActionConstants.ACTION_DESTROY.equals(action)) {
                mask |= DESTROY;
            }
        }
        return mask;
    }
}
