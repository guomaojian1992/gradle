/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance.regression.java

import org.gradle.performance.AbstractCrossVersionPerformanceTest
import spock.lang.Unroll

class JavaCleanTestPerformanceTest extends AbstractCrossVersionPerformanceTest {

    @Unroll
    def "clean test on #testProject"() {
        given:
        runner.testProject = testProject
        runner.tasksToRun = ['cleanTest', 'test']
        runner.warmUpRuns = warmUpRuns
        runner.runs = runs
        runner.targetVersions = ["3.5-20170223000042+0000"]
        runner.args = ['-i']

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()

        where:
        testProject                          | warmUpRuns | runs
        "largeMonolithicJavaProject"         | 2          | 6
        //"largeMonolithicJavaProjectTestNG" | 2          | 6 // testNG requires more test workers, which take too long to start up
        //"largeJavaMultiProject"            | 2          | 6 // due to long test worker startup times, multi-project test runs take too long
        //"largeJavaMultiProjectTestNG"      | 2          | 6
    }
}
