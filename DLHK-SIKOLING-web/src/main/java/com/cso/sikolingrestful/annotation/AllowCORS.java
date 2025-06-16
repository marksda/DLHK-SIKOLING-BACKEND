package com.cso.sikolingrestful.annotation;

import jakarta.ws.rs.NameBinding;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@NameBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface AllowCORS {
    
}
