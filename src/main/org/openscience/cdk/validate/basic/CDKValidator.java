/* Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
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
 */
package org.openscience.cdk.validate.basic;

import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemSequence;
import org.openscience.cdk.validate.AbstractValidator;
import org.openscience.cdk.validate.ValidationReport;
import org.openscience.cdk.validate.ValidationTest;
import org.openscience.cdk.validate.AbstractValidationTestType;

/**
 * This Validator tests the internal datastructures, and
 * tries to detect inconsistencies in it.
 *
 * @author   Egon Willighagen
 * @cdk.githash
 * @cdk.created  2003-08-22
 * @cdk.module   valid
 */ 
public class CDKValidator extends AbstractValidator {

    private final static AbstractValidationTestType NULL_CHEMSEQUENCE =
        new AbstractValidationTestType(
            "ChemFile contains a null ChemSequence."
        ) {};
    private final static AbstractValidationTestType NULL_CHEMMODEL =
        new AbstractValidationTestType(
            "ChemSequence contains a null ChemModel."
        ) {};

    public CDKValidator() {
    }
    
    public ValidationReport validateChemFile(IChemFile subject) {
        return validateChemFileNulls(subject);
    }
    public ValidationReport validateChemSequence(IChemSequence subject) {
        return validateChemSequenceNulls(subject);
    }

    private ValidationReport validateChemFileNulls(IChemFile chemFile) {
        ValidationReport report = new ValidationReport();
        ValidationTest hasNulls = new ValidationTest(NULL_CHEMSEQUENCE, chemFile);
        for (int i=0; i < chemFile.getChemSequenceCount(); i++) { // DIRTY !!!! FIXME !!!!!
            // but it does not seem to work on 1.4.2 otherwise....
            if (chemFile.getChemSequence(i) == null) {
                report.addError(hasNulls);
            } else {
                report.addOK(hasNulls);
            }
        }
        return report;
    }
        
    private ValidationReport validateChemSequenceNulls(IChemSequence sequence) {
        ValidationReport report = new ValidationReport();
        ValidationTest hasNulls = new ValidationTest(NULL_CHEMMODEL, sequence);
        for (int i=0; i < sequence.getChemModelCount(); i++) { // DIRTY !!!! FIXME !!!!!
            // but it does not seem to work on 1.4.2 otherwise....
            if (sequence.getChemModel(i) == null) {
                report.addError(hasNulls);
            } else {
                report.addOK(hasNulls);
            }
        }
        return report;
    }

}