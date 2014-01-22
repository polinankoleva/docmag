package bg.unisofia.fmi.docmag.pdf;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.markdown4j.Markdown4jProcessor;

import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.profile.StudentProfile;
import bg.unisofia.fmi.docmag.domain.impl.profile.TeacherProfile;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

public class PdfTester {

	private static final String ENCODING = "UTF-8";
	private static final String DATE_PATTERN = "dd.MM.YYYY";
	private String fontsDir, htmlDir, cssDir;
	private Markdown4jProcessor markdown = new Markdown4jProcessor();
	private VelocityEngine velocity = new VelocityEngine();
	private PdfRenderer renderer;

	public void init() throws Exception {
		ClassLoader loader = PdfTester.class.getClassLoader();
		Path root = Paths.get(loader.getResource("").toURI());
		Path src = root.getParent().getParent().resolve("src");
		Path webInf = src.resolve("main").resolve("webapp").resolve("WEB-INF");
		Path templPath = webInf.resolve("pdf-templates");
		fontsDir = templPath.resolve("fonts").toAbsolutePath().toString();
		htmlDir = templPath.resolve("html").toAbsolutePath().toString();
		cssDir = templPath.resolve("css").toAbsolutePath().toString();
		renderer = PdfRenderer.newInstance(fontsDir, cssDir);
		velocity.setProperty("file.resource.loader.path", htmlDir);
		velocity.setProperty("input.encoding", "UTF-8");
		velocity.setProperty("output.encoding", "UTF-8");
	}

	public void testThesisProposal(String pdfFile) throws Exception {
		String fileName = "thesis_proposal.vm.html";
		Template templ = velocity.getTemplate(fileName, ENCODING);
		Context context = new VelocityContext();
		Writer writer = new StringWriter();
		context.put("markdown", markdown);
		context.put("dateFormat", new SimpleDateFormat(DATE_PATTERN));
		Student student = student();
		Document doc = thesisProposal();
		List<Teacher> leaders = new LinkedList<>();
		List<Teacher> consultants = new LinkedList<>();
		leaders.add(teacher());
		consultants.add(teacher());
		context.put("student", student);
		context.put("document", doc);
		context.put("scientificLeaders", leaders);
		context.put("consultants", consultants);
		templ.merge(context, writer);
		OutputStream output = new FileOutputStream(pdfFile);
		renderer.renderString(writer.toString(), output);
	}

	private Student student() {
		Student student = new Student(UserType.Student);
		StudentProfile profile = new StudentProfile();
		profile.setFirstName("Минчо");
		profile.setSurname("Ангелов");
		profile.setLastName("Гайдаров");
		profile.setStudentIdentifier("65432");
		profile.setFaculty("ФМИ");
		profile.setEducationSubject("Софтуерно инженерство");
		student.setProfile(profile);
		return student;
	}

	private Teacher teacher() {
		Teacher teacher = new Teacher(UserType.Teacher);
		List<User> assignees = new LinkedList<>();
		assignees.add(student());
		TeacherProfile profile = new TeacherProfile("доц. д-р", assignees);
		profile.setFirstName("Пламен");
		profile.setSurname("Николов");
		profile.setLastName("Сидеров");
		profile.setFaculty("ФМИ");
		profile.setDepartment(TeacherProfile.Department.Algebra);
		teacher.setProfile(profile);
		return teacher;
	}

	private ThesisProposal thesisProposal() {
		ThesisProposal doc = new ThesisProposal();
		doc.setAnnotation("Анотация на дипломната работа.");
		doc.setExecutionDeadline(new Date());
		doc.setPurpose("Целта на дипломната работа е да се свърши нещо.");
		doc.setRestrictions("1. Да няма много работа.\n2. Да е лесничко.");
		doc.setSubject("##Темата е много сложна и трудна.");
		doc.setTasks("* Модул за разни работи.\n* Нищо особено.");
		return doc;
	}

	public static void main(String[] args) throws Exception {
		String home = System.getProperty("user.home");
		PdfTester tester = new PdfTester();
		tester.init();
		tester.testThesisProposal(home + "/ThesisProposal.pdf");
	}
}
