package ds.testingsystem.web.servlets.filters;

import ds.testingsystem.database.model.Model;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "teacherFilter", value = {
        "/test-control-panel", "/blockTest", "/loadTestAccessPage",
        "/updateServlet", "/getResults", "/get-blocked-test",
        "/create-test"
})
public class TeacherFilter implements Filter {
    private ServletContext servletContext;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("#Teacher filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        if(model.getCurrentUser().getRoleId()==2||model.getCurrentUser().getRoleId()==1){
            chain.doFilter(request, response);
        } else {
            servletContext.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
