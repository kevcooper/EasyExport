/*=============================================================================
 *		  File:	EEPreferencePage.java
 *		Author: Kevin Cooper (kevincooper@email.arizona.edu)
 * Description: 
 *
 *===========================================================================*/

package com.kevlarr.easyexport.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.kevlarr.easyexport.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class EEPreferencePage extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public EEPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("-Choose the name of your archive and where you want it to go\n-The .zip extension is added automatically.\n-You must change the default archive name before you can export.");
		//setTitle("EasyExport Settings");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"Export to &directory:", getFieldEditorParent()));
		
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "Archive project name:", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}