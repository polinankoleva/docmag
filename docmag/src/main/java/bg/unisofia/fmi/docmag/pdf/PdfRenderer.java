package bg.unisofia.fmi.docmag.pdf;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.dao.impl.UserDAOImpl;
import bg.unisofia.fmi.docmag.domain.impl.document.Document.DocumentType;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.SAXException;

public class PdfRenderer {

    private String cssUri;
    private ITextRenderer renderer;
    private Map<DocumentType, Document> templates;

    public PdfRenderer() {
        ClassLoader loader = getClass().getClassLoader();
        String cssDir      = loader.getResource(Constants.CSS_DIR).getPath();
        cssUri             = new File(cssDir).toURI().toString();
        renderer           = new ITextRenderer();
        templates          = new HashMap<>(Template.values().length);
    }

    /**
     * Do this only once, because it involves IO and throws Exceptions.
     */
    public void loadFonts() throws IOException, DocumentException {
        ITextFontResolver resolver = renderer.getFontResolver();
        for (Font font : Font.values())
            for (String file : font.files()) {
                String path = font.dir() + '/' + file;
                resolver.addFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
    }

    /**
     * You know the drill.
     */
    public void loadTemplates()
            throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder        = factory.newDocumentBuilder();
        // Otherwise will try to download DTD.
        builder.setEntityResolver(FSEntityResolver.instance());
        for (Template templ : Template.values()) {
            Document doc = builder.parse(new File(templ.file()));
            templates.put(templ.type(), doc);
        }
    }

    /**
     * Call this to get a template, then fill in the blanks and render it.
     */
    public Document getTemplate(DocumentType type) {
        return templates.get(type);
    }

    public void renderDoc(Document doc, OutputStream output)
            throws DocumentException {
        renderer.setDocument(doc, cssUri);
        renderer.layout();
        renderer.createPDF(output, true);
    }

    // Let's try it.
    public static void main(String[] args) throws Exception {
//        String testFile = System.getProperty("user.home") + "/test.pdf";
//        try (OutputStream output = new FileOutputStream(testFile)) {
//            PdfRenderer renderer = new PdfRenderer();
//            renderer.loadFonts();
//            renderer.loadTemplates();
//            Document doc = renderer.getTemplate(DocumentType.ThesisProposal);
//            Element department = doc.getElementById("department");
//            department.appendChild(doc.createTextNode("Софтуерни технологии"));
//            Element faculty = doc.getElementById("faculty");
//            faculty.appendChild(doc.createTextNode("ФМИ"));
//            Element studentName = doc.getElementById("student_name");
//            studentName.appendChild(doc.createTextNode("Теодора Тончева"));
//            Element studentSubject = doc.getElementById("student_subject");
//            studentSubject.appendChild(doc.createTextNode("Софтуерно инженерство"));
//            Element facultyNumber = doc.getElementById("faculty_number");
//            facultyNumber.appendChild(doc.createTextNode("65432"));
//            renderer.renderDoc(doc, output);
//        }
        
        Markdown4jProcessor md4j = new Markdown4jProcessor();
        
       Student student = new Student(UserType.Student);
       StudentProfile profile = new StudentProfile();
       profile.setFirstName("Teddy");
       profile.setLastName("Toncheva");
       profile.setDepartment("Soft Tech");
       profile.setEducationSubject("SI");
       student.setProfile(profile);
       VelocityEngine ve = new VelocityEngine();
       ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
       ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
       ve.init();
       String text = "pdf-templates/html/thesis_proposal.html";
       org.apache.velocity.Template t = ve.getTemplate(text, "UTF-8");
       VelocityContext context = new VelocityContext();
       context.put("faculty", "FMI");
       context.put("student", student);
       context.put("markdown", md4j);
       context.put("title", "##title");
       StringWriter writer = new StringWriter();
       t.merge(context, writer);
       System.out.println(writer.toString());
    }
}
