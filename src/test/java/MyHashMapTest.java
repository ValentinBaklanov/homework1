
import org.junit.jupiter.api.Test;
import util.TestClassBadHash;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MyHashMapTest {

    @Test
    void addTenThousandKeysAndValues() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(String.valueOf(i), i);
        }

        assertThat(10000).isEqualTo(myHashMap.size());
        assertThat(10000).isEqualTo(myHashMap.keySet().size());
        assertThat(10000).isEqualTo(myHashMap.values().size());
        assertThat(10000).isEqualTo(myHashMap.entrySet().size());

    }

    @Test
    void addTenThousandWithCollisions() {
        MyHashMap<TestClassBadHash, Integer> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(new TestClassBadHash(i), i);
        }

        assertThat(10000).isEqualTo(myHashMap.size());
        assertThat(10000).isEqualTo(myHashMap.keySet().size());
        assertThat(10000).isEqualTo(myHashMap.values().size());
        assertThat(10000).isEqualTo(myHashMap.entrySet().size());
    }

    @Test
    void addAndGetTenThousand() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(String.valueOf(i), i);
        }

        for (int i = 0; i < 10000; i++) {
            Integer expected = myHashMap.get(String.valueOf(i));
            assertThat(i).isEqualTo(expected);
        }
    }

    @Test
    void addAndGetTenThousandWithCollisions() {
        MyHashMap<TestClassBadHash, Integer> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(new TestClassBadHash(i), i);
        }

        for (int i = 0; i < 10000; i++) {
            Integer expected = myHashMap.get(new TestClassBadHash(i));
            assertThat(i).isEqualTo(expected);
        }
    }

    @Test
    void addTenThousandAndDeleteFiveThousands() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>(5000);

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(String.valueOf(i), i);
        }

        for (int i = 0; i < 5000; i++) {
            myHashMap.delete(String.valueOf(i));
        }

        for (int i = 0; i < 5000; i++) {
            assertNull(myHashMap.get(String.valueOf(i)));
        }

        for (int i = 5000; i < 10000; i++) {
            assertThat(i).isEqualTo(myHashMap.get(String.valueOf(i)));
        }

        assertThat(5000).isEqualTo(myHashMap.size());
        assertThat(5000).isEqualTo(myHashMap.keySet().size());
        assertThat(5000).isEqualTo(myHashMap.values().size());
        assertThat(5000).isEqualTo(myHashMap.entrySet().size());


    }

    @Test
    void addTenThousandAndDeleteFiveThousandsWithCollisions() {
        MyHashMap<TestClassBadHash, Integer> myHashMap = new MyHashMap<>();

        for (int i = 0; i < 10000; i++) {
            myHashMap.put(new TestClassBadHash(i), i);
        }

        for (int i = 0; i < 5000; i++) {
            myHashMap.delete(new TestClassBadHash(i));
        }

        for (int i = 0; i < 5000; i++) {
            assertNull(myHashMap.get(new TestClassBadHash(i)));
        }

        for (int i = 5000; i < 10000; i++) {
            assertThat(i).isEqualTo(myHashMap.get(new TestClassBadHash(i)));
        }

        assertThat(5000).isEqualTo(myHashMap.size());
        assertThat(5000).isEqualTo(myHashMap.keySet().size());
        assertThat(5000).isEqualTo(myHashMap.values().size());
        assertThat(5000).isEqualTo(myHashMap.entrySet().size());

    }

    @Test
    void getEntryKeyAndValue() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();

        myHashMap.put("Ivan", 23);
        myHashMap.put("Sergey", 22);
        myHashMap.put("Petr", 21);

        Set<String> keySet = myHashMap.keySet();

        Set<MyHashMap.MyEntry<String, Integer>> myEntries = myHashMap.entrySet();
        List<Integer> values = myHashMap.values();

        for (MyHashMap.MyEntry<String, Integer> myEntry : myEntries) {
            assertTrue(keySet.contains(myEntry.getKey()));
            assertTrue(values.contains(myEntry.getValue()));
        }

    }

}
