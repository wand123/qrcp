package ru.quickresto.qrcp.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static void invokeSetter(Object object, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = object.getClass().getMethod("set"+field.getName()
                .replaceFirst(field.getName().substring(0, 1), field.getName()
                        .substring(0, 1).toUpperCase()),field.getType());

        method.invoke(object, value);
    }

    public static List<Field> getDeclaredColumnFields(Class<?> type, Class<? extends Annotation> annotationType) {
        List<Field> declaredColumnFields = Collections.emptyList();

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationType)) {
                declaredColumnFields.add(field);
            }
        }

        Class<?> parentType = type.getSuperclass();
        if (parentType != null) {
            declaredColumnFields.addAll(getDeclaredColumnFields(parentType, annotationType));
        }

        return declaredColumnFields;
    }
}