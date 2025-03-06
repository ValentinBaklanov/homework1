package util;

public class TestClassBadHash {
    private int val;

    public TestClassBadHash(int val) {
        this.val = val;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClassBadHash testClassBadHash = (TestClassBadHash) o;
        return val == testClassBadHash.val;
    }


    @Override
    public String toString() {
        return "TestClassBadHash{" +
                "val=" + val +
                '}';
    }
}
