package com.laze.w2.controller;

import com.laze.w2.dto.TodoDTO;
import com.laze.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Long tno = Long.parseLong(req.getParameter("tno"));

            TodoDTO todoDTO = todoService.get(tno);

            req.setAttribute("dto", todoDTO);

            Cookie viewToodCookie = findCookie(req.getCookies(), "viewTodos");
            String todoListStr = viewToodCookie.getValue();
            boolean exist = false;

            if(todoListStr != null && todoListStr.indexOf(tno+"-") >= 0) {
                exist = true;
            }

            log.info("exist: " +exist);

            if(!exist) {
                todoListStr += tno + "-";
                viewToodCookie.setValue(todoListStr);
                viewToodCookie.setMaxAge(60 * 60 * 24);
                viewToodCookie.setPath("/");
                resp.addCookie(viewToodCookie);
            }

            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {

        Cookie targetCookie = null;

        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    targetCookie = cookie;
                    break;
                }
            }
        }

        if(targetCookie == null) {
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }

        return targetCookie;
    }

}
