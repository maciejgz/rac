package pl.mg.rac.user.infrastructure.out.messaging;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RacTracingProducerInterceptor<K, V> implements ProducerInterceptor<K, V> {

    public RacTracingProducerInterceptor() {
    }

    @Override
    public ProducerRecord<K, V> onSend(ProducerRecord<K, V> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        // No action
    }

    @Override
    public void close() {
        // No action
    }

    @Override
    public void configure(Map<String, ?> configs) {
        // No action
    }

}
