package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * A very simple implementation of the java {@link ParameterizedType} interface.
 * 
 * @author  Emil Forslund
 * @since   2.4.1
 */
public final class SimpleParameterizedType implements ParameterizedType {

    /**
     * Creates a new {@code SimpleParameterizedType} based on the class name of
     * the specified class and the specified parameters.
     * 
     * @param clazz       the class to get the name from
     * @param parameters  list of generic parameters to this type
     * @return            the created type
     */
    public static SimpleParameterizedType create(Class<?> clazz, Type... parameters) {
        return create(clazz.getName(), parameters);
    }
    
    /**
     * Creates a new {@code SimpleParameterizedType} with the specified absolute 
     * class name.
     * 
     * @param fullName    the absolute type name
     * @param parameters  list of generic parameters to this type
     * @return            the created simple type
     */
    public static SimpleParameterizedType create(String fullName, Type... parameters) {
        return new SimpleParameterizedType(fullName, parameters);
    }
    
    /**
     * Creates a new {@code SimpleParameterizedType} referencing the specified 
     * class in the specified file. These do not have to exist yet.
     * 
     * @param file        the file to reference
     * @param clazz       the class to reference
     * @param parameters  list of generic parameters to this type
     * @return            the new simple type
     */
    public static SimpleParameterizedType create(File file, ClassOrInterface<?> clazz, Type... parameters) {
        return create(SimpleTypeUtil.nameOf(file, clazz), parameters);
    }
    
    @Override
    public Type[] getActualTypeArguments() {
        return Arrays.copyOf(parameters, parameters.length);
    }

    @Override
    public Type getRawType() {
        return SimpleType.create(fullName);
    }

    @Override
    public Type getOwnerType() {
        throw new UnsupportedOperationException(
            "Owner types are currently not supported by CodeGen."
        );
    }

    @Override
    public String getTypeName() {
        return fullName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.fullName);
        hash = 29 * hash + Arrays.deepHashCode(this.parameters);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        else if (obj == null) { return false; }
        else if (!(obj instanceof ParameterizedType)) { return false; }
        
        final ParameterizedType other = (ParameterizedType) obj;
        return Objects.equals(fullName, other.getTypeName())
            && Arrays.deepEquals(parameters, other.getActualTypeArguments());
    }
    
    @Override
    public String toString() {
        return getTypeName();
    }
    
    private SimpleParameterizedType(String fullName, Type[] parameters) {
        this.fullName   = requireNonNull(fullName);
        this.parameters = requireNonNull(parameters);
    }
    
    private final String fullName;
    private final Type[] parameters;
    
}
