package Codes.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Loggable{
    String value() default "Default Log Message";
}

class LogService{
    @Loggable("Executing a log method")
    public void logMethod(){
        System.out.println("inside log Method");
    }

    public void noLogMethod(){
        System.out.println("Not a log method");
    }

    @Loggable("new log mehtod")
    public void newLogMethod(){
        System.out.println("inside new log method");
    }
}

public class MethodLevelAnnotationReflection {
    public static void main(String[] args){
        Class<LogService> ls = LogService.class;
        System.out.println("Scanning the ls class for @Loggable annotations");

        Method meth[] = ls.getDeclaredMethods();
        for(Method m: meth){
            if(m.isAnnotationPresent(Loggable.class)){
                System.out.println("Annotated method: "+m.getName());

                Loggable l = m.getAnnotation(Loggable.class);
                System.out.println("Annotatin value: "+l.value());
            }
        }
    }
}
