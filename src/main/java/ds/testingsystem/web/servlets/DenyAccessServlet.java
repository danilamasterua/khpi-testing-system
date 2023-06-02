package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.SupportController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/denyAccess")
public class DenyAccessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        int groupId = Integer.parseInt(req.getParameter("gId"));
        SupportController.denyAccess(groupId, testId);
        getServletContext().getRequestDispatcher("/loadTestAccessPage").forward(req, resp);
    }
}
