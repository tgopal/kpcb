import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.JUnit4;


import static org.junit.Assert.*;

/*
 * Author: @tgopal - Tejas Gopal, KPCB Engineering Fellow Applicant
 *
 * PrimHashmapTest will serve as the JUnit Test Suite corresponding to the implementation of
 * PrimHashmap, a hashmap using only primitives and one that is fixed in size.
 *
 * Functions tested: constructor(size), set(key, val), get(key), delete(key), load()
 *
 * Execution of these tests can be done by running the PrimHashmapTestRunner class.
 */

public class PrimHashmapTest {

    /**
     * Test #1: Tests the constructor function. First initializes empty PrimHashmaps, and checks 
     * to see if the constructor function, when a size is specified, allocates enough space 
     * for elements (i.e, creating a new PrimHashmap).
     */
    @Test
    public void testConstructor() throws Exception {
        // Initializes empty PrimHashmap objects (size not set, nothing allocated)
        PrimHashmap<String> temp0 = new PrimHashmap<>();
        PrimHashmap<String> temp1 = new PrimHashmap<>();
        PrimHashmap<String> temp2 = new PrimHashmap<>();
        PrimHashmap<String> temp3 = new PrimHashmap<>();

        // Checks to see if for these respective size params, constructor ACTUALLY
        // allocates space for the closest power of 2, but sets max capacity as size.

        @SuppressWarnings("unchecked")
        PrimHashmap<String> emptyHashmap = temp0.constructor(0);
        assertEquals(0, emptyHashmap.getHashmapSize());
        assertEquals(0, emptyHashmap.getTrueSize());

        @SuppressWarnings("unchecked")
        PrimHashmap<String> hashmap1 = temp1.constructor(4);
        assertEquals(4, hashmap1.getHashmapSize());
        assertEquals(4, hashmap1.getTrueSize());

        @SuppressWarnings("unchecked")
        PrimHashmap<String> hashmap2 = temp2.constructor(129);
        assertEquals(129, hashmap2.getHashmapSize());
        assertEquals(256, hashmap2.getTrueSize());

        @SuppressWarnings("unchecked")
        PrimHashmap<String> hashmap3 = temp3.constructor(900);
        assertEquals(900, hashmap3.getHashmapSize());
        assertEquals(1024, hashmap3.getTrueSize());
    }

    /**
     * Test #2: Tests mapping from string->string in a valid, basic case. Will test
     * three values and use the get() function to verify that things were set.
     */
    @Test
    public void testBasicStringSet() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(5);
        hashmap.set("1", "first");
        hashmap.set("2", "second");
        hashmap.set("3", "third");

