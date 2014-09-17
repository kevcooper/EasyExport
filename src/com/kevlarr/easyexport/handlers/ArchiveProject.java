/*=============================================================================
 *		  File:	ArchiveProject.java
 *		Author: Kevin Cooper (kevincooper@email.arizona.edu)
 * Description: 
 *
 *===========================================================================*/

package com.kevlarr.easyexport.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.kevlarr.easyexport.Activator;
	 
	public class ArchiveProject{
	    List<String> fileList;
	    private String outputArchive;
	    private String sourceDirectory;
	 
	    ArchiveProject(String project){
		fileList = new ArrayList<String>();
		outputArchive = Activator.getDefault().getPreferenceStore().getString("pathPreference") 
							+ project.substring(project.lastIndexOf("/")) + ".zip";
		sourceDirectory = project;
		
		genFileList(new File(sourceDirectory));
	    }
	 
	    public void createArchive(){
	     byte[] buffer = new byte[1024];
	 
	     try{
	 
	    	FileOutputStream fileOutStream = new FileOutputStream(outputArchive);
	    	ZipOutputStream zipOutStream = new ZipOutputStream(fileOutStream);
	 
	    	for(String file : this.fileList){
	 
	    		ZipEntry entry = new ZipEntry(file);
	        	zipOutStream.putNextEntry(entry);
	 
	        	FileInputStream in = 
	                       new FileInputStream(sourceDirectory + File.separator + file);
	 
	        	int len;
	        	while ((len = in.read(buffer)) > 0) {
	        		zipOutStream.write(buffer, 0, len);
	        	}
	 
	        	in.close();
	    	}
	 
	    	zipOutStream.closeEntry();
	    	zipOutStream.close();
	 
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	   }
	 
	    public void genFileList(File node){
	 
		if(node.isFile()){
			fileList.add(archiveEntry(node.getAbsoluteFile().toString()));
		}
	 
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				genFileList(new File(node, filename));
			}
		}
	 
	    }

	    private String archiveEntry(String file){
	    	return file.substring(sourceDirectory.length()+1, file.length());
	    }
	    
	}