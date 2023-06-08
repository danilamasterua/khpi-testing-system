package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUserPoints")
public class DeleteUserPointsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        TestController.clearUserPoint(testId, userId);
        resp.sendRedirect(req.getContextPath()+"/getResults?testId="+testId);
    }
}
