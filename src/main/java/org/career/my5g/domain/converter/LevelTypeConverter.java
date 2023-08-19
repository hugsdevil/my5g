package org.career.my5g.domain.converter;

import javax.persistence.AttributeConverter;

import org.career.my5g.domain.type.LevelType;

public class LevelTypeConverter implements AttributeConverter<LevelType, String> {
    @Override
    public String convertToDatabaseColumn(LevelType attribute) {
        return attribute.toString();
    }

    @Override
    public LevelType convertToEntityAttribute(String dbData) {
        return LevelType.stringOf(dbData);
    }
}
