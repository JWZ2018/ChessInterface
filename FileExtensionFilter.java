import java.io.File;
import javax.swing.filechooser.*;
public class FileExtensionFilter extends FileFilter{
	private String fileExt;
    public FileExtensionFilter() {
    	this.fileExt=".pgn";
    }
    public FileExtensionFilter(String ext){
    	this.fileExt=ext;
    }
    public boolean accept(File f){
    	if (f.isDirectory()){
    		return true;
    	}
        return  (f.getName().toLowerCase().endsWith(fileExt)); 
    }
    public String getDescription(){
    	return fileExt;
    }
    
    
}