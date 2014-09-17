/*=============================================================================
 *		  File:	Rename.java
 *		Author: Kevin Cooper (kevincooper@email.arizona.edu)
 * Description: 
 *
 *===========================================================================*/

package com.kevlarr.easyexport.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;

public class Rename {
	private String oldName, newName;
	private NullProgressMonitor prog;
	
	public Rename(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
		prog = new NullProgressMonitor();
	}
	
	public void applyNewName() {
		change(oldName, newName);
	}
	
	public void revertToOldName() {
		change(newName, oldName);
	}
	
	private void change(String start, String finish) {
		IProject theProject = ResourcesPlugin.getWorkspace().getRoot().getProject(start);
		RenameResourceChange change = new RenameResourceChange(theProject.getFullPath(),finish);
		try {
			change.perform(prog);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
