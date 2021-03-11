package br.unb.cic.jfuzzer.util;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TransferQueue;

//TODO tratar license!!!
//Retirado de: https://github.com/j-easy/easy-random/blob/97b508e9736429e509b620c33266dc73aa69867b/easy-random-core/src/main/java/org/jeasy/random/util/ReflectionUtils.java
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * Get declared fields of a given type.
     *
     * @param type the type to introspect
     * @param <T>  the actual type to introspect
     * @return list of declared fields
     */
    public static <T> List<Field> getDeclaredFields(T type) {
        return new ArrayList<>(asList(type.getClass().getDeclaredFields()));
    }

    /**
     * Get inherited fields of a given type.
     *
     * @param type the type to introspect
     * @return list of inherited fields
     */
    public static List<Field> getInheritedFields(Class<?> type) {
        List<Field> inheritedFields = new ArrayList<>();
        while (type.getSuperclass() != null) {
            Class<?> superclass = type.getSuperclass();
            inheritedFields.addAll(asList(superclass.getDeclaredFields()));
            type = superclass;
        }
        return inheritedFields;
    }

    /**
     * Set a value in a field of a target object. If the target object provides a
     * setter for the field, this setter will be used. Otherwise, the field will be
     * set using reflection.
     *
     * @param object instance to set the property on
     * @param field  field to set the property on
     * @param value  value to set
     * @throws IllegalAccessException if the property cannot be set
     */
    public static void setProperty(final Object object, final Field field, final Object value) throws IllegalAccessException, InvocationTargetException {
        try {
            Optional<Method> setter = getWriteMethod(field);
            if (setter.isPresent()) {
                setter.get().invoke(object, value);
            } else {
                setFieldValue(object, field, value);
            }
        } catch (IllegalAccessException e) {
            // otherwise, set field using reflection
            setFieldValue(object, field, value);
        }
    }

    /**
     * Set a value (accessible or not accessible) in a field of a target object.
     *
     * @param object instance to set the property on
     * @param field  field to set the property on
     * @param value  value to set
     * @throws IllegalAccessException if the property cannot be set
     */
    public static void setFieldValue(final Object object, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.trySetAccessible();
        field.set(object, value);
        field.setAccessible(access);
    }

    /**
     * Get the value (accessible or not accessible) of a field of a target object.
     *
     * @param object instance to get the field of
     * @param field  field to get the value of
     * @return the value of the field
     * @throws IllegalAccessException if field cannot be accessed
     */
    public static Object getFieldValue(final Object object, final Field field) throws IllegalAccessException {
        boolean access = field.trySetAccessible();
        Object value = field.get(object);
        field.setAccessible(access);
        return value;
    }

    /**
     * Get wrapper type of a primitive type.
     *
     * @param primitiveType to get its wrapper type
     * @return the wrapper type of the given primitive type
     */
