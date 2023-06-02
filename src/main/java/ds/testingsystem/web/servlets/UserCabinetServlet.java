package ds.testingsystem.web.servlets;

import ds.testingsystem.database.model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/userCab")
public class UserCabinetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        req.setAttribute("user", model.getCurrentUser());
        getServletContext().getRequestDispatcher("/userInfo.jsp").forward(req, resp);
    }
}
