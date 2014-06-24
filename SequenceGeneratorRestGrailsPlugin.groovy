/*
 * Copyright (c) 2012 Goran Ehrsson.
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

class SequenceGeneratorRestGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def dependsOn = [:]
    def loadAfter = ['sequenceGenerator']
    def pluginExcludes = [
            "grails-app/views/error.gsp",
    ]
    def title = "Restful Sequence Number Generator"
    def author = "Goran Ehrsson"
    def authorEmail = "goran@technipelago.se"
    def description = '''
This plugin is an extension to the 'sequence-generator' plugin. This plugin communicates with a REST service
to get sequences. See 'sequence-generator' plugin for general information about sequence generators.
'''
    def documentation = "https://github.com/goeh/grails-sequence-generator-rest"
    def license = "APACHE"
    def organization = [name: "Technipelago AB", url: "http://www.technipelago.se/"]
    def issueManagement = [system: "github", url: "https://github.com/goeh/grails-sequence-generator-rest/issues"]
    def scm = [url: "https://github.com/goeh/grails-sequence-generator-rest"]

    def doWithSpring = {

        sequenceGenerator(grails.plugins.sequence.RestSequenceGenerator) { bean ->
            bean.autowire = 'byName'
        }
    }
}
