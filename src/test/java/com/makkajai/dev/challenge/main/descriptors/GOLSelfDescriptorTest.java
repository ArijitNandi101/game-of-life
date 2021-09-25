//package com.makkajai.dev.challenge.main.descriptors;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class GOLSelfDescriptorTest {
//
//    @Test
//    void testConstructor() {
//        GOLDescriptor desc = new GOLSelfDescriptor(10);
//        assertEquals(10, desc.getValue());
//    }
//
//     @Test
//     void testUpdate() {
//
//         GOLDescriptor desc = new GOLSelfDescriptor(0);
//         desc.update();
//         assertEquals(1, desc.getValue());
//
//         desc = new GOLSelfDescriptor(1);
//         desc.update();
//         assertEquals(6, desc.getValue());
//
//         desc = new GOLSelfDescriptor(2);
//         desc.update();
//         assertEquals(7, desc.getValue());
//
//
//         desc = new GOLSelfDescriptor(3);
//         desc.update();
//         assertEquals(0xB, desc.getValue());
//
//         desc = new GOLSelfDescriptor(8);
//         desc.update();
//         assertEquals(9, desc.getValue());
//
//         desc = new GOLSelfDescriptor(9);
//         desc.update();
//         assertEquals(0xE, desc.getValue());
//
//         desc = new GOLSelfDescriptor(0xA);
//         desc.update();
//         assertEquals(0xF, desc.getValue());
//
//         desc = new GOLSelfDescriptor(0xB);
//         desc.update();
//         assertEquals(0xB, desc.getValue());
//
//         desc = new GOLSelfDescriptor(0xF);
//         desc.update();
//         assertEquals(0xB, desc.getValue());
//     }
//}
