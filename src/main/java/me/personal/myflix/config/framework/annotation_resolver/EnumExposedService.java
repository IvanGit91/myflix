package me.personal.myflix.config.framework.annotation_resolver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.SneakyThrows;
import me.personal.myflix.annotation.EnumExposedViaRest;
import me.personal.myflix.dto.EnumExposedResult;
import me.personal.myflix.utility.Const;
import me.personal.myflix.utility.framework.ClassUtils;
import me.personal.myflix.utility.multilanguage.Multilanguage;

import java.lang.reflect.Method;
import java.util.*;

import static me.personal.myflix.utility.utils.Utils.decapitalize;

public class EnumExposedService {

    private Map<String, EnumExposedResult> exposedEnums;

    public synchronized void init() {
        Map<String, EnumExposedResult> result = new HashMap<>();
        Collection<Class> enumExposedClasses = ClassUtils.getPackageEnums(
                Const.BASE_PACKAGE_NAME,
                List.of(EnumExposedViaRest.class.getSimpleName()));
        for (Class c : enumExposedClasses) {
            EnumExposedViaRest exp = (EnumExposedViaRest) c.getAnnotation(EnumExposedViaRest.class);
            String apiName = exp.enumApiName();
            List<Map<String, Object>> fields = this.getEnumToFieldsList(c);
            result.put(apiName, new EnumExposedResult(fields));
        }
        this.exposedEnums = result;
    }

    public EnumExposedResult getEnumExposedResult(String enumApiName) {
        if (this.exposedEnums == null)
            this.init();

        EnumExposedResult result = this.exposedEnums.get(enumApiName);
        if (result == null)
            throw new IllegalArgumentException("Cannot find an enum with apiName:" + enumApiName + ". Available enum names:" + this.exposedEnums.keySet());

        return result;
    }

    @SneakyThrows
    public List<Map<String, Object>> getEnumToFieldsList(Class<? extends Enum<?>> headerEnum) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Method> methodsToExpose = ClassUtils.getPublicGetIsMethods(headerEnum);
        EnumExposedViaRest exp = headerEnum.getAnnotation(EnumExposedViaRest.class);

        final Enum<?>[] enumValues = headerEnum.getEnumConstants();
        for (Enum<?> enumValue : enumValues) {
            Map<String, Object> fields = new HashMap<>();

            for (Method method : methodsToExpose) {
                String methodName = method.getName();
                methodName = methodName.startsWith("get") ? methodName.replace("get", "") : methodName;
                methodName = methodName.startsWith("is") ? methodName.replace("is", "") : methodName;
                methodName = decapitalize(methodName);
                if (method.getAnnotation(JsonIgnore.class) == null)
                    fields.put(methodName, method.invoke(enumValue));
            }
            fields.put("key", enumValue.name());

            // se richiesto aggiungi descrizione
            if (exp.multilanguageDescription())
                fields.put("description", Multilanguage.getMessage(enumValue));

            result.add(fields);
        }

        if (exp.multilanguageDescription())
            result.sort(Comparator.comparing(a -> a.get("description").toString()));

        return result;
    }
}
