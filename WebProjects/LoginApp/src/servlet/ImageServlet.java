package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImageServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String imageName = request.getParameter("imageName");
		if (imageName != null) {
			String[] extensionArray = imageName.split("\\.");

			String extension = extensionArray[extensionArray.length - 1];

			File file = new File(
					"C:/Users/akovalcuks/workspace/LoginApp/images/"
							+ imageName);

			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();

			response.setContentType("image/" + extension);
			response.setContentLength((int) file.length());

			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}

			out.close();
			in.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}