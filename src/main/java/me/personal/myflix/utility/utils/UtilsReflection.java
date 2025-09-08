package me.personal.myflix.utility.utils;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class UtilsReflection {
    private UtilsReflection() {
        throw new IllegalStateException("UtilsReflection class");
    }

    public static void setClassNotNullMethods(Object entity, Map<String, Object> methods) {
        methods.forEach((key, value) -> callSetter(entity, key, value));
    }

    @SneakyThrows
    public static Object callGetter(Object obj, String fieldName){
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        return pd.getReadMethod().invoke(obj);
    }

    @SneakyThrows
    public static Object callGetterSuperclass(Object obj, String fieldName){
        return obj.getClass().getSuperclass().getMethod("get" + UtilsManipulation.capitalize(fieldName)).invoke(obj);
    }

    public static <T,E> void callGetterList(E v, String listName) {
        List<T> pro = (List<T>) UtilsReflection.callGetter(v, listName);
    }

    @SneakyThrows
    public static void callSetter(Object obj, String fieldName, Object value){
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        pd.getWriteMethod().invoke(obj, value);
    }

    public static <T> void callMethod(Class<T> classe, String nomeMetodo) {
        try {
            Method method = classe.getMethod(nomeMetodo);
            method.invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T,X> void callMethod(Class<T> classe, String nomeMetodo, X param) {
        try {
            param = UtilsHibernate.unProxy(param);
            Method method = classe.getMethod(nomeMetodo, Object.class);
            method.invoke(classe.getDeclaredConstructor().newInstance(), param);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static <T> void callMethod(Class<T> classe, String nomeMetodo, String param) {
        try {
            Method method = classe.getMethod(nomeMetodo, String.class);
            Object o = method.invoke(null, param);
            log.info("Method " + nomeMetodo + " called with parameter " + param + " and returned " + o);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Method> getMethods(Class<?> clazz) {
        Map<String, Method> fields = new HashMap<String, Method>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!fields.containsKey(method.getName())) {
                    fields.put(method.getName(), method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        Collection<Method> returnCollection = fields.values();
        returnCollection.forEach(e -> log.info("M: " + e.getName() + " " + Arrays.toString(e.getParameterTypes())));
        return returnCollection;
    }

    public static Set<String> getMethodsFiltered(Class<?> clazz, String[] filters) {
        Set<String> methods = new HashSet<>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().startsWith("get")) {
                    String name = method.getName().replace("get", "");
                    name = String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
                    if (Arrays.stream(filters).noneMatch(name::equals)) {
                        methods.add(name);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    public static Field getField(Class<?> clazz, String target) {
        Field fieldTarget = null;
        while (fieldTarget == null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (target.equals(field.getName())) {
                    fieldTarget = field;
                    break;
                }
            }
        }
        return fieldTarget;
    }

    public static Collection<Field> getFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName())) {
                    fields.put(field.getName(), field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        Collection<Field> returnCollection = fields.values();
        return returnCollection;
    }


    public static <E> E createContents(Class<E> clazz) throws NoSuchMethodException, InvocationTargetException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static List<String> getNullPropertyNamesList(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    boolean res = false;
                    Object obj = wrappedSource.getPropertyValue(propertyName);
                    if (obj != null) {
                        if (obj instanceof List) {
                            List<?> list = (List<?>) obj;
                            res = list.isEmpty();
                            //res = true; // TODO - for the moment, I exclude the list to avoid the following error: "org.hibernate.PersistentObjectException: detached entity passed to persist"
                        }
                    } else
                        res = true;
                    return res;
                })
                .collect(Collectors.toList());
    }

    public static List<String> getNotNullPropertyNamesList(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> {
                    boolean res = true;
                    Object obj = wrappedSource.getPropertyValue(propertyName);
                    if (obj != null) {
                        if (obj instanceof List || obj instanceof Class) {
                            res = false;
                        }
                    } else
                        res = false;
                    return res;
                })
                .toList();
    }

    public static Map<String, Object> getClassNotNullMethods(Object entity, boolean superclass, String...filters) {
        Map<String, Object> methods = new HashMap<>();
        Class<?> clazz = entity.getClass();
        while (clazz != null) {
            Map<String, Object> meths = Stream.of(clazz.getDeclaredMethods())
                    .filter(m -> Arrays.stream(filters).noneMatch(m.getName()::equals) && m.getName().startsWith("get"))
                    .map(m -> {
                        String meth = m.getName().replace("get", "");
                        return String.valueOf(meth.charAt(0)).toLowerCase() + meth.substring(1);
                    })
                    .filter(m -> callGetter(entity, m) != null)
                    .collect(Collectors.toMap(m -> m, m -> callGetter(entity, m)));
            methods.putAll(meths);
            clazz = superclass ? clazz.getSuperclass() : null;
        }
        return methods;
    }

    public static Optional<Field> getFirstAnnotatedField(Class<?> clazz, Class annotationClass){
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getDeclaredAnnotationsByType(annotationClass).length > 0).findFirst();
    }
    public static List<Field> getAnnotatedFields(Class<?> clazz, Class annotationClass){
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getDeclaredAnnotationsByType(annotationClass).length > 0).collect(Collectors.toList());
    }
    public static List<Field> getAnnotatedFields(Class<?> clazz, List<Class> annotationClass){
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> annotationClass.stream().anyMatch(a -> f.getDeclaredAnnotationsByType(a).length > 0)).collect(Collectors.toList());
    }
    public static List<Field> getNotEmptyNotNull(Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> (f.getDeclaredAnnotationsByType(NotNull.class).length > 0 || f.getDeclaredAnnotationsByType(NotEmpty.class).length > 0))
                .toList();
    }
    public static <T> Boolean checkInvalidElements(T entity){
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> (f.getDeclaredAnnotationsByType(NotNull.class).length > 0 || f.getDeclaredAnnotationsByType(NotEmpty.class).length > 0))
                .anyMatch(f -> callGetter(entity, f.getName()) == null);
    }

    public static <T> String findAndGetIdFieldName(T entity){
        String fieldName = null;
        Optional<Field> field = UtilsReflection.getFirstAnnotatedField(entity.getClass(), Id.class);
        if (field.isPresent()) {
            fieldName = field.get().getName();
        }
        return fieldName;
    }

    public static <T, ID extends Serializable> ID findAndGetId(T entity){
        ID id = null;
        Optional<Field> field = UtilsReflection.getFirstAnnotatedField(entity.getClass(), Id.class);
        if (field.isPresent()) {
            id = (ID) UtilsReflection.callGetter(entity, field.get().getName());
        }
        return id;
    }

    public static List<Field> equalsTargetClassFields(Class<?> clazz, Class<?> targetClass){
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> {
            Class<?> classToCheck = f.getType();
            if (f.getType().isAssignableFrom(List.class) || f.getType().isAssignableFrom(Set.class)) {
                ParameterizedType listType = (ParameterizedType) f.getGenericType();
                classToCheck = (Class<?>) listType.getActualTypeArguments()[0];
            }
            return classToCheck.equals(targetClass);
        }).collect(Collectors.toList());
    }

    public static List<Field> equalsTargetClassFieldMappedBy(Class<?> clazz, String fieldName){
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> {
            OneToOne oneToOne = f.getAnnotation(OneToOne.class);
            OneToMany oneToMany = f.getAnnotation(OneToMany.class);
            ManyToMany manyToMany = f.getAnnotation(ManyToMany.class);
            String mappedBy = oneToOne != null ? oneToOne.mappedBy() : oneToMany != null ? oneToMany.mappedBy() : manyToMany != null ? manyToMany.mappedBy() : "";
            return mappedBy.equals(fieldName);
        }).collect(Collectors.toList());
    }

    @SneakyThrows
    public static <T> T callDefaultConstructor(Class<?> clazz){
        Constructor<?> costrCity = clazz.getConstructor();
        return (T) costrCity.newInstance();
    }
}
