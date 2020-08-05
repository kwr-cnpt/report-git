package controllers.search;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class SearchReportServlet
 */
@WebServlet("/search/report")
public class SearchReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int backId = 2;

        List<Report> allReports = em.createNamedQuery("getAllReports", Report.class).getResultList();

        List<Report> byName = em.createNamedQuery("searchByEmployeeName", Report.class)
                                .setParameter("name", "%" + request.getParameter("name") + "%")
                                .getResultList();

        List<Report> byStartDate = new ArrayList<Report>();
        if(request.getParameter("startDate") == null || request.getParameter("startDate").equals("")){
            byStartDate = allReports;
        }else{
            byStartDate = em.createNamedQuery("searchByStartDate", Report.class)
                     .setParameter("startDate", Date.valueOf(request.getParameter("startDate")))
                     .getResultList();
        }

        List<Report> byEndDate = new ArrayList<Report>();
        if(request.getParameter("endDate") == null || request.getParameter("endDate").equals("")){
            byEndDate = allReports;
        }else{
            byEndDate = em.createNamedQuery("searchByEndDate", Report.class)
                     .setParameter("endDate", Date.valueOf(request.getParameter("endDate")))
                     .getResultList();
        }

        List<Report> byTitle = em.createNamedQuery("searchByTitle", Report.class)
                                 .setParameter("title", "%" + request.getParameter("content") + "%")
                                 .getResultList();
        List<Report> byContent = em.createNamedQuery("searchByContent", Report.class)
                 .setParameter("content", "%" + request.getParameter("content") + "%")
                 .getResultList();

        List<Report> r = new ArrayList<Report>();
        for(int i = 0; i < allReports.size(); i++){
            if(byName.contains(allReports.get(i)) && byStartDate.contains(allReports.get(i)) && byEndDate.contains(allReports.get(i)) && (byTitle.contains(allReports.get(i)) || byContent.contains(allReports.get(i)))){
                r.add(allReports.get(i));
            }
        }

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page = 1;
        }
        int reports_count = r.size();
        List<Report> reports = new ArrayList<Report>();
        for(int i = 15 * (page - 1); i < 15 * page; i++){
            if(i == reports_count){
                break;
            }
            reports.add(r.get(i));
        }

        em.close();

        request.setAttribute("backId", backId);
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("content", request.getParameter("content"));
        request.setAttribute("startDate", request.getParameter("startDate"));
        request.setAttribute("endDate", request.getParameter("endDate"));

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);

        System.out.println("名前検索");
        System.out.println(byName);
        System.out.println("期間検索");
        System.out.println(byStartDate);
        System.out.println(byEndDate);
        System.out.println("タイトル検索");
        System.out.println(byTitle);
        System.out.println("内容検索");
        System.out.println(byContent);
    }

}