//    public static Class<?> getWrapperType(Class<?> primitiveType) {
//        for(PrimitiveEnum p : PrimitiveEnum.values()) {
//            if(p.getType().equals(primitiveType)) {
//                return p.getClazz();
//            }
//        }
//
//        return primitiveType; // if not primitive, return it as is
//    }

    /**
     * Check if a field has a primitive type and matching default value which is set
     * by the compiler.
     *
     * @param object instance to get the field value of
     * @param field  field to check
     * @return true if the field is primitive and is set to the default value, false
     *         otherwise
     * @throws IllegalAccessException if field cannot be accessed
     */
    public static boolean isPrimitiveFieldWithDefaultValue(final Object object, final Field field) throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        if (!fieldType.isPrimitive()) {
            return false;
        }
        Object fieldValue = getFieldValue(object, field);
        if (fieldValue == null) {
            return false;
        }
        if (fieldType.equals(boolean.class) && (boolean) fieldValue == false) {
            return true;
        }
        if (fieldType.equals(byte.class) && (byte) fieldValue == (byte) 0) {
            return true;
        }
        if (fieldType.equals(short.class) && (short) fieldValue == (short) 0) {
            return true;
        }
        if (fieldType.equals(int.class) && (int) fieldValue == 0) {
            return true;
        }
        if (fieldType.equals(long.class) && (long) fieldValue == 0L) {
            return true;
        }
        if (fieldType.equals(float.class) && (float) fieldValue == 0.0F) {
            return true;
        }
        if (fieldType.equals(double.class) && (double) fieldValue == 0.0D) {
            return true;
        }
        if (fieldType.equals(char.class) && (char) fieldValue == '\u0000') {
            return true;
        }
        return false;
    }

    /**
     * Check if a field is static.
     *
     * @param field the field to check
     * @return true if the field is static, false otherwise
     */
    public static boolean isStatic(final Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * Check if a type is an interface.
     *
     * @param type the type to check
     * @return true if the type is an interface, false otherwise
     */
    public static boolean isInterface(final Class<?> type) {
        return type.isInterface();
    }

    /**
     * Check if the type is abstract (either an interface or an abstract class).
     *
     * @param type the type to check
     * @param <T>  the actual type to check
     * @return true if the type is abstract, false otherwise
     */
    public static <T> boolean isAbstract(final Class<T> type) {
        return Modifier.isAbstract(type.getModifiers());
    }

    /**
     * Check if the type is public.
     *
     * @param type the type to check
     * @param <T>  the actual type to check
     * @return true if the type is public, false otherwise
     */
    public static <T> boolean isPublic(final Class<T> type) {
        return Modifier.isPublic(type.getModifiers());
    }

    /**
     * Check if a type is an array type.
     *
     * @param type the type to check.
     * @return true if the type is an array type, false otherwise.
     */
    public static boolean isArrayType(final Class<?> type) {
        return type.isArray();
    }

    /**
     * Check if a type is an enum type.
     *
     * @param type the type to check.
     * @return true if the type is an enum type, false otherwise.
     */
    public static boolean isEnumType(final Class<?> type) {
        return type.isEnum();
    }

    /**
     * Check if a type is a collection type.
     *
     * @param type the type to check.
     * @return true if the type is a collection type, false otherwise
     */
    public static boolean isCollectionType(final Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a collection type.
     *
     * @param type the type to check.
     * @return true if the type is a collection type, false otherwise
     */
    public static boolean isCollectionType(final Type type) {
        return isParameterizedType(type) && isCollectionType((Class<?>) ((ParameterizedType) type).getRawType());
    }

    /**
     * Check if a type is populatable.
     *
     * @param type the type to check
     * @return true if the type is populatable, false otherwise
     */
    public static boolean isPopulatable(final Type type) {
        return !isWildcardType(type) && !isTypeVariable(type) && !isCollectionType(type) && !isParameterizedType(type);
    }

    /**
     * Check if a type should be introspected for internal fields.
     *
     * @param type the type to check
     * @return true if the type should be introspected, false otherwise
     */
    public static boolean isIntrospectable(final Class<?> type) {
        return !isEnumType(type)
                && !isArrayType(type)
                && !(isCollectionType(type) && isJdkBuiltIn(type))
                && !(isMapType(type) && isJdkBuiltIn(type));
    }

    /**
     * Check if a type is a map type.
     *
     * @param type the type to check
     * @return true if the type is a map type, false otherwise.
     */
    public static boolean isMapType(final Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is {@link Optional}.
     *
     * @param type the type to check
     * @return true if the type is {@link Optional}, false otherwise.
     */
    public static boolean isOptionalType(final Class<?> type) {
        return Optional.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a JDK built-in collection/map.
     *
     * @param type the type to check
     * @return true if the type is a built-in collection/map type, false otherwise.
     */
    public static boolean isJdkBuiltIn(final Class<?> type) {
        return type.getName().startsWith("java.util");
    }

    /**
     * Check if a type is a parameterized type
     *
     * @param type the type to check
     * @return true if the type is parameterized, false otherwise
     */
    public static boolean isParameterizedType(final Type type) {
        return type != null && type instanceof ParameterizedType && ((ParameterizedType) type).getActualTypeArguments().length > 0;
    }

    /**
     * Check if a type is a wildcard type
     *
     * @param type the type to check
     * @return true if the type is a wildcard type, false otherwise
     */
    public static boolean isWildcardType(final Type type) {
        return type instanceof WildcardType;
    }

    /**
     * Check if a type is a type variable
     *
     * @param type the type to check
     * @return true if the type is a type variable, false otherwise
     */
    public static boolean isTypeVariable(final Type type) {
        return type instanceof TypeVariable<?>;
    }

//    /**
//     * Searches the classpath for all public concrete subtypes of the given interface or abstract class.
//     *
//     * @param type to search concrete subtypes of
//     * @param <T>  the actual type to introspect
//     * @return a list of all concrete subtypes found
//     */
//    public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
//        return ClassGraphFacade.getPublicConcreteSubTypesOf(type);
//    }

    /**
     * Filters a list of types to keep only elements having the same parameterized
     * types as the given type.
     *
     * @param type  the type to use for the search
     * @param types a list of types to filter
     * @return a list of types having the same parameterized types as the given type
     */
    public static List<Class<?>> filterSameParameterizedTypes(final List<Class<?>> types, final Type type) {
        if (type instanceof ParameterizedType) {
            Type[] fieldArugmentTypes = ((ParameterizedType) type).getActualTypeArguments();
            List<Class<?>> typesWithSameParameterizedTypes = new ArrayList<>();
            for (Class<?> currentConcreteType : types) {
                List<Type[]> actualTypeArguments = getActualTypeArgumentsOfGenericInterfaces(currentConcreteType);
                typesWithSameParameterizedTypes.addAll(actualTypeArguments.stream().filter(currentTypeArguments -> Arrays.equals(fieldArugmentTypes, currentTypeArguments)).map(currentTypeArguments -> currentConcreteType).collect(toList()));
            }
            return typesWithSameParameterizedTypes;
        }
        return types;
    }

    /**
     * Looks for given annotationType on given field or read method for field.
     *
     * @param field          field to check
     * @param annotationType Type of annotation you're looking for.
     * @param <T>            the actual type of annotation
     * @return given annotation if field or read method has this annotation or null.
     */
    public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationType) {
        return field.getAnnotation(annotationType) == null ? getAnnotationFromReadMethod(getReadMethod(field).orElse(null),
                annotationType) : field.getAnnotation(annotationType);
    }

    /**
     * Checks if field or corresponding read method is annotated with given
     * annotationType.
     *
     * @param field          Field to check
     * @param annotationType Annotation you're looking for.
     * @return true if field or read method it annotated with given annotationType
     *         or false.
     */
    public static boolean isAnnotationPresent(Field field, Class<? extends Annotation> annotationType) {
        final Optional<Method> readMethod = getReadMethod(field);
        return field.isAnnotationPresent(annotationType) || readMethod.isPresent() && readMethod.get().isAnnotationPresent(annotationType);
    }

    /**
     * Return an empty implementation for a {@link Collection} type.
     *
     * @param collectionInterface for which an empty implementation should be
     *                            returned
     * @return empty implementation for the collection interface
     */
    public static Collection<?> getEmptyImplementationForCollectionInterface(final Class<?> collectionInterface) {
        Collection<?> collection = new ArrayList<>();
        if (List.class.isAssignableFrom(collectionInterface)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(collectionInterface)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(collectionInterface)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(collectionInterface)) {
            collection = new HashSet<>();
        } else if (BlockingDeque.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedBlockingDeque<>();
        } else if (Deque.class.isAssignableFrom(collectionInterface)) {
            collection = new ArrayDeque<>();
        } else if (TransferQueue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedTransferQueue<>();
        } else if (BlockingQueue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedBlockingQueue<>();
        } else if (Queue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedList<>();
        }
        return collection;
    }

    /**
     * Create an empty collection for the given type.
     * 
     * @param fieldType   for which an empty collection should we created
     * @param initialSize initial size of the collection
     * @return empty collection
     */
    public static Collection<?> createEmptyCollectionForType(Class<?> fieldType, int initialSize) {
        rejectUnsupportedTypes(fieldType);
        Collection<?> collection;
        try {
            collection = (Collection<?>) fieldType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            if (fieldType.equals(ArrayBlockingQueue.class)) {
                collection = new ArrayBlockingQueue<>(initialSize);
            } else {
                // TODO collection = (Collection<?>) new ObjenesisStd().newInstance(fieldType);
                collection = null;
            }
        }
        return collection;
    }

    /**
     * Return an empty implementation for the given {@link Map} interface.
     * 
     * @param mapInterface for which an empty implementation should be returned
     * @return empty implementation for the given {@link Map} interface.
     */
    public static Map<?, ?> getEmptyImplementationForMapInterface(final Class<?> mapInterface) {
        Map<?, ?> map = new HashMap<>();
        if (ConcurrentNavigableMap.class.isAssignableFrom(mapInterface)) {
            map = new ConcurrentSkipListMap<>();
        } else if (ConcurrentMap.class.isAssignableFrom(mapInterface)) {
            map = new ConcurrentHashMap<>();
        } else if (NavigableMap.class.isAssignableFrom(mapInterface)) {
            map = new TreeMap<>();
        } else if (SortedMap.class.isAssignableFrom(mapInterface)) {
            map = new TreeMap<>();
        }
        return map;
    }

    private static void rejectUnsupportedTypes(Class<?> type) {
        if (type.equals(SynchronousQueue.class)) {
            // SynchronousQueue is not supported since it requires a consuming thread at
            // insertion time
            throw new UnsupportedOperationException(SynchronousQueue.class.getName() + " type is not supported");
        }
        if (type.equals(DelayQueue.class)) {
            // DelayQueue is not supported since it requires creating dummy delayed objects
            throw new UnsupportedOperationException(DelayQueue.class.getName() + " type is not supported");
        }
    }

    /**
     * Get the write method for given field.
     *
     * @param field field to get the write method for
     * @return Optional of write method or empty if field has no write method
     */
    public static Optional<Method> getWriteMethod(Field field) {
        return getPublicMethod("set" + capitalize(field.getName()), field.getDeclaringClass(), field.getType());
    }

    /**
     * Get the read method for given field.
     * 
     * @param field field to get the read method for.
     * @return Optional of read method or empty if field has no read method
     */
    public static Optional<Method> getReadMethod(Field field) {
        String fieldName = field.getName();
        Class<?> fieldClass = field.getDeclaringClass();
        String capitalizedFieldName = capitalize(fieldName);
        // try to find getProperty
        Optional<Method> getter = getPublicMethod("get" + capitalizedFieldName, fieldClass);
        if (getter.isPresent()) {
            return getter;
        }
        // try to find isProperty for boolean properties
        return getPublicMethod("is" + capitalizedFieldName, fieldClass);
    }

    private static String capitalize(String propertyName) {
        return propertyName.substring(0, 1).toUpperCase(ENGLISH) + propertyName.substring(1);
    }

    private static Optional<Method> getPublicMethod(String name, Class<?> target, Class<?>... parameterTypes) {
        try {
            return Optional.of(target.getMethod(name, parameterTypes));
        } catch (NoSuchMethodException | SecurityException e) {
            return Optional.empty();
        }
    }

    private static <T extends Annotation> T getAnnotationFromReadMethod(Method readMethod, Class<T> clazz) {
        return readMethod == null ? null : readMethod.getAnnotation(clazz);
    }

    private static List<Type[]> getActualTypeArgumentsOfGenericInterfaces(final Class<?> type) {
        List<Type[]> actualTypeArguments = new ArrayList<>();
        Type[] genericInterfaceTypes = type.getGenericInterfaces();
        for (Type currentGenericInterfaceType : genericInterfaceTypes) {
            if (currentGenericInterfaceType instanceof ParameterizedType) {
                actualTypeArguments.add(((ParameterizedType) currentGenericInterfaceType).getActualTypeArguments());
            }
        }
        return actualTypeArguments;
    }

}
