package bg.unisofia.fmi.docmag.pdf;

import java.io.File;
import java.io.FilenameFilter;

/**
 * We need Cyrillic support. Also, have to be open source.
 */
public enum Font {
    LIBERATION_SERIF("liberation_serif", "ttf"), // Serif
    SANSATION("sansation", "ttf"),               // Sans
    UBUNTU("ubuntu", "ttf");                     // Sans + Mono

    private String dir, type;

    Font(String dir, String type) {
        String      path   = Constants.FONTS_DIR + '/' + dir;
        ClassLoader loader = getClass().getClassLoader();
        this.dir           = loader.getResource(path).getPath();
        this.type          = type;
    }

    public String dir()  { return dir;  }
    public String type() { return type; }

    public String[] files() {
        return new File(dir()).list(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                return name.endsWith('.' + type());
            }
        });
    }
}
