/*=============================================================================
 *		  File:	PreferenceInitializer.java
 *		Author: Kevin Cooper (kevincooper@email.arizona.edu)
 * Description: 
 *
 *===========================================================================*/

package com.kevlarr.easyexport.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.kevlarr.easyexport.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_PATH, System.getProperty("user.home") + "/Desktop");
		store.setDefault(PreferenceConstants.P_STRING, "SectionLeader_FirsLast");
	}

}
