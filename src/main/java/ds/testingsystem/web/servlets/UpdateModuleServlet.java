package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.TestController;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateModule")
public class UpdateModuleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonData = req.getParameter("data");
        try {
            TestController.updateModule(jsonData);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int moduleId = Integer.parseInt(req.getParameter("mId"));
        try {
            JSONObject jsonResponse = TestController.getModuleJson(moduleId);
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
