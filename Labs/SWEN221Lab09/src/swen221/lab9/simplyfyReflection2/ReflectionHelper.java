package swen221.lab9.simplyfyReflection2;

import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper {
    public static interface SupplierWithException<T> {
        T get() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
                SecurityException;
    }

    public static <T> T tryCatch(SupplierWithException<T> s) {
        try {
            return s.get();
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new Error("Unexpected shape of the classes", e);
        } catch (SecurityException e) {
            throw new Error("Reflection blocked by security manager", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new Error("Unexpected checked exception", cause);
        }
    }
}
