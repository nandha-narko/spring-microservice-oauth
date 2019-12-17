package nandhas.common.exception;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String body = IOUtils.toString(response.body().asInputStream());
            Collection<String> values = response.headers().get("errorcode");
            if (values != null && values.size() > 0) {
                String errorCode = (String) values.toArray()[0];
                Reflections ref = new Reflections("nandhas.common.exception");
                for (Class<?> cl : ref.getTypesAnnotatedWith(CustomError.class)) {
                    CustomError customError = cl.getAnnotation(CustomError.class);
                    if (customError.code().equals(errorCode)) {
                        return (Exception) cl.newInstance();
                    }
                }
            }
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Exception();
    }
}