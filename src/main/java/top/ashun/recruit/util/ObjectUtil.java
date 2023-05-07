package top.ashun.recruit.util;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author 18483
 */
public class ObjectUtil {

    public static void checkNotNull(Object object, String... propertyNames) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Arrays.stream(propertyNames).anyMatch(name -> name.contains(field.getName()))) {
                try {
                    field.setAccessible(true);

                    if (field.getType().equals(String.class)) {
                        // 判断属性类型是否为字符串类型
                        String value = (String) field.get(object);
                        if (value == null || value.trim().isEmpty()) {
                            String message = String.format("The property %s in %s must not be null or empty", field.getName(),
                                    object.getClass().getName());
                            throw new IllegalArgumentException(message);
                        }
                    } else { // 不是字符串类型的属性，判断是否为空
                        if (field.get(object) == null) {
                            String message = String.format("The property %s in %s must not be null", field.getName(),
                                    object.getClass().getName());
                            throw new IllegalArgumentException(message);
                        }
                    }

                } catch (IllegalAccessException e) {
                    String message = String.format("Failed to access property %s in %s", field.getName(),
                            object.getClass().getName());
                    throw new IllegalArgumentException(message, e);
                }
            }
        }
    }
}
