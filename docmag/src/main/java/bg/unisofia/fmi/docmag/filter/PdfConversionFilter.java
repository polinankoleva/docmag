package bg.unisofia.fmi.docmag.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import bg.unisofia.fmi.docmag.pdf.PdfRenderer;

import com.lowagie.text.DocumentException;

public class PdfConversionFilter implements Filter {

    private PdfRenderer renderer;
    private String fontsDir, cssDir;
    @Autowired ServletContext context;
    // getters
    public String getFontsDir() { return fontsDir; }
    public String getCssDir()   { return cssDir;   }

    public void setFontsDir(String path) {
        fontsDir = context.getRealPath(path);
    }

    public void setCssDir(  String path) {
        cssDir   = context.getRealPath(path);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        try { renderer = PdfRenderer.newInstance(fontsDir, cssDir); }
        catch (Exception ex) {
            throw new ServletException(ex.getMessage(), ex);
        }
    }

    @Override
    public void doFilter(ServletRequest  request,
                         ServletResponse response,
                         FilterChain     chain)
                                 throws IOException, ServletException {
        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            CharResponseWrapper wrapper  = new CharResponseWrapper(httpResp);
            try (OutputStream output     = httpResp.getOutputStream()) {
                chain.doFilter(request, wrapper);
                renderer.renderString(wrapper.toString(), output);
                wrapper.setContentType("application/pdf");
            } catch (DocumentException | SAXException ex) {
                throw new ServletException(ex.getMessage(), ex);
            }
        } else chain.doFilter(request, response);
    }

    @Override public void destroy() { }
}
