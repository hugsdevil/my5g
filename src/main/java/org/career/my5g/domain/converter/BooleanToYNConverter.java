package org.career.my5g.domain.converter;

import javax.persistence.AttributeConverter;

public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null && attribute) {
            return "Y";
        } else {
            return "N";
        }
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
