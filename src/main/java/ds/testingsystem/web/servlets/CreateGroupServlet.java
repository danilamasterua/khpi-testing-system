package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Group;
import ds.testingsystem.web.controllers.UserController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createGroup")
public class CreateGroupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("description");
        int gId = UserController.createEdGroup(new Group(name));
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("gId", gId);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().append(jsonResponse.toString());
    }
}
