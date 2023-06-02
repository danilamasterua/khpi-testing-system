package ds.testingsystem.web.servlets.filters;

import ds.testingsystem.database.model.Model;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "userFilter", value = {"/*"})
public class UserFilter implements Filter {
    private ServletContext servletContext;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Model model = (Model) session.getAttribute("model");
        String requestURI = req.getRequestURI();
        if(requestURI.endsWith("/login")||requestURI.equals("/")){
            chain.doFilter(request, response);
        } else {
            if(model!=null){
                chain.doFilter(request, response);
            } else {
                servletContext.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
