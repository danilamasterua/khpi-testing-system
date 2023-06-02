package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.beans.GroupAccess;
import ds.testingsystem.web.controllers.SupportController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/grantAccess")
public class GrantAccessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int testId = Integer.parseInt(req.getParameter("testId"));
        int groupId = Integer.parseInt(req.getParameter("groupId"));
        GroupAccess ga = new GroupAccess();
        if(!req.getParameter("accStTime").equals("") && !req.getParameter("accFinTime").equals("")) {
            LocalDateTime stDateTime = LocalDateTime.parse(req.getParameter("accStTime"));
            LocalDateTime finDateTime = LocalDateTime.parse(req.getParameter("accFinTime"));
            ga = new GroupAccess(testId, groupId, stDateTime, finDateTime);
        } else if (!req.getParameter("minToFin").equals("")) {
            int minToFin = Integer.parseInt(req.getParameter("minToFin"));
            ga = new GroupAccess(testId, groupId, minToFin);
        } else {
            ga=new GroupAccess(testId, groupId);
        }
        SupportController.grantAccess(ga);
        getServletContext().getRequestDispatcher("/loadTestAccessPage").forward(req, resp);
    }
}
