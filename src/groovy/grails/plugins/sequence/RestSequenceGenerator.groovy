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

import groovy.transform.CompileStatic
import groovyx.net.http.RESTClient
import org.codehaus.groovy.grails.commons.GrailsApplication

import static groovyx.net.http.ContentType.*

/**
 * Created by goran on 2014-06-24.
 */
class RestSequenceGenerator<T extends Number> implements SequenceGenerator<T> {

    GrailsApplication grailsApplication

    /**
     * Create a new sequence.
     *
     * @param tenant tenant ID
     * @param name sequence name
     * @param group sub-sequence
     * @param format number format
     * @param start start number
     * @return current sequence status
     */
    @Override
    SequenceStatus createSequence(long tenant, String name, String group, String format, T start) {
        def client = new RESTClient("http://localhost:8082")
        client.auth.basic('admin', 'password')
        def resp = client.post(path: "/api/sequence/${tenant}/${name}",
                contentType: JSON,
                body: [format: '%04d', number: start])
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        def data = resp.data
        return new SequenceStatus(data.name, data.format, start)
    }

    /**
     * Delete a sequence.
     *
     * @param tenant tenant ID
     * @param name sequence name
     * @param group sub-sequence
     * @return true if sequence was removed
     */
    @Override
    boolean deleteSequence(long tenant, String name, String group) {
        def client = new RESTClient("http://localhost:8082")
        client.auth.basic('admin', 'password')
        def path = "/api/sequence/${tenant}/${name}"
        if (group) {
            path += "/${group}"
        }
        def resp = client.delete(path: path,
                contentType: JSON)
        return (resp.status == 200)
    }

    /**
     * Get next unique number formatted.
     *
     * @param tenant tenant ID
     * @param name sequence name
     * @param group sub-sequence
     * @return formatted number
     */
    @Override
    String nextNumber(long tenant, String name, String group) {
        def client = new RESTClient("http://localhost:8082/api/sequence")
        client.auth.basic('user', 'password')
        def resp = client.get(path: "/api/sequence/${tenant}/${name}/next", contentType: JSON)
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        resp.data.number
    }

    /**
     * Get next unique (raw) number.
     *
     * @param tenant tenant ID
     * @param name sequence name
     * @param group sub-sequence
     * @return number as a long
     */
    @Override
    T nextNumberLong(long tenant, String name, String group) {
        def client = new RESTClient("http://localhost:8082/api/sequence")
        client.auth.basic('user', 'password')
        def resp = client.get(path: "/api/sequence/${tenant}/${name}/next", contentType: JSON)
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        return Long.valueOf(resp.data.number)
    }

    /**
     * Update sequence.
     *
     * @param tenant tenant ID
     * @param name sequence name
     * @param group sub-sequence
     * @param format number format
     * @param current current number
     * @param start new number
     * @return sequence status if sequence was updated, null otherwise
     */
    @Override
    SequenceStatus update(long tenant, String name, String group, String format, T current, T start) {
        def client = new RESTClient("http://localhost:8082/api/sequence")
        client.auth.basic('admin', 'password')
        def resp = client.put(path: "/api/sequence/${tenant}/${name}",
                contentType: JSON,
                body: [format: format, current: current, number: start])
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        return new SequenceStatus(name, format, start)
    }

    @Override
    SequenceStatus status(long tenant, String name, String group) {
        def client = new RESTClient("http://localhost:8082/api/sequence")
        client.auth.basic('user', 'password')
        def resp = client.get(path: "/api/sequence/${tenant}/${name}", contentType: JSON)
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        def data = resp.data
        println data
        new SequenceStatus(data.name, data.format, data.number)
    }

    /**
     * Get sequence statistics.
     *
     * @param tenant tenant ID
     * @return statistics for all sequences in the tenant
     */
    @Override
    Iterable<SequenceStatus> getStatistics(long tenant) {
        def client = new RESTClient("http://localhost:8082/api/sequence")
        client.auth.basic('admin', 'password')
        def resp = client.get(path: "/api/sequence/${tenant}", contentType: JSON)
        if (resp.status != 200) {
            throw new RuntimeException(resp.toString())
        }
        return null
    }

    @Override
    void shutdown() {
        // Nothing to do here.
    }
}
