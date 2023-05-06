package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteTest")
public class DeleteTestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        TestController.deleteTest(testId);
        getServletContext().getRequestDispatcher("/test-control-panel").forward(req, resp);
    }
}
