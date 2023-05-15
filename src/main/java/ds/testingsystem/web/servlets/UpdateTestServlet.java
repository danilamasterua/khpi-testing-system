package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Test;
import ds.testingsystem.web.controllers.SupportController;
import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateServlet")
public class UpdateTestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        int testId = Integer.parseInt(req.getParameter("testId"));
        String name = req.getParameter("testName");
        String description = req.getParameter("testDescription");
        try {
            TestController.updateTest(testId, new Test(name, description));
            goTo(req, resp, testId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        try {
            goTo(req, resp, testId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void goTo(HttpServletRequest req, HttpServletResponse resp, int testId) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Test test = TestController.loadTest(testId);
        session.setAttribute("creatingTestId", testId);
        req.setAttribute("test", test);
        req.setAttribute("questionTypes", SupportController.getQuestionTypes());
        req.setAttribute("difficults", SupportController.getDifficults());
        getServletContext().getRequestDispatcher("/updateTest.jsp").forward(req, resp);
    }
}
