package controllers.follow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;


/**
 * Servlet implementation class FollowIndexServlet
 */
@WebServlet("/follows/index")
public class FollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page = 1;
        }

        List<Follow> follows = em.createNamedQuery("getFollowed", Follow.class)
                .setParameter("follower", login_employee)
                .getResultList();
        List<Report> all_follow_reports = new ArrayList<Report>();

        for(int i = 0; i < follows.size(); i++){
            all_follow_reports.addAll(em.createNamedQuery("getFollowReports", Report.class)
                    .setParameter("employee", follows.get(i).getFollowed())
                    .getResultList());
        }
        int follow_reports_count = all_follow_reports.size();

        List<Report> follow_reports = new ArrayList<Report>();
        for(int i = 15 * (page - 1); i < 15 * page; i++){
            if(i == follow_reports_count){
                break;
            }
            follow_reports.add(all_follow_reports.get(i));
        }

        em.close();

        request.setAttribute("follow_reports", follow_reports);
        request.setAttribute("follow_reports_count", follow_reports_count);
        request.setAttribute("page", page);

        request.setAttribute("follows", follows);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);

//        System.out.println(follows);
//        System.out.println(follow_reports);
    }

}
