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

package org.gradle.integtests.fixtures

class KotlinScriptIntegrationTest extends AbstractIntegrationSpec {

    @Override
    protected String getDefaultBuildFileName() {
        'build.gradle.kts'
    }

    def setup() {
        settingsFile << "rootProject.buildFileName = '$defaultBuildFileName'"
    }

    protected void withKotlinBuildSrc() {
        file("buildSrc/settings.gradle")  << "rootProject.buildFileName = 'build.gradle.kts'"
        file("buildSrc/build.gradle.kts") << """
            buildscript {
                repositories { gradleScriptKotlin() }
                dependencies { classpath(kotlinModule("gradle-plugin")) }
            }
            apply { plugin("kotlin") }
            repositories { gradleScriptKotlin() }
            dependencies { compile(gradleScriptKotlinApi()) }
        """
    }
}
