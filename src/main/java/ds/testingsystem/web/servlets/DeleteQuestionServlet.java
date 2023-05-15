package ds.testingsystem.web.servlets;

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

@WebServlet("/deleteQuestion")
public class DeleteQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
        HttpSession session = req.getSession();
        int testId = (int) session.getAttribute("creatingTestId");
        int qId = Integer.parseInt(req.getParameter("qId"));
        TestController.deleteQuestion(qId);
        JSONObject jsonResponse = TestController.getTestJsonResponse(testId);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().append(jsonResponse.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
