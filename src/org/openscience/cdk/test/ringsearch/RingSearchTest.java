/*  $RCSfile$
 *  $Author$
 *  $Date$
 *  $Revision$
 *
 *  Copyright (C) 2003-2005  The Chemistry Development Kit (CDK) project
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.openscience.cdk.test.ringsearch;

import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openscience.cdk.ChemObject;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.Ring;
import org.openscience.cdk.RingSet;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.ringsearch.SSSRFinder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.templates.MoleculeFactory;
import org.openscience.cdk.tools.LoggingTool;
import org.openscience.cdk.test.CDKTestCase;

/**
 * This class tests the SSSRFinder class.
 *
 * @cdk.module test
 *
 * @author     steinbeck
 * @cdk.created    2003-10-17
 */
public class RingSearchTest extends CDKTestCase
{

	static boolean standAlone = false;
	private LoggingTool logger = null;


	/**
	 *  Constructor for the RingSearchTest object
	 *
	 *@param  name  Description of the Parameter
	 */
	public RingSearchTest(String name)
	{
		super(name);
	}


	/**
	 *  The JUnit setup method
	 */
	public void setUp() throws Exception {
        super.setUp();
		logger = new LoggingTool(this);
	}


	/**
	 *  A unit test suite for JUnit
	 *
	 *@return    The test suite
	 */
	public static Test suite()
	{
		return new TestSuite(RingSearchTest.class);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testAlphaPinene()
	{
		Molecule molecule = MoleculeFactory.makeAlphaPinene();
		SSSRFinder sssrf = new SSSRFinder(molecule);

		RingSet ringSet = sssrf.findSSSR();
		assertEquals(2, ringSet.size());
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  Exception  Description of the Exception
	 */
	public void testBenzene() throws Exception
	{
		SmilesParser sp = new SmilesParser();
		Molecule molecule = sp.parseSmiles("c1ccccc1");
		SSSRFinder sssrf = new SSSRFinder(molecule);
		RingSet ringSet = sssrf.findSSSR();
		assertEquals(1, ringSet.size());
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  Exception  Description of the Exception
	 */
	public void testBicyclicCompound() throws Exception
	{
		SmilesParser sp = new SmilesParser();
		Molecule molecule = sp.parseSmiles("C1CCC(CCCCC2)C2C1");
		SSSRFinder sssrf = new SSSRFinder(molecule);
		RingSet ringSet = sssrf.findSSSR();
		assertEquals(2, ringSet.size());
	}

	public void testSFBug826942() throws Exception
	{
		SmilesParser sp = new SmilesParser();
		Molecule molecule = sp.parseSmiles("C1CCC2C(C1)C4CCC3(CCCCC23)(C4)");
		SSSRFinder sssrf = new SSSRFinder(molecule);
		RingSet ringSet = sssrf.findSSSR();
		assertEquals(4, ringSet.size());
	}

	/**
	 *  A unit test for JUnit
	 */
	public void testProblem1()
	{
		Molecule molecule = null;
		Ring ring = null;
		try
		{
			String filename = "data/mdl/figueras-test-sep3D.mol";
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
			MDLReader reader = new MDLReader(new InputStreamReader(ins));
			molecule = (Molecule) reader.read((ChemObject) new Molecule());
			if (standAlone) System.out.println("Testing " + filename);
			
			SSSRFinder sssrf = new SSSRFinder(molecule);
			RingSet ringSet = sssrf.findSSSR();
			if (standAlone) System.out.println("Found ring set of size: " + ringSet.size());
			assertEquals(3, ringSet.size());
			for (int f = 0; f < ringSet.size(); f++)
			{
				ring = (Ring) ringSet.elementAt(f);
				if (standAlone) System.out.println("ring: " + toString(ring, molecule));
			}
		} catch (Exception exc)
		{
			exc.printStackTrace();
			fail();
		}
	}


	/**
	 *  A unit test for JUnit
	 */
	public void testProblem2()
	{
		Molecule molecule = null;
		Ring ring = null;
		try
		{
			String filename = "data/mdl/figueras-test-buried.mol";
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
			MDLReader reader = new MDLReader(new InputStreamReader(ins));
			molecule = (Molecule) reader.read((ChemObject) new Molecule());
			if (standAlone) System.out.println("Testing " + filename);
			
			SSSRFinder sssrf = new SSSRFinder(molecule);
			RingSet ringSet = sssrf.findSSSR();
			if (standAlone) System.out.println("Found ring set of size: " + ringSet.size());
			assertEquals(10, ringSet.size());
			for (int f = 0; f < ringSet.size(); f++)
			{
				ring = (Ring) ringSet.elementAt(f);
				if (standAlone) System.out.println("ring: " + toString(ring, molecule));
			}
		} catch (Exception exc)
		{
			exc.printStackTrace();
			fail();
		}
	}

	
	/**
	 *  A unit test for JUnit
	 */
	public void testProblem3()
	{
		Molecule molecule = null;
		Ring ring = null;
		try
		{
			String filename = "data/mdl/figueras-test-inring.mol";
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
			MDLReader reader = new MDLReader(new InputStreamReader(ins));
			molecule = (Molecule) reader.read((ChemObject) new Molecule());
			if (standAlone) System.out.println("Testing " + filename);
			
			SSSRFinder sssrf = new SSSRFinder(molecule);
			RingSet ringSet = sssrf.findSSSR();
			if (standAlone) System.out.println("Found ring set of size: " + ringSet.size());
			assertEquals(5, ringSet.size());
			for (int f = 0; f < ringSet.size(); f++)
			{
				ring = (Ring) ringSet.elementAt(f);
				if (standAlone) System.out.println("ring: " + toString(ring, molecule));
			}
		} catch (Exception exc)
		{
			exc.printStackTrace();
			fail();
		}
	}
	
	public void testBug891021() {
		Molecule molecule = null;
		Ring ring = null;
		try {
			String filename = "data/mdl/too.many.rings.mol";
			InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
			MDLReader reader = new MDLReader(new InputStreamReader(ins));
			molecule = (Molecule) reader.read((ChemObject) new Molecule());
			if (standAlone) System.out.println("Testing " + filename);
			
			SSSRFinder sssrf = new SSSRFinder(molecule);
			RingSet ringSet = sssrf.findSSSR();
			if (standAlone) System.out.println("Found ring set of size: " + ringSet.size());
		} catch (Exception exc) {
			exc.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 *  The main program for the RingSearchTest class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args)
	{
		RingSearchTest rst = new RingSearchTest("RingSearchTest");
		standAlone = true;
        try {
            rst.setUp();
            rst.testProblem1();
            rst.testProblem2();
            rst.testProblem3();
        } catch(Exception exc) {
            System.err.println("Could setup the TestCase");
        }
	}

	 /**
	  * Convenience method for giving a string representation 
	  * of this ring based on the number of the atom in a given 
	  * molecule.
      *
	  * @param molecule  A molecule to determine an atom number for each ring atom
      * @return          string representation of this ring
	  */
	public String toString(Ring ring, Molecule molecule)
	{
		String str = "";
		for (int f = 0; f < ring.getAtomCount(); f++)
		{
			try
			{
				str += molecule.getAtomNumber(ring.getAtomAt(f)) +  " - ";
			}
			catch(Exception exc)
			{
			    System.err.println("Could not create string representation of Ring: " + exc.getMessage());
			}
		}
		return str;
	}
	
}

