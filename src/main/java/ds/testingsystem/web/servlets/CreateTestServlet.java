package ds.testingsystem.web.servlets;

import com.google.gson.Gson;
import ds.testingsystem.database.model.Model;
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

@WebServlet("/create-test")
public class CreateTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("difficults", SupportController.getDifficults());
            req.setAttribute("questionTypes", SupportController.getQuestionTypes());
            getServletContext().getRequestDispatcher("/createTest.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Model model = (Model) session.getAttribute("model");
            String testJson = req.getParameter("test");
            Gson g = new Gson();
            Test test = g.fromJson(testJson, Test.class);
            test.setUserId(model.getCurrentUser().getUserId());
            int testId =  TestController.createTest(test); //1;
            Test newTest = TestController.loadTest(testId);
            session.setAttribute("creatingTestId", testId);
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
