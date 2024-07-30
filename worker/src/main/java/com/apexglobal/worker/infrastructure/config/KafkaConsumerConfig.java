package com.apexglobal.worker.infrastructure.config;

import com.apexglobal.worker.model.OrderDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Class</b>: KafkaConsumerConfig <br/>
 * 
 * @version 1.0.0
 */
@Configuration
public class KafkaConsumerConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;
  
  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Bean
  public Map<String, Object> consumerConfigs(){
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    return props;
  }

  @Bean
  public ConsumerFactory<String, OrderDto> consumerFactory() {
    JsonDeserializer<OrderDto> jsonDeserializer = new JsonDeserializer<>(OrderDto.class, false);
    jsonDeserializer.addTrustedPackages("*");
      
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(), 
            new StringDeserializer(), jsonDeserializer);
  }
  
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderDto>>
    kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, OrderDto> factory = 
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    
    return factory;
  }
}
