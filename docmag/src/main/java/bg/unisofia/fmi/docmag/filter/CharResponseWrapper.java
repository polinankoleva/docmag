package bg.unisofia.fmi.docmag.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CharResponseWrapper extends HttpServletResponseWrapper {

	private Writer writer;
	private int status;

	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		writer = new CharArrayWriter();
	}

	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(writer);
	}

	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int code) {
		status = code;
		super.setStatus(code);
	}

	@Override
	public String toString() {
		return writer.toString();
	}
}
