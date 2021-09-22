package com.makkajai.dev.challenge.main.descriptors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GOLNeighbourDescriptorTest {

    @Test
    void testConstructor() {
        GOLDescriptor desc = new GOLNeighbourDescriptor(10);
        assertEquals(10, desc.getValue());
    }

     @Test
     void testUpdateNeighbourDescriptor() {

         GOLNeighbourDescriptor desc = new GOLNeighbourDescriptor(0);
         desc.update();
         assertEquals(1, desc.getValue());

         desc = new GOLNeighbourDescriptor(1);
         desc.update();
         assertEquals(2, desc.getValue());

         desc = new GOLNeighbourDescriptor(2);
         desc.update();
         assertEquals(7, desc.getValue());

         desc = new GOLNeighbourDescriptor(3);
         desc.update();
         assertEquals(3, desc.getValue());

         desc = new GOLNeighbourDescriptor(8);
         desc.update();
         assertEquals(9, desc.getValue());

         desc = new GOLNeighbourDescriptor(9);
         desc.update();
         assertEquals(0xA, desc.getValue());

         desc = new GOLNeighbourDescriptor(0xA);
         desc.update();
         assertEquals(7, desc.getValue());

         desc = new GOLNeighbourDescriptor(0xB);
         desc.update();
         assertEquals(3, desc.getValue());

         desc = new GOLNeighbourDescriptor(0xF);
         desc.update();
         assertEquals(3, desc.getValue());
     }
}
