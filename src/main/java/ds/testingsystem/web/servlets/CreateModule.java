package ds.testingsystem.web.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ds.testingsystem.database.model.Model;
import ds.testingsystem.database.model.Module;
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

@WebServlet("/createModule")
public class CreateModule extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Model model = (Model) session.getAttribute("model");
            String jsonData = req.getParameter("data");
            Gson g = new Gson();
            JsonObject moduleJson = g.fromJson(jsonData, JsonObject.class);
            Module module = new Module(moduleJson.get("description").getAsString(), moduleJson.get("qCount").getAsInt());
            TestController.insertModule(moduleJson.get("testId").getAsInt(), module);
            Test newTest = TestController.loadTest(moduleJson.get("testId").getAsInt());
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("testId", moduleJson.get("testId").getAsInt());
            jsonResponse.put("test", newTest);
            jsonResponse.put("modules", newTest.getModules());
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().append(jsonResponse.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
