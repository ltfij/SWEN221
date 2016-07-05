package swen221.lab7;

import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.lang.reflect.*;
import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BiHashMapTests {

	@Test
	public void test01_Put() {
		checkMethodGenerics("get", Object.class);
		checkMethodGenerics("put", Object.class, Object.class);
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		assertTrue(map.get("Hello").equals("World"));
		assertTrue(map.getKeys("World").size() == 1);
		assertTrue(map.getKeys("World").contains("Hello"));
		assertTrue(map.containsKey("Hello"));
		assertTrue(map.containsValue("World"));
	}

	@Test
	public void test02_Remove() {
		checkMethodGenerics("get", Object.class);
		checkMethodGenerics("remove", Object.class);
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		map.remove("Hello");
		assertTrue(map.get("Hello") == null);
		assertTrue(map.getKeys("World").size() == 0);
		assertFalse(map.containsKey("Hello"));
		assertFalse(map.containsValue("World"));
	}

	@Test
	public void test03_Clear() {
		checkMethodGenerics("get", Object.class);
		checkMethodGenerics("clear");
		BiHashMap map = new BiHashMap();
		map.put("Hello", "World");
		map.clear();
		assertTrue(map.get("Hello") == null);
		assertTrue(map.getKeys("World") == null);
		assertFalse(map.containsKey("Hello"));
		assertFalse(map.containsValue("World"));
	}

	@Test
	public void test04_PutAll() {
		checkMethodGenerics("putAll", Map.class);
		HashMap<String, String> omap = new HashMap<String, String>();
		omap.put("Hello", "World");
		omap.put("Dave", "World");
		omap.put("Something", "Else");

		BiHashMap map = new BiHashMap();
		map.putAll(omap);

		for (Map.Entry<String, String> e : omap.entrySet()) {
			assertTrue(map.get(e.getKey()).equals(e.getValue()));
		}

		assertTrue(map.getKeys("World").size() == 2);
		assertTrue(map.getKeys("World").contains("Hello"));
		assertTrue(map.getKeys("World").contains("Dave"));
		assertTrue(map.getKeys("Else").size() == 1);
		assertTrue(map.getKeys("Else").contains("Something"));
	}

	@Test
	public void test05_EntrySet() {
		checkMethodGenerics("get", Object.class);
		checkMethodGenerics("entrySet");
		String[][] data = { { "Hello", "World" }, { "Dave", "World" }, { "Something", "Else" } };

		HashMap<String, String> omap = new HashMap<String, String>();
		HashMap<String, Set<String>> rmap = new HashMap<String, Set<String>>();

		for (String[] p : data) {
			omap.put(p[0], p[1]);
			Set<String> r = rmap.get(p[1]);
			if (r == null) {
				r = new HashSet<String>();
				rmap.put(p[1], r);
			}
			r.add(p[0]);
		}

		BiHashMap map = new BiHashMap();
		map.putAll(omap);
		for (Object o : map.entrySet()) {
			Map.Entry e = (Map.Entry) o;
			assertTrue(omap.get(e.getKey()).equals(e.getValue()));
			// Following line needed to convert set returned by getKeys() into a
			// HashSet for the comparison to work.
			HashSet<String> keys = new HashSet<String>(map.getKeys(e.getValue()));
			assertTrue(rmap.get(e.getValue()).equals(keys));
		}
	}

	// Non trivial tests!
	@Test
	public void test06_Update1() {
		checkMethodGenerics("put", Object.class, Object.class);
		checkMethodGenerics("keySet");
		BiHashMap b = new BiHashMap();
		b.put("Sam", "Person");
		b.put("Marco", "Person");
		b.put("Mary", "Person");
		System.out.println(b.keySet().size());
		assertEquals(3, b.keySet().size());
		assertEquals(3, b.getKeys("Person").size());
		assertTrue(b.getKeys("Person").size() <= b.keySet().size());
		b.put("Sam", "Person2");
//		assertEquals(3, b.getKeys("Person").size());
		assertEquals(2, b.getKeys("Person").size());
	}

	@Test
	public void test07_Update2() {
		checkMethodGenerics("put", Object.class, Object.class);
		checkMethodGenerics("keySet");
		BiHashMap b = new BiHashMap();
		b.put("Sam", "Person");
		assertEquals(1, b.keySet().size());
		assertEquals(1, b.getKeys("Person").size());
		assertTrue(b.getKeys("Person").size() <= b.keySet().size());
		b.put("Sam", "Person2");
//		assertEquals(1, b.getKeys("Person").size());
		assertEquals(0, b.getKeys("Person").size());
	}

	@Test
	public void test08_Update3() {
		checkMethodGenerics("put", Object.class, Object.class);
		checkMethodGenerics("keySet");
		BiHashMap b = new BiHashMap();
		b.put("Sam", "Person");
		b.put("Sam", "Person");
		// NOTE: To pass this test you will need to update the BiHashMap
		// implementation so that it maps values to Sets of keys, rather than
		// Lists of keys.
		assertEquals(b.getKeys("Person").size(),b.keySet().size());
		assertEquals(1, b.getKeys("Person").size());
	}

	@Test
	public void test09_values() {
		checkMethodGenerics("values");
		BiHashMap b = new BiHashMap();
		b.put("Sam", "Person");
		b.put("Marco", "Person2");
		assertEquals(b.values().size(),2);
		assertTrue(b.values().contains("Person"));
		assertTrue(b.values().contains("Person2"));	
	}
	
	/**
	 * <p>
	 * This method checks that the implementation of a given method in the
	 * BiHashMap class actually has declared generic parameters. Furthermore, it
	 * compares them against the corresponding signature given in Map to check
	 * they are roughly the same.
	 * </p>
	 * 
	 * NOTE: you do not need to understand how this method is working!
	 * 
	 * @param methodName
	 * @param o
	 */
	private void checkMethodGenerics(String name, Class<?>... parameterTypes) {
		Class<BiHashMap> c1 = BiHashMap.class;
		Class<Map> c2 = Map.class;
		Map<String, String> binding = determineBinding(c1, c2);
		try {
			Method m1 = c1.getDeclaredMethod(name, parameterTypes);
			Method m2 = c2.getDeclaredMethod(name, parameterTypes);
			Type[] m1types = m1.getGenericParameterTypes();
			Type[] m2types = m2.getGenericParameterTypes();
			for (int i = 0; i != m2types.length; ++i) {
				if (!equals(m1types[i], m2types[i], binding)) {
					fail("Generic signature for \"" + name + "\" is not correct.");
				}
			}
			Type r1 = m1.getGenericReturnType();
			Type r2 = m2.getGenericReturnType();
			if (!equals(r1, r2, binding)) {
				fail("Generic signature for \"" + name + "\" is not correct.");
			}
		} catch (NoSuchMethodException e) {
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail(e.getMessage());
		}
	}

	private Map<String, String> determineBinding(Class<?> c1, Class<?> c2) {
		HashMap<String, String> binding = new HashMap<String, String>();
		TypeVariable[] c1Parameters = c1.getTypeParameters();
		TypeVariable[] c2Parameters = c2.getTypeParameters();
		int min = Math.min(c1Parameters.length, c2Parameters.length);
		for (int i = 0; i < min; ++i) {
			TypeVariable tv1 = c1Parameters[i];
			TypeVariable tv2 = c2Parameters[i];
			binding.put(tv1.getName(), tv2.getName());
		}
		return binding;
	}

	private boolean equals(Type t1, Type t2, Map<String, String> binding) {
		if (t1 instanceof TypeVariable && t2 instanceof TypeVariable) {
			TypeVariable tv1 = (TypeVariable) t1;
			TypeVariable tv2 = (TypeVariable) t2;
			String n1 = binding.get(tv1.getName());
			String n2 = tv2.getName();
			return n1.equals(n2);
		} else if (t1 instanceof Class && t2 instanceof Class) {
			Class c1 = (Class) t1;
			Class c2 = (Class) t2;
			if (!c1.getName().equals(c2.getName())) {
				return false;
			}
			TypeVariable[] c1Parameters = c1.getTypeParameters();
			TypeVariable[] c2Parameters = c2.getTypeParameters();
			if (c1Parameters.length != c2Parameters.length) {
				return false;
			}
			for (int i = 0; i < c1Parameters.length; ++i) {
				if (!equals(c1Parameters[i], c2Parameters[i], binding)) {
					return false;
				}
			}
			return true;
		} else if (t1 instanceof ParameterizedType && t2 instanceof ParameterizedType) {
			ParameterizedType c1 = (ParameterizedType) t1;
			ParameterizedType c2 = (ParameterizedType) t2;
			Type[] c1Parameters = c1.getActualTypeArguments();
			Type[] c2Parameters = c2.getActualTypeArguments();
			if (c1Parameters.length != c2Parameters.length) {
				return false;
			}
			for (int i = 0; i < c1Parameters.length; ++i) {
				if (!equals(c1Parameters[i], c2Parameters[i], binding)) {
					return false;
				}
			}
			return true;
		} else if (t1 instanceof WildcardType && t2 instanceof WildcardType) {
			WildcardType w1 = (WildcardType) t1;
			WildcardType w2 = (WildcardType) t2;
			Type[] w1LowerBounds = w1.getLowerBounds();
			Type[] w2LowerBounds = w2.getLowerBounds();
			if (w1LowerBounds.length != w2LowerBounds.length) {
				return false;
			}
			for (int i = 0; i < w1LowerBounds.length; ++i) {
				if (!equals(w1LowerBounds[i], w2LowerBounds[i], binding)) {
					return false;
				}
			}
			Type[] w1UpperBounds = w1.getUpperBounds();
			Type[] w2UpperBounds = w2.getUpperBounds();
			if (w1UpperBounds.length != w2UpperBounds.length) {
				return false;
			}
			for (int i = 0; i < w1UpperBounds.length; ++i) {
				if (!equals(w1UpperBounds[i], w2UpperBounds[i], binding)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
