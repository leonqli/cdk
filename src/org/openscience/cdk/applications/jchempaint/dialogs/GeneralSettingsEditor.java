/* $RCSfile$   
 * $Author$   
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 2004-2005  The Chemistry Development Kit (CDK) project
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
 */
package org.openscience.cdk.applications.jchempaint.dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.openscience.cdk.Atom;
import org.openscience.cdk.ChemObject;
import org.openscience.cdk.applications.swing.FieldTablePanel;
import org.openscience.cdk.applications.jchempaint.*;

/**
 * JFrame that allows setting of a number of general application options.
 * 
 * @cdk.module jchempaint.application
 */
public class GeneralSettingsEditor extends FieldTablePanel implements ActionListener {
    
    private JCheckBox askForIOSettings;
    
    private JFrame frame;
    
    public GeneralSettingsEditor(JFrame frame) {
        super();
        this.frame = frame;
        constructPanel();
    }
    
    private void constructPanel() {
        askForIOSettings = new JCheckBox();
        addField("Ask for IO settings", askForIOSettings);
    }
    
    public void setSettings() {
        Properties props = JCPPropertyHandler.getInstance().getJCPProperties();
        askForIOSettings.setSelected(props.getProperty("askForIOSettings", "true").equals("true"));
        validate();
    }

    public void applyChanges() {
        Properties props = JCPPropertyHandler.getInstance().getJCPProperties();
        props.setProperty("askForIOSettings",
            askForIOSettings.isSelected() ? "true" : "false"
        );
    }
    
    /**
     * Required by the ActionListener interface.
     */
    public void actionPerformed(ActionEvent e) {
        // nothing to do whatsoever
    }
}


