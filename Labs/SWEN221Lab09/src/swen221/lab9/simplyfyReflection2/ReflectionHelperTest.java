package swen221.lab9.simplyfyReflection2;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReflectionHelperTest {

    @Test
    public void testExample() {
        String res = (String) ReflectionHelper.tryCatch(() -> String.class.getMethod("toString").invoke(""));
        assertEquals("", res);
    }

    @SuppressWarnings("serial")
    static class MyError extends Error {
    }

    @SuppressWarnings("serial")
    static class MyRuntime extends RuntimeException {
    }

    @SuppressWarnings("serial")
    static class MyChecked extends Exception {
    }

    static class DoErrors {
        public int foo() {
            throw new MyError();
        }

        public int bar() {
            throw new MyRuntime();
        }

        public int beer() throws Throwable {
            throw new MyChecked();
        }
    }

    @Test(expected = MyError.class)
    public void testError() {
        @SuppressWarnings("unused")
        String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("foo").invoke(new DoErrors()));
        throw new Error("Expected error");
    }

    @Test(expected = MyRuntime.class)
    public void testRuntime() {
        @SuppressWarnings("unused")
        String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("bar").invoke(new DoErrors()));
        throw new Error("Expected error");
    }

    @Test(expected = MyChecked.class)
    public void testChecked() throws Throwable {
        try {
            @SuppressWarnings("unused")
            String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("beer").invoke(new DoErrors()));
        } catch (Error e) {
            throw e.getCause();
        }
        throw new Error("Expected error");
    }

    @Test(expected = NoSuchMethodException.class)
    public void testNoMethod() throws Throwable {
        try {
            @SuppressWarnings("unused")
            String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("orange").invoke(new DoErrors()));
        } catch (Error e) {
            throw e.getCause();
        }
        throw new Error("Expected error");
    }
}