        assertEquals("first", hashmap.get("1"));
        assertEquals("second", hashmap.get("2"));
        assertEquals("third", hashmap.get("3"));
    }

    /**
     * Test #3: Tests set() function to override values. When a different value is specified
     * for a specific key that already exists in the map, its value should be updated.
     */
    @Test
    public void testOverrideSet() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(5);
        hashmap.set("1", "first");
        hashmap.set("1", "newfirst");
        hashmap.set("2", "second");
        hashmap.set("2", "newsecond");

        assertEquals("newfirst", hashmap.get("1"));
        assertEquals("newsecond", hashmap.get("2"));
    }

    /**
     * Test #4: Tests mapping from string->integer in a valid, basic case. Will test
     * three values and use the get() function to verify that things were set.
     * Ensures that PrimHashmap indeed remains able to map string to arbitary object.
     */
    @Test
    public void testBasicIntegerSet() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(5);
        assertTrue(hashmap.set("1", 1));
        assertTrue(hashmap.set("2", 2));
        assertTrue(hashmap.set("3", 3));

        assertEquals(Integer.valueOf(1), hashmap.get("1"));
        assertEquals(Integer.valueOf(2), hashmap.get("2"));
        assertEquals(Integer.valueOf(3), hashmap.get("3"));
    }

    /**
     * Test #5: Tests set() function when the map has already reached max capacity.
     * In this case, set() should return false and the key-value pair should not be set.
     */
    @Test
    public void testSetWhenFull() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(2);
        assertTrue(hashmap.set("1", 1));
        assertTrue(hashmap.set("2", 2));

        assertFalse(hashmap.set("3", 3));
    }

    /**
     * Test #6: Tests set() function when the map is full, but the user wants to overwrite
     * the value of a key. In this case, the set() function should NOT return false and
     * should override the value.
     */
    @Test
    public void testSetWhenFullDuplicate() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(2);
        assertTrue(hashmap.set("1", 1));
        assertTrue(hashmap.set("2", 2));

        assertTrue(hashmap.set("2", 3));
        assertEquals(Integer.valueOf(3), hashmap.get("2"));
    }

    /**
     * Test #7: Tests set() function's ability to perform in high capacity. Also,
     * ensures that values past max capacity cannot be set.
     */
    @Test
    public void testLoadSet() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(10000);
        for (int i = 0; i < 10000; i++) {
            hashmap.set("" + i, i);
        }

        assertFalse(hashmap.set("10000", 10000));
    }

    /**
     * Test #8: Tests set() function's behavior when two strings have the
     * same hashcode. The map handles collisions via linkedlist -> ensures
     * that only one index in the underlying table array is set.
     */
    @Test
    public void testSetWithCollisions() throws Exception {
        // These two strings have the same hashCode()!
        String str1 = "FB";
        String str2 = "Ea";

        PrimHashmap<String> hashmap = new PrimHashmap<>(5);
        hashmap.set(str1, "aaa");
        hashmap.set(str2, "aaa");

        // Ensure only one index in the table is occupied
        Object[] table = hashmap.getTable();
        int count = 0;
        int index = findHashIndex(str1.hashCode(), table.length);
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                count++;
            }
        }

        assertEquals(count, 1);
        assertEquals("aaa", hashmap.get(str1));
        assertEquals("aaa", hashmap.get(str2));
    }

    /**
     * Test #9: Tests set() function when no space has been allocated for elements.
     * In any case, set() should continue returning false.
     */
    @Test
    public void testSetNonexistentMap() throws Exception {
        PrimHashmap<Double> hashmap = new PrimHashmap<>();
        assertFalse(hashmap.set("can't", 1.0));
        assertFalse(hashmap.set("do", 1.1));
        assertFalse(hashmap.set("anything", 1.2));
    }

    /**
     * Test #10: Tests get() function when no space has been allocated for elements.
     * In any case, get() should continue returning null.
     */
    @Test
    public void testGetWhenEmpty() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(3);
        assertEquals(null, hashmap.get("can't"));
        assertEquals(null, hashmap.get("do"));
        assertEquals(null, hashmap.get("anything"));
    }

    /**
     * Test #11: Tests get() function under normal conditions with no duplicates.
     * Should return valid values given keys.
     */
    @Test
    public void testValidGet() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(2);
        assertTrue(hashmap.set("abc", 1));
        assertTrue(hashmap.set("def", 2));

        assertEquals(Integer.valueOf(1), hashmap.get("abc"));
        assertEquals(Integer.valueOf(2), hashmap.get("def"));
    }

    /**
     * Test #12: Tests get() function when there is no associated key 
     * present in the PrimHashmap. get() should return null.
     */
    @Test
    public void testInvalidGet() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(2);
        assertTrue(hashmap.set("a", "aaa"));
        assertEquals(null, hashmap.get("e"));
    }

    /**
     * Test #13: Tests get() function after a key's value has been overriden by
     * the set() function. Should return the updated value.
     */
    @Test
    public void testGetAfterOverride() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(4);
        assertTrue(hashmap.set("a", "aaa"));
        assertTrue(hashmap.set("b", "bbb"));

        assertEquals("aaa", hashmap.get("a"));
        assertEquals("bbb", hashmap.get("b"));

        hashmap.set("a", "bbb");
        hashmap.set("b", "aaa");

        assertEquals("bbb", hashmap.get("a"));
        assertEquals("aaa", hashmap.get("b"));
    }

    /**
     * Test #14: Tests delete() function basic funcitonality under
     * normal conditions. Ensures get()ing the values after deletion
     * returns false.
     */
    @Test
    public void testBasicDelete() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(4);
        assertTrue(hashmap.set("a", "aaa"));
        assertTrue(hashmap.set("b", "bbb"));
        assertEquals(2, hashmap.getNumItems());
        assertEquals("bbb", hashmap.delete("b"));
        assertEquals("aaa", hashmap.delete("a"));
        assertEquals(null, hashmap.get("a"));
        assertEquals(null, hashmap.get("b"));
        assertEquals(0, hashmap.getNumItems());
    }

    /**
     * Test #15: Tests delete() function after a value has been updated 
     * with the set() function. Upon deletion, should return the updated
     * value and then return null if called one more time.
     */
    @Test
    public void testDeleteAfterOverride() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>(4);
        assertTrue(hashmap.set("a", "aaa"));
        assertTrue(hashmap.set("a", "bbb"));
        assertEquals(1, hashmap.getNumItems());

        assertEquals("bbb", hashmap.delete("a"));
        assertEquals(0, hashmap.getNumItems());
        assertEquals(null, hashmap.get("a"));
    }

    /**
     * Test #16: Tests delete() on a PrimHashmap with no allocated space.
     * Should return null in any case.
     */
    @Test
    public void testDeleteNonexistentMap() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>();
        assertEquals(null, hashmap.delete("can't"));
        assertEquals(null, hashmap.delete("dp"));
        assertEquals(null, hashmap.delete("anything"));
        assertEquals(0, hashmap.getNumItems());
    }

    /**
     * Test #17: Tests load() function on a PrimHashmap with no allocated space.
     * Should return 0 in any case.
     */
    @Test
    public void testLoadNonexistentMap() throws Exception {
        PrimHashmap<String> hashmap = new PrimHashmap<>();
        assertEquals(0, hashmap.load(), 0);
    }

    /**
     * Test #18: Tests load() function under basic, normal condtions.
     */
    @Test
    public void testBasicLoad() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(5);
        hashmap.set("a", 1);
        hashmap.set("b", 2);
        hashmap.set("c", 3);

        assertEquals(0.6, hashmap.load(), 0.01);
        hashmap.set("d", 4);
        assertEquals(0.8, hashmap.load(), 0.01);
    }

    /**
     * Test #19: Tests load() function after a value has been overriden. Load
     * factor should remain the same, since numElements remains the same.
     */
    @Test
    public void testLoadAfterOverride() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(5);
        hashmap.set("a", 1);
        hashmap.set("b", 2);
        hashmap.set("c", 3);

        assertEquals(0.6, hashmap.load(), 0.01);
        hashmap.set("c", 4);
        assertEquals(0.6, hashmap.load(), 0.01);
    }

    /**
     * Test #20: Tests load() function when table is full, should return 1.
     */
    @Test
    public void testLoadWhenFull() throws Exception {
        PrimHashmap<Integer> hashmap = new PrimHashmap<>(3);
        hashmap.set("a", 1);
        hashmap.set("b", 2);
        hashmap.set("c", 3);

        // Load factor should be exactly equal to 1
        assertEquals(1.0, hashmap.load(), 0);
    }

    /**
     * Helper function findHashIndex will mock hashing, just as it is done
     * within the PrimHashmap definition for test cases.
     *
     * @param hashVal 
     *      The string key's hashCode() value
     * @param size
     *      The size of the PrimHashmap underlying table
     *
     * @return index (int) that this key has been hashed to in table.
     */
    private int findHashIndex(int hashVal, int size) {
        return hashVal & (size-1);
    }
}
