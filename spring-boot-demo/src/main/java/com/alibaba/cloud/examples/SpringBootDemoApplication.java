/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootDemoApplication.class).web(WebApplicationType.NONE).run(args);
    }

    final String topic = "hz-topic";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    public CommandLineRunner runner1() {
        return args -> {
            rocketMQTemplate.send(topic, MessageBuilder.withPayload("hi, this is msg from springboot").build());
        };
    }

    @Service
    @RocketMQMessageListener(topic = topic, consumerGroup = "20190921-gz")
    class MyListener implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            System.out.println("receive: " + message);
        }
    }

}
