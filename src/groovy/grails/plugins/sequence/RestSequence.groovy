/*
 * Copyright (c) 2014 Goran Ehrsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */

package grails.plugins.sequence

/**
 * Created by goran on 2014-06-24.
 */
class RestSequence<T extends Number> implements grails.plugins.sequence.Sequence<T> {

    private final SequenceGenerator sequenceGenerator
    private final long tenant
    private final String name
    private final String group

    RestSequence(SequenceGenerator sequenceGenerator, long tenant, String name, String group) {
        this.sequenceGenerator = sequenceGenerator
        this.tenant = tenant
        this.name = name
        this.group = group
    }

    @Override
    T getNumber() {
        throw new UnsupportedOperationException("getNumber() not yet implemented in RestSequence")
    }

    @Override
    T next() {
        sequenceGenerator.nextNumberLong(tenant, name, group)
    }

    @Override
    String nextFormatted() {
        sequenceGenerator.nextNumber(tenant, name, group)
    }
}
