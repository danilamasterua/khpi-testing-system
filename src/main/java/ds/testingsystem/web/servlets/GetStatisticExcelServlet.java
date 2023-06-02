package ds.testingsystem.web.servlets;

import ds.testingsystem.web.controllers.TestController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.SQLException;

import static ds.testingsystem.services.GenerateExcelFile.rfc5987_encode;

@WebServlet("/getResults/xlsFile")
public class GetStatisticExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int testId = Integer.parseInt(req.getParameter("testId"));
            String appPath = req.getServletContext().getRealPath("");
            appPath.replace("\\","/");
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + "test_data\\";
            } else {
                fullSavePath = appPath + "test_data\\";
            }
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            String filename = TestController.generateExcelTestPoints(fullSavePath, testId);
            File file = new File(filename);
            filename = file.getName();
            ServletOutputStream outputStream = null;
            BufferedInputStream inputStream = null;
            try {
                outputStream = resp.getOutputStream();
                resp.setContentType("application/vnd.ms-excel");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + rfc5987_encode(filename)  + "\"");
                resp.setContentLength((int) file.length());
                FileInputStream fileInputStream = new FileInputStream(file);
                inputStream = new BufferedInputStream(fileInputStream);
                int readBytes = 0;
                while ((readBytes = inputStream.read()) != -1)
                    outputStream.write(readBytes);
            }catch (ExportException e){
                e.printStackTrace();
            }finally {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
            req.setAttribute("testId", testId);
            getServletContext().getRequestDispatcher("/getResults").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
