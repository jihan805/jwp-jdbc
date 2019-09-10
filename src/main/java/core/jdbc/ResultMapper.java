package core.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ResultMapper<T> implements Mapper<T> {

    private Class<?> clazz;

    public ResultMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T mapResult(ResultSet rs) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        Class<?>[] types = Arrays.stream(fields)
                .map(Field::getType)
                .toArray(Class<?>[]::new);

        Constructor<?> constructor = clazz.getConstructor(types);
        Object[] parameters = getParameters(fields, rs);
        Object obj = constructor.newInstance(parameters);

        return (T) obj;
    }

    private Object[] getParameters(Field[] fields, ResultSet rs) throws SQLException {
        Object[] parameters = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            parameters[i] = rs.getString(fields[i].getName());
        }

        return parameters;
    }

}
