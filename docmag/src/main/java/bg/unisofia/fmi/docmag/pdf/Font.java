package bg.unisofia.fmi.docmag.pdf;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;

/** We need Cyrillic support. Also, have to be open source. */
public enum Font {
    LIBERATION_SERIF("liberation_serif", "ttf"), // Serif
    SANSATION("sansation", "ttf"),               // Sans
    UBUNTU("ubuntu", "ttf");                     // Sans + Mono

    private String dir, type;

    Font(String dir, String type) {
        this.dir  = dir;
        this.type = type;
    }

    public String dir()  { return dir;  }
    public String type() { return type; }

    public String[] files(String baseDir) {
        Path path = new File(baseDir).toPath().resolve(dir());
        return path.toFile().list(new FilenameFilter() {
            @Override public boolean accept(File dir, String name) {
                return name.endsWith('.' + type());
            }
        });
    }
}
