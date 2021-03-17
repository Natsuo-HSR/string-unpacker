package test;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static main.jp.nat.StringGenerator.*;


class StringGeneratorTest {

    @Test
    void unpackTest() {
        // valid tests
        assertEquals("xyz", unpack("xyz"));
        assertEquals("xhtxhtxhtxhtz", unpack("4[xht]z"));
        assertEquals("xyzxyzxyzxyxyxyxyz", unpack("3[xyz]4[xy]z"));
        assertEquals("xxxyxxxyxyxyxyxy", unpack("2[3[x]y]4[xy]"));
        assertEquals("xxzxxzxxzyxxzxxzxxzyxyxyxyxy", unpack("2[3[2[x]z]y]4[xy]"));

        // not valid tests
        assertEquals("Not valid", unpack("3[33[x]!y]4[xy]"));
        assertEquals("Not valid", unpack("a[xyz]4[xy]z"));
        assertEquals("Not valid", unpack("3[xyz]4[xy]z]"));
    }
}