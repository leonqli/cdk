/* Copyright (C) 2001-2007  Christoph Steinbeck <steinbeck@users.sf.net>
 *                    2013  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IIsotope;

/**
 * List of isotopes. Data is taken from the Blue Obelisk Data Repository,
 * version 10.
 * 
 * @author      egonw
 * @cdk.module  core 
 * @cdk.githash
 */
public class BODRIsotopes extends IsotopeFactory {

	private static BODRIsotopes myself = null;

	public static BODRIsotopes getInstance() throws IOException {
		if (myself == null) myself = new BODRIsotopes();
		return myself;
	}

	private BODRIsotopes() throws IOException {
		String configFile = "org/openscience/cdk/config/data/isotopes.txt";
		isotopes = new ArrayList<IIsotope>();
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(configFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
		String line = reader.readLine();
		while (line != null) {
			String[] fields = line.split(",");
			if (!fields[0].trim().isEmpty()) {
				IIsotope isotope = new BODRIsotope(
					fields[0].trim().intern(),
					Integer.valueOf(fields[1].trim()),
					Integer.valueOf(fields[2].trim()),
					Double.valueOf(fields[3].trim()),
					Double.valueOf(fields[4].trim())
				);
				isotopes.add(isotope);
			}
			line = reader.readLine();
		}
        majorIsotopes = new HashMap<String, IIsotope>();
	}

	/**
	 * Gets an array of all isotopes known to the IsotopeFactory for the given
	 * element symbol.
	 *
	 *@param  symbol  An element symbol to search for
	 *@return         An array of isotopes that matches the given element symbol
	 */
    @TestMethod("testGetIsotopes_String")
    public IIsotope[] getIsotopes(String symbol) {
        ArrayList<IIsotope> list = new ArrayList<IIsotope>();
        for (IIsotope isotope : isotopes) {
            if (isotope.getSymbol().equals(symbol)) {
            	list.add(isotope);
            }
        }
        return list.toArray(new IIsotope[list.size()]);
    }

    /**
	 * Gets a array of all isotopes known to the IsotopeFactory.
	 *
	 * @return         An array of all isotopes
	 */
    @TestMethod("testGetIsotopes")
    public IIsotope[] getIsotopes() {
    	return isotopes.toArray(new IIsotope[isotopes.size()]);
    }

    /**
	 * Gets an array of all isotopes matching the searched exact mass within
	 * a certain difference.
	 *
	 * @param  exactMass  search mass
	 * @param  difference mass the isotope is allowed to differ from the search mass
	 * @return            An array of all isotopes
	 */
    @TestMethod("testGetIsotopes_double_double")
    public IIsotope[] getIsotopes(double exactMass, double difference) {
    	ArrayList<IIsotope> list = new ArrayList<IIsotope>();
    	for (IIsotope isotope : isotopes) {
    		if (Math.abs(isotope.getExactMass() - exactMass) <= difference) {
    			list.add(isotope);
    		}
    	}
    	return list.toArray(new IIsotope[list.size()]);
    }
    
    /**
     * Get isotope based on element symbol and mass number.
     *
     * @param symbol the element symbol
     * @param massNumber the mass number
     * @return the corresponding isotope
     */
    @TestMethod("testGetIsotope")
    public IIsotope getIsotope(String symbol, int massNumber) {
        for (IIsotope isotope : isotopes) {
            if (isotope.getSymbol().equals(symbol) && isotope.getMassNumber() == massNumber) {
                return isotope;
            }
        }
        return null;
    }

    /**
     * Get an isotope based on the element symbol and exact mass.
     *
     * @param symbol    the element symbol
     * @param exactMass the mass number
     * @param tolerance allowed difference from provided exact mass
     * @return the corresponding isotope
     */
    @TestMethod("testGetIsotopeFromExactMass")
    public IIsotope getIsotope(String symbol, double exactMass, double tolerance) {
        IIsotope ret     = null;
        double   minDiff = Double.MAX_VALUE;
        for (IIsotope isotope : isotopes) {
            double diff = Math.abs(isotope.getExactMass() - exactMass);
            if (isotope.getSymbol().equals(symbol) &&
            	diff <= tolerance && diff < minDiff) {
            	ret = isotope;
            	minDiff = diff;
            }
        }
        return ret;
    }

    /**
	 * Returns the most abundant (major) isotope with a given atomic number.
     *
     * <p>The isotope's abundancy is for atoms with atomic number 60 and smaller
     * defined as a number that is proportional to the 100 of the most abundant
     * isotope. For atoms with higher atomic numbers, the abundancy is defined
     * as a percentage.
	 *
	 * @param  atomicNumber  The atomicNumber for which an isotope is to be returned
	 * @return               The isotope corresponding to the given atomic number
     *
     * @see #getMajorIsotope(String symbol)
     */
    @TestMethod("testGetMajorIsotope_int")
    public IIsotope getMajorIsotope(int atomicNumber) {
        IIsotope major = null;
        for (IIsotope isotope : isotopes) {
            if (isotope.getAtomicNumber() == atomicNumber) {
            	if (major == null||
            		isotope.getNaturalAbundance() > major.getNaturalAbundance()) {
            		major = isotope;
            	}
            }
        }
        if (major == null) logger.error("Could not find major isotope for: ", atomicNumber);
        return major;
    }

    /**
     *  Returns the most abundant (major) isotope whose symbol equals element.
     *
     *@param  symbol  the symbol of the element in question
     *@return         The Major Isotope value
     */
    @TestMethod("testGetMajorIsotope_String")
    public IIsotope getMajorIsotope(String symbol) {
        IIsotope major = null;
        if (majorIsotopes.containsKey(symbol)) {
            major = majorIsotopes.get(symbol);
        } else {
            for (IIsotope isotope : isotopes) {
                if (isotope.getSymbol().equals(symbol)) {
                	if (major == null ||
                		isotope.getNaturalAbundance() > major.getNaturalAbundance()) {
                		major = isotope;
                	}
                }
            }
            if (major == null) {
                logger.error("Could not find major isotope for: ", symbol);
            } else {
                majorIsotopes.put(symbol, major);
            }
        }
        return major;
    }

}
