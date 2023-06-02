package ds.testingsystem.web.servlets;

import com.google.gson.Gson;
import ds.testingsystem.database.model.Model;
import ds.testingsystem.database.model.beans.UserAnswerList;
import ds.testingsystem.web.controllers.TestController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/setAnswer")
public class SetAnswerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Model model = (Model) session.getAttribute("model");
            String jsonData = req.getParameter("data");
            Gson gson = new Gson();
            UserAnswerList userAnswerList = gson.fromJson(jsonData, UserAnswerList.class);
            boolean isRight = TestController.setAnswers(model.getCurrentUser().getUserId(), userAnswerList);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("isRight", isRight);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
