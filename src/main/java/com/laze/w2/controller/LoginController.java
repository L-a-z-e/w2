package com.laze.w2.controller;

import com.laze.w2.dto.MemberDTO;
import com.laze.w2.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/login")
@Log4j2
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("login doGet...");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("login doPost...");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");
        String auto = req.getParameter("auto");

        boolean remember = auto != null && auto.equals("on");

        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);

            if(remember) {
                String uuid = UUID.randomUUID().toString();

                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);

                Cookie rememberCookie = new Cookie("remember", uuid);
                rememberCookie.setMaxAge(60*60*24*7);
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }

            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");
        } catch (Exception e) {
            resp.sendRedirect("/login?result=error");
        }

    }
}
