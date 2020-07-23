package controllers.follow;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class FollowDestroyServlet
 */
@WebServlet("/follows/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        Employee followed = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        List<Follow> follows = em.createNamedQuery("getFollowed", Follow.class)
                .setParameter("follower", login_employee)
                .getResultList();
        Follow f = null;
        for(int i = 0; i < follows.size(); i++){
            if(follows.get(i).getFollowed() == followed){
                f = follows.get(i);
            }
        }

       int rd = Integer.parseInt(request.getParameter("rd"));

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", followed.getName() + "さんのフォローを解除しました。");

//       response.sendRedirect(request.getContextPath() + "/follows/show");

       if(rd == 2){
            response.sendRedirect(request.getContextPath() + "/follows/show");
        }else if(rd == 1){
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + Integer.parseInt(request.getParameter("reportId")) + "&backId=" + Integer.parseInt(request.getParameter("backId")));
        }

    }

}
