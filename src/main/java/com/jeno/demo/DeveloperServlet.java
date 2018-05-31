package com.jeno.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DeveloperServlet", urlPatterns = {ConstantUtil.ALL_DEVELOPERS_URL, ConstantUtil.QUERY_DEVELOPER_URL,
        ConstantUtil.ADD_DEVELOPER_URL, ConstantUtil.UPDATE_DEVELOPER_URL, ConstantUtil.DELETE_DEVELOPER_URL})
public class DeveloperServlet extends HttpServlet {


    DeveloperBusiness developerBusiness = new DeveloperBusiness();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("url=" + request.getRequestURI());
        request.setCharacterEncoding("UTF-8");

        //设置响应内容类型
        response.setContentType("text/json;charset=UTF-8");
        String url = request.getRequestURI();
        if (url.equals(ConstantUtil.ALL_DEVELOPERS_URL)) {
            //得到全部的数据
            getAllDevelopers(request, response);
        } else if (url.equals(ConstantUtil.ADD_DEVELOPER_URL)) {
            addDeveloper(request, response);
        }


    }

    private void getAllDevelopers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置逻辑实现
        PrintWriter printWriter = response.getWriter();
        List<DeveloperModel> developerList = developerBusiness.getAllDevelopers();
        CommonModel commonModel = new CommonModel();
        if (developerList.size() > 0) {
            commonModel.setSuccess();
            commonModel.setObject(developerList);
        } else {
            commonModel.setFail();
        }
        printWriter.println(GsonUtil.bean2Json(commonModel));
        printWriter.flush();

    }

    private void addDeveloper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//设置逻辑实现
        PrintWriter printWriter = response.getWriter();
        String name = request.getParameter("name");
        System.out.println("name=" + name);
        String site = request.getParameter("site");
        String avatar = request.getParameter("avatar");
        CommonModel commonModel = new CommonModel();
        DeveloperModel developerModel = new DeveloperModel();
        developerModel.setName(name);
        developerModel.setSite(site);

        if (developerBusiness.addDeveloper(developerModel)) {
            commonModel.setSuccess();
        } else {
            commonModel.setFail();
        }
        printWriter.println(GsonUtil.bean2Json(commonModel));
    }


}
