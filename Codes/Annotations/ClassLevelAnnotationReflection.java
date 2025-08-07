package Codes.Annotations;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AppInfo{
    String auth() default "Unknown";
    String version() default "1.0";
}

@AppInfo(auth = "Me", version = "2.0")
class MyApp{
    public void run(){
        System.out.println("The app's runnning!");
    }
}

public class ClassLevelAnnotationReflection {
    public static void main(String[] args){
        Class<MyApp> appClass = MyApp.class;

        if(appClass.isAnnotationPresent(AppInfo.class)){
            System.out.println("AppInfo Annotation is present");
            
            AppInfo appInfo = appClass.getAnnotation(AppInfo.class);
            System.out.println("Auth: "+appInfo.auth());
            System.out.println("Version: "+appInfo.version());
        }else{
            System.out.println("AppInfo Annotation not present!");
        }
    }
}
