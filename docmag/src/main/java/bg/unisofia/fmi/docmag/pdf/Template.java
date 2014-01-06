package bg.unisofia.fmi.docmag.pdf;

import bg.unisofia.fmi.docmag.domain.impl.Doc;

/**
 * Be careful to define only one template per class.
 */
public enum Template {
    THESIS_PROPOSITION(Doc.class, "thesis_proposition.html");

    private String file;
    private Class<? extends Doc> type;

    Template(Class<? extends Doc> type, String file) {
        String      path   = Constants.HTML_DIR + '/' + file;
        ClassLoader loader = getClass().getClassLoader();
        this.file          = loader.getResource(path).getFile();
        this.type          = type;
    }

    public String               file() { return file; }
    public Class<? extends Doc> type() { return type; }
}
