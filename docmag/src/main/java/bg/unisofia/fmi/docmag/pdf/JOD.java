package bg.unisofia.fmi.docmag.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class JOD {

    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("E:\\Downloads\\prop.odt");
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        IContext context = report.createContext();
        Student student = new Student("Misho", "SI", "65432");
        context.put("student", student);
        OutputStream out = new FileOutputStream("E:\\Downloads\\out.pdf");
        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(new Instructor("HOHO", "LEEEKI", "AMSD", "ASDASd"));
        instructors.add(new Instructor("HOHO", "LEEEKI", "AMSD", "ASDASd"));
        instructors.add(new Instructor("HOHO", "LEEEKI", "AMSD", "ASDASd"));
        context.put("instructors", instructors);
        report.convert(context, options, out);
    }
}


