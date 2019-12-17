package nandhas.common.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;;

/**
 * CustomError
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomError {

    String code();
    
}