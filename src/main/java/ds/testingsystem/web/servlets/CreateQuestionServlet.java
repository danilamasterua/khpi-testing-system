package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Test;
import ds.testingsystem.web.controllers.TestController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/createQuestion")
public class CreateQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            int testId = (Integer) session.getAttribute("creatingTestId");
            String jsonData = req.getParameter("data");
            TestController.createQuestion(jsonData);
            Test newTest = TestController.loadTest(testId);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("testId", testId);
            jsonResponse.put("test", newTest);
            jsonResponse.put("modules", newTest.getModules());
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
