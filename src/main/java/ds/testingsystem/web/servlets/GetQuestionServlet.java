package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Test;
import ds.testingsystem.web.controllers.SupportController;
import ds.testingsystem.web.controllers.TestController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/getQuestion")
public class GetQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            int testId = (int) session.getAttribute("creatingTestId");
            String jsonData = req.getParameter("data");
            TestController.updateQuestion(jsonData);
            goTo(req, resp, session);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int qId = Integer.parseInt(req.getParameter("qId"));
            JSONObject jsonResponse = TestController.getQuestionJSON(qId);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void goTo(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SQLException, ServletException, IOException {
        int testId = (int) session.getAttribute("creatingTestId");
        Test test = TestController.loadTest(testId);
        req.setAttribute("test", test);
        req.setAttribute("questionTypes", SupportController.getQuestionTypes());
        req.setAttribute("difficults", SupportController.getDifficults());
        getServletContext().getRequestDispatcher("/updateTest.jsp").forward(req, resp);
    }
}
