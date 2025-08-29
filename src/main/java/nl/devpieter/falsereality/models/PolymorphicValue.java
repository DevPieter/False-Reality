package nl.devpieter.falsereality.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public class PolymorphicValue<T> {

    private static final Gson gson = new Gson();

    private String className;
    private JsonElement data;

    private transient T instance;

    public PolymorphicValue() {
    }

    public PolymorphicValue(@NotNull T value) {
        this.className = value.getClass().getName();
        this.data = gson.toJsonTree(value);
        this.instance = value;
    }

    public T getValue(Class<T> baseType) {
        if (instance != null) return instance;

        try {
            Class<?> clazz = Class.forName(className);
            instance = baseType.cast(gson.fromJson(data, clazz));

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sync() {
        this.data = gson.toJsonTree(instance);
    }
}
