package br.com.elo7.starlink.infra;

import br.com.elo7.starlink.domains.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterImpl implements Converter {

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String serialize(Object value) throws RuntimeException {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(String content, TypeReference<T> valueTypeRef) throws RuntimeException {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
