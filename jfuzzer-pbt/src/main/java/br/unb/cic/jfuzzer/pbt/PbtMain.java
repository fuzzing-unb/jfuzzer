package br.unb.cic.jfuzzer.pbt;

import java.awt.List;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import br.unb.cic.jfuzzer.api.Fuzzer;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;

public class PbtMain {

    private PbtConfig config;
    private FuzzerManager generatorManager;

    public PbtMain() {
        this(new PbtConfig());
    }

    public PbtMain(PbtConfig config) {
        this.config = config;
        generatorManager = new FuzzerManager(config);
    }

    public <T> T next(Class<T> clazz) {
        // recupera o gerador da classe especificada
        Fuzzer<T> generator = generatorManager.get(clazz);
        // caso exista um gerador para a classe especificada
        if (!Objects.isNull(generator)) {
            // gera um novo valor e o retorna
            return generator.fuzz();
        }
        // senao, usa java reflaction para construir o objeto

        // TODO
        try {
            return nextObject(clazz);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T nextObject(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
//        System.err.println("::::: TMP ::::");
        Field[] fields = clazz.getDeclaredFields();
        T instance = clazz.getConstructor().newInstance();
        for (Field field : fields) {

//            System.out.println("field=" + field.getName());

            // checa se o field deve ser tratado (nao tratar fields estaticos ou final)
            // se o campo for privado, verifica se pode ser acessado
            // e se nao for privado continua o tratamento
            // em resumo: se o field for "tratavel"
            if (notStaticOrFinal(field) && checkPrivate(field)) {

                // TODO tratar: colecoes, (nao tratar) fields static, arrays, enums, heranca,
                // primitivos,
                // depth, optional ...
                // TODO fazer algo parecido:
                // https://github.com/j-easy/easy-random/blob/97b508e9736429e509b620c33266dc73aa69867b/easy-random-core/src/main/java/org/jeasy/random/util/ReflectionUtils.java

                if (Collection.class.isAssignableFrom(field.getType())) {
                    populateCollection(field, instance);
                } else if (hasValidationAnnotations(field)) {
                    // TODO tratamento expecifico de javax.validation
                    populateAnnotedField(field, instance);
                } else {
                    populateField(field, instance);
                }
            }

        }
        return instance;
    }

    private <T> void populateCollection(Field field, T instance) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // TODO
        // gera um tamanho aleatorio da colecao, dentro do range especificado
        Integer size = new NumberFuzzer<Integer>(config.getCollectionSizeRange(), getRandom()).fuzz();
        // instancia a colecao
        Collection collection = instantiateCollection(field.getType());
        // popula a colecao
        for (int i = 0; i < size; i++) {
            collection.add(next(getGenericType(field)));
        }
        // seta a colecao na instancia
        Method setterMethod = getSetterMethod(field.getDeclaringClass(), field);
        if (!Objects.isNull(setterMethod)) {
            setterMethod.invoke(instance, collection);
        }
    }

    // TODO rever
    private Class<?> getGenericType(Field field) {
        Type type = field.getGenericType();
        ParameterizedType pType = (ParameterizedType) type;
        return (Class<?>) pType.getActualTypeArguments()[0];
    }

    // TODO rever
    private <T> Collection<? extends T> instantiateCollection(Class<T> clazz) {
        if (List.class.equals(clazz)) {
            return new ArrayList<>();
        }
        if (Set.class.equals(clazz)) {
            return new HashSet<>();
        }
        // TODO tratar os outros casos
        return new ArrayList<>();
    }

    private <T> void populateField(Field field, T instance) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = field.getDeclaringClass();
        Method setterMethod = getSetterMethod(clazz, field);
        if (!Objects.isNull(setterMethod)) {
//          System.err.println(">>> " + field + "=" + field.getType() + " >>> " + next(field.getType()));
            setterMethod.invoke(instance, next(field.getType()));
//            return next(field.getType());
        }
    }

    private <T> void populateAnnotedField(Field field, T instance) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // TODO
        Class<?> clazz = field.getDeclaringClass();
        Method setterMethod = getSetterMethod(clazz, field);
        if (!Objects.isNull(setterMethod)) {
//          System.err.println(">>> " + field + "=" + field.getType() + " >>> " + next(field.getType()));
            setterMethod.invoke(instance, next(field.getType()));
//            return next(field.getType());
        }
    }

    private boolean notStaticOrFinal(Field field) {
        return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
    }

    private boolean checkPrivate(Field field) {
        // se o field for privado
        if (Modifier.isPrivate(field.getModifiers())) {
            // libera acesso ao field privado
            // field.setAccessible(true);
            return field.trySetAccessible();
        }
        return true;
    }

    private boolean hasValidationAnnotations(Field field) {
        // TODO
        // if (field.getAnnotations().length > 0) {
        return false;
    }

    /**
     * Recupera o método setter do field passado como parâmetro.
     * 
     * @param clazz a classe onde está o field
     * @param field o field
     * @return retorna o método setter do field, ou null caso o método não tenha
     *         sido encontrado.
     * @throws IntrospectionException
     */
    private Method getSetterMethod(Class clazz, Field field) throws IntrospectionException {
        BeanInfo beaninfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor pds[] = beaninfo.getPropertyDescriptors();
        Method setterMethod = null;
        for (PropertyDescriptor pd : pds) {
            if (field.getName().equals(pd.getName())) {
                setterMethod = pd.getWriteMethod();
                if (!Objects.isNull(setterMethod)) {
                    return setterMethod;
                }
            }
        }
        return null;
    }

    public <T> void register(Class<T> clazz, Fuzzer<T> generator) {
        generatorManager.register(clazz, generator);
    }

    public Random getRandom() {
        return config.getRandom().getRandom();
    }
}
