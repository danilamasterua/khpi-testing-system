package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Group;
import ds.testingsystem.database.model.Question;
import ds.testingsystem.database.model.beans.UserTestPoints;
import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

@WebServlet("/getResults")
public class GetResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        HashMap<Group, LinkedList<UserTestPoints>> info = TestController.getUserPointsByTestId(testId);
        try {
            HashMap<Question, Double> questionStatistic = TestController.getQuestionStatistic(testId);
            req.setAttribute("statistic", questionStatistic);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("gI", info);
        req.setAttribute("testId", testId);
        getServletContext().getRequestDispatcher("/testStatistic.jsp").forward(req, resp);
    }
}
