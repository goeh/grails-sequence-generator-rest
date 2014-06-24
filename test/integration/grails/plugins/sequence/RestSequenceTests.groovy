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

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created by goran on 2014-06-24.
 */
class RestSequenceTests extends GroovyTestCase {

    GrailsApplication grailsApplication

    void testCreateSequence() {
        def g = new RestSequenceGenerator<Long>()
        g.grailsApplication = grailsApplication
        try {
            g.deleteSequence(0L, "test", null)
        } catch (Exception e) {
            println e.message
        }
        def s = g.createSequence(0L, "test", null, "%04d", 42L)
        assertEquals "test", s.name
        assertEquals "%04d", s.format
        assertEquals "0042", s.getNumberFormatted()

        s = g.status(0L, "test", null)
        assertEquals "test", s.name
        assertEquals "%04d", s.format
        assertEquals "0042", s.getNumberFormatted()

        assertEquals "0042", g.nextNumber(0L, "test", null)
        assertEquals "0043", g.nextNumber(0L, "test", null)
        assertEquals "0044", g.nextNumber(0L, "test", null)
    }
}
