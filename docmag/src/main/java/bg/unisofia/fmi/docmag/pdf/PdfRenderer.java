package bg.unisofia.fmi.docmag.pdf;

import java.io.*;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PdfRenderer {

    private String          cssUri;
    private ITextRenderer   renderer = new ITextRenderer();
    private DocumentBuilder docBuilder;

    /**
     * Use initializer pattern, because it involves IO and throws Exceptions.
     */
    public static PdfRenderer newInstance(String fontsDir, String cssDir)
            throws IOException,
                   DocumentException,
                   ParserConfigurationException {
        PdfRenderer renderer = new PdfRenderer();
        renderer.loadFonts(fontsDir);
        renderer.cssUri      = new File(cssDir).toURI().toString();
        renderer.docBuilder  = newDocumentBuilder();
        return renderer;
    }

    public void renderDocument(Document doc, OutputStream output)
            throws DocumentException {
        renderer.setDocument(doc, cssUri);
        renderer.layout();
        renderer.createPDF(output, true);
    }

    public void renderBytes(byte[] data, OutputStream output)
            throws IOException, SAXException, DocumentException {
        Document doc = docBuilder.parse(new ByteArrayInputStream(data));
        renderDocument(doc, output);
    }

    public void renderString(String data, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderBytes(data.getBytes(), output);
    }

    public void renderFile(File file, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderDocument(docBuilder.parse(file), output);
    }

    public void renderFile(String path, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderFile(new File(path), output);
    }

    public void renderUri(String uri, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderDocument(docBuilder.parse(uri), output);
    }

    public void renderInputStream(InputStream input, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderDocument(docBuilder.parse(input), output);
    }

    public void renderInputSource(InputSource source, OutputStream output)
            throws IOException, SAXException, DocumentException {
        renderDocument(docBuilder.parse(source), output);
    }

    private void loadFonts(String dir) throws IOException, DocumentException {
        ITextFontResolver resolver = renderer.getFontResolver();
        Path baseDir = new File(dir).toPath();
        for (Font font : Font.values()) {
            Path fontDir = baseDir.resolve(font.dir());
            for (String file : font.files(dir)) {
                String path = fontDir.resolve(file).toAbsolutePath().toString();
                resolver.addFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        }
    }

    private static DocumentBuilder newDocumentBuilder()
            throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder        builder = factory.newDocumentBuilder();
        // Otherwise will try to download DTD.
        builder.setEntityResolver(FSEntityResolver.instance());
        return builder;
    }
}
