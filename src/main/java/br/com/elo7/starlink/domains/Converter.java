package br.com.elo7.starlink.domains;

import com.fasterxml.jackson.core.type.TypeReference;

public interface Converter {

    String serialize(Object value) throws RuntimeException;
    <T> T deserialize(String content, TypeReference<T> valueTypeRef) throws RuntimeException;

}
