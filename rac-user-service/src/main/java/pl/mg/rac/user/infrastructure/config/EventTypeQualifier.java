package pl.mg.rac.user.infrastructure.config;


import org.springframework.beans.factory.annotation.Qualifier;
import pl.mg.rac.commons.event.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface EventTypeQualifier {

    EventType value();

}
