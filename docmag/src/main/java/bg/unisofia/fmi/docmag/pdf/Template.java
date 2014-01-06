package bg.unisofia.fmi.docmag.pdf;

import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;

/**
 * Be careful to define only one template per class.
 */
public enum Template {
    THESIS_PROPOSAL(DocumentType.ThesisProposal, "thesis_proposal.html");

    private String file;
    private DocumentType type;

    Template(DocumentType type, String file) {
        String      path   = Constants.HTML_DIR + '/' + file;
        ClassLoader loader = getClass().getClassLoader();
        this.file          = loader.getResource(path).getFile();
        this.type          = type;
    }

    public String       file() { return file; }
    public DocumentType type() { return type; }
}
