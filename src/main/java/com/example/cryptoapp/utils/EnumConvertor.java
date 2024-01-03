package com.example.cryptoapp.utils;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class EnumConvertor {
    public static <E extends Enum<E>> List<String> enumToStringList(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

   // ExampleEnum enumValue = stringValueToEnum(ExampleEnum.class, "VALUE1");
    public static <E extends Enum<E>> E stringValueToEnum(Class<E> enumType, String value) {
        if (value == null || enumType == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        for (E enumValue : EnumSet.allOf(enumType)) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }

        throw new IllegalArgumentException("No enum constant " + enumType + "." + value);
    }
}
