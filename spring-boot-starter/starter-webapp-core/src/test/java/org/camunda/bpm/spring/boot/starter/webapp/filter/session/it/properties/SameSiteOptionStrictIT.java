/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.starter.webapp.filter.session.it.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.camunda.bpm.spring.boot.starter.webapp.filter.util.HeaderRule;
import org.camunda.bpm.spring.boot.starter.webapp.filter.util.TestApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
  "camunda.bpm.webapp.session-cookie.sameSiteCookieOption=strict"
})
public class SameSiteOptionStrictIT {

  @Rule
  public HeaderRule headerRule = new HeaderRule();

  @LocalServerPort
  public int port;

  @Test
  public void shouldSetSameSiteCookieOptionStrict() {
    headerRule.performRequest("http://localhost:" + port + "/camunda/app/tasklist/default");

    String sessionCookieValue = headerRule.getSessionCookieValue();

    assertThat(sessionCookieValue).matches(headerRule.getSessionCookieRegex("Strict"));
  }

}
