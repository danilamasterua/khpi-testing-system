package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Model;
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
import java.time.LocalDateTime;

@WebServlet("/getPoints")
public class GetTestPointsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            int testId = Integer.parseInt(req.getParameter("testId"));
            LocalDateTime startDateTime = LocalDateTime.parse(req.getParameter("startDate"));
            Model model = (Model) session.getAttribute("model");
            int userId = model.getCurrentUser().getUserId();
            double points = TestController.getPoints(userId, startDateTime);
            double maxPoints = TestController.getMaxPoint(userId, startDateTime);
            double percentPoints = points/maxPoints*100;
            TestController.setUserPoint(percentPoints, testId, userId);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("points", percentPoints);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
