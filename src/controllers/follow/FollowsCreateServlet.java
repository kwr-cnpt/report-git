package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsCreateServlet
 */
@WebServlet("/follows/create")
public class FollowsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int rd = Integer.parseInt(request.getParameter("rd"));

        Follow f = new Follow();

        f.setFollower((Employee)request.getSession().getAttribute("login_employee"));
        Employee followed = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        f.setFollowed(followed);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", followed.getName() + "さんをフォローしました。");

//       response.sendRedirect(request.getContextPath() + "/follows/show");

        if(rd == 2){
            response.sendRedirect(request.getContextPath() + "/follows/show");
        }else if(rd == 1){
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + Integer.parseInt(request.getParameter("reportId")) + "&backId=" + Integer.parseInt(request.getParameter("backId")));
        }
    }

}
