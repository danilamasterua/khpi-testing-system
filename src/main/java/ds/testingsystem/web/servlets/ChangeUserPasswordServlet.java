package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.UserController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changePassword")
public class ChangeUserPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String verCode = req.getParameter("verCode");
        System.out.println(verCode);
        int errorCode = UserController.changePassword(verCode, userId);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("errorCode", errorCode);
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().append(jsonResponse.toString());
    }
}
