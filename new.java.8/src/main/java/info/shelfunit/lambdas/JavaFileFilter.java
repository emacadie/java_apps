package info.shelfunit.lambdas;

import java.io.File;
import java.io.FileFilter;

public class JavaFileFilter implements FileFilter {
    public boolean accept( File file ) {
        return file.getName().endsWith( ".java" );
    }
}


