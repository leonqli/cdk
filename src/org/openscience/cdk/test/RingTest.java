/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2005  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 * 
 */

package org.openscience.cdk.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openscience.cdk.interfaces.Atom;
import org.openscience.cdk.interfaces.AtomContainer;
import org.openscience.cdk.interfaces.Bond;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.Ring;
import org.openscience.cdk.interfaces.ChemObjectBuilder;

/**
 * Checks the funcitonality of the Ring class.
 *
 * @cdk.module test
 *
 * @see org.openscience.cdk.Ring
 */
public class RingTest extends CDKTestCase {

	protected ChemObjectBuilder builder;
	
    public RingTest(String name) {
        super(name);
    }

    public void setUp() {
       	builder = DefaultChemObjectBuilder.getInstance();
    }

    public static Test suite() {
        return new TestSuite(RingTest.class);
    }
    
    public void testRing_int_String() {
        Ring r = builder.newRing(5, "C");
        assertEquals(5, r.getAtomCount());
        assertEquals(5, r.getBondCount());
    }
    
    public void testRing_int() {
        Ring r = builder.newRing(5); // This does not create a ring!
        assertEquals(0, r.getAtomCount());
        assertEquals(0, r.getBondCount());
    }
    
    public void testRing() {
        Ring ring = builder.newRing();
        assertNotNull(ring);
        assertEquals(0, ring.getAtomCount());
        assertEquals(0, ring.getBondCount());
    }

    public void testRing_AtomContainer() {
        AtomContainer container = new org.openscience.cdk.AtomContainer();
        container.addAtom(builder.newAtom("C"));
        container.addAtom(builder.newAtom("C"));
        
        Ring ring = builder.newRing(container);
        assertNotNull(ring);
        assertEquals(2, ring.getAtomCount());
        assertEquals(0, ring.getBondCount());
    }

    public void testGetOrderSum() {
        Ring r = builder.newRing(5, "C");
        assertEquals(5, r.getOrderSum());
    }
    
    public void testGetRingSize() {
        Ring r = builder.newRing(5, "C");
        assertEquals(5, r.getRingSize());
    }
    
    public void testGetNextBond_Bond_Atom() {
        Ring ring = builder.newRing();
        Atom c1 = builder.newAtom("C");
        Atom c2 = builder.newAtom("C");
        Atom c3 = builder.newAtom("C");
        Bond b1 = builder.newBond(c1, c2, 1.0);
        Bond b2 = builder.newBond(c3, c2, 1.0);
        Bond b3 = builder.newBond(c1, c3, 1.0);
        ring.addAtom(c1);
        ring.addAtom(c2);
        ring.addAtom(c3);
        ring.addBond(b1);
        ring.addBond(b2);
        ring.addBond(b3);
        
        assertEquals(b1, ring.getNextBond(b2,c2));
        assertEquals(b1, ring.getNextBond(b3,c1));
        assertEquals(b2, ring.getNextBond(b1,c2));
        assertEquals(b2, ring.getNextBond(b3,c3));
        assertEquals(b3, ring.getNextBond(b1,c1));
        assertEquals(b3, ring.getNextBond(b2,c3));
    }
    
    public void testToString() {
        Ring ring = builder.newRing(5, "C");
        String description = ring.toString();
        for (int i=0; i< description.length(); i++) {
            assertTrue(description.charAt(i) != '\n');
            assertTrue(description.charAt(i) != '\r');
        }
    }
}
