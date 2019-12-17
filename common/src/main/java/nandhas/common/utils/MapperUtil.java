package nandhas.common.utils;

import org.modelmapper.ModelMapper;

/**
 * MapperUtils
 */
public final class MapperUtil {

    public static <T> T Convert(Object source, Class<T> destinationType) {
        ModelMapper modelMapper = SpringContext.getBean(ModelMapper.class);
        return modelMapper.map(source, destinationType);
    }
}