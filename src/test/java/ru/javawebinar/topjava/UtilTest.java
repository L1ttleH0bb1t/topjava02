package ru.javawebinar.topjava;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * GKislin
 * 12.04.2015.
 */
public class UtilTest {

    private static final List<Integer> ARRAY_LIST = Arrays.asList(1, 2, 3, 4, 5);
    private static final List<Integer> LIST = new LinkedList<>(ARRAY_LIST);
    private static final List<Integer> UNMODIFIABLE_LIST = Collections.unmodifiableList(LIST);

    @Test(expected = UnsupportedOperationException.class)
    public void testArrayListModify() throws Exception {
        ARRAY_LIST.add(6);
    }

    @Test
    public void testListModify() throws Exception {
        LIST.add(6);
    }
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableListModify() throws Exception {
        UNMODIFIABLE_LIST.add(6);
    }
}