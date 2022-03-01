package servlets;

import expression.MyExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.myexcep.DivideByZeroException;
import expression.exceptions.myexcep.InvalidTokenException;
import expression.exceptions.myexcep.OverflowException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import templater.PageGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UniversalServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        String message = request.getParameter("expression");
        String varX = request.getParameter("x");
        String varY = request.getParameter("y");
        String varZ = request.getParameter("z");
        int x, y, z;
        try {
            x = Integer.parseInt(varX);
        } catch (NumberFormatException e) {
            x = 0;
        }
        try {
            y = Integer.parseInt(varY);
        } catch (NumberFormatException e) {
            y = 0;
        }
        try {
            z = Integer.parseInt(varZ);
        } catch (NumberFormatException e) {
            z = 0;
        }
        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        ExpressionParser parser = new ExpressionParser();
        if (message != null) {
            try {
                MyExpression res = parser.parse(message);
                pageVariables.put("result", res.evaluate(x, y, z));
                pageVariables.put("expression", res.toString());
            } catch (InvalidTokenException | OverflowException | DivideByZeroException e) {
                pageVariables.put("result", e.getMessage());
                pageVariables.put("expression", message);
            }
        }
        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", 0);
        pageVariables.put("expression", "");
        pageVariables.put("x", "0");
        pageVariables.put("y", "0");
        pageVariables.put("z", "0");
        return pageVariables;
    }
}
