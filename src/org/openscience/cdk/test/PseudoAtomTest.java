/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 2003-2004  The Chemistry Development Kit (CDK) project
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 * 
 */
package org.openscience.cdk.test;

import org.openscience.cdk.*;
import org.openscience.cdk.tools.*;
import java.util.*;
import junit.framework.*;
import javax.vecmath.*;

/**
 * Checks the functionality of the AtomTypeFactory
 */
public class PseudoAtomTest extends TestCase {

    public PseudoAtomTest(String name) {
        super(name);
    }

    public void setUp() {}

    public static Test suite() {
        return new TestSuite(PseudoAtomTest.class);
    }

    public void testPseudoAtom() {
        String label = "Arg255";
        PseudoAtom a = new PseudoAtom(label);
        assertEquals("R", a.getSymbol());
        assertEquals(label, a.getLabel());
        assertNull(a.getPoint3D());
        assertNull(a.getPoint2D());
        assertNull(a.getFractionalPoint3D());
    }

    public void testPseudoAtom_Atom() {
        Atom atom = new Atom("C");
        Point3d fract = new Point3d(0.5, 0.5, 0.5);
        Point3d threeD = new Point3d(0.5, 0.5, 0.5);
        Point2d twoD = new Point2d(0.5, 0.5);
        atom.setFractionalPoint3D(fract);
        atom.setPoint3D(threeD);
        atom.setPoint2D(twoD);
        
        PseudoAtom a = new PseudoAtom(atom);
        assertEquals(fract, a.getFractionalPoint3D());
        assertEquals(threeD, a.getPoint3D());
        assertEquals(twoD, a.getPoint2D());
    }

    /**
     * Method to test wether the class complies with RFC #9.
     */
    public void testToString() {
        Atom atom = new PseudoAtom("R");
        String description = atom.toString();
        for (int i=0; i< description.length(); i++) {
            assertTrue(description.charAt(i) != '\n');
            assertTrue(description.charAt(i) != '\r');
        }
    }
}
