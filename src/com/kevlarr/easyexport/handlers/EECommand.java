/*=============================================================================
 *		  File:	EECommand.java
 *		Author: Kevin Cooper (kevincooper@email.arizona.edu)
 * Description: 
 *
 *===========================================================================*/

package com.kevlarr.easyexport.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;

import com.kevlarr.easyexport.Activator;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class EECommand extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public EECommand() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
//		MessageDialog.openInformation(
//				window.getShell(),
//				"EasyExport",
//				"Hello, Eclipse world");

		if (prefStore.isDefault("stringPreference")) {
			PreferenceDialog pref = PreferencesUtil.createPreferenceDialogOn(null, 
					"com.kevlarr.easyexport.preferences.EEPreferencePage", null, null);
			pref.open();
		} else if(window.getWorkbench().saveAllEditors(true)){
			String archiveName = prefStore.getString("stringPreference");
			if(archiveName.toLowerCase().indexOf(".zip") > 0){
				archiveName = archiveName.substring(0,archiveName.toLowerCase().indexOf(".zip"));
				prefStore.setValue("stringPreference", archiveName);
			}
			IProject theProject = getCurrentProject(window);
			Rename nameChanger = new Rename(theProject.getName(), archiveName);
			nameChanger.applyNewName();

			theProject = ResourcesPlugin.getWorkspace().getRoot().getProject(archiveName);
			ArchiveProject archiver = new ArchiveProject(theProject.getLocation().toString());
			archiver.createArchive();

			nameChanger.revertToOldName();

			// MessageDialog.openInformation(
			// window.getShell(),
			// "EasyExport",
			// projects[0].getLocation().toString());
		}
		return null;
	}

	/*---------------------------------------------------------------------
	 *  Method name:	getCurrentProject
	 *	 Parameters:	N/A
	 *	    Purpose:	Get the current project by looking at 
	 *						the active file editor
	 *	    Returns:	IProject
	 *-------------------------------------------------------------------*/
	private IProject getCurrentProject(IWorkbenchWindow window) {
		IWorkbenchPage page = window.getActivePage();
		IEditorPart editor = page.getActiveEditor();
		IEditorInput input = editor.getEditorInput();
		//IPath path = ((FileEditorInput)input).getPath();
		IFile file = ((FileEditorInput)input).getFile();
		return file.getProject();
	}
}
