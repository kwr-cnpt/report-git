package controllers.search;

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
import utils.DBUtil;
/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search/employee")
public class SearchEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){ }

        List<Employee> allEmployees = em.createNamedQuery("getAllEmployees", Employee.class).getResultList();

//        従業員名で検索
        List<Employee> employees_byName = em.createNamedQuery("searchEmployeeByName", Employee.class)
                                     .setParameter("name", "%" + request.getParameter("name") + "%")
                                     .getResultList();
//        社員番号で検索
        List<Employee> employees_byCode = new ArrayList<Employee>();
        if(request.getParameter("code") == null || request.getParameter("code").equals("")){
            employees_byCode = allEmployees;
        }else{
            employees_byCode = em.createNamedQuery("searchEmployeeByCode", Employee.class)
                    .setParameter("code", request.getParameter("code"))
                    .getResultList();
        }

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        List<Follow> follows = em.createNamedQuery("getFollowed", Follow.class)
                .setParameter("follower", login_employee)
                .getResultList();
        List<Employee> followed = new ArrayList<Employee>();
        for(int i = 0; i < follows.size(); i++){
            followed.add(follows.get(i).getFollowed());
        }

//        全てを満たす検索結果を取得
        List<Employee> e = new ArrayList<Employee>();
        for(int i = 0; i < allEmployees.size(); i++){
            if(employees_byName.contains(allEmployees.get(i)) && employees_byCode.contains(allEmployees.get(i))){
                e.add(allEmployees.get(i));
            }
        }
        int employees_count = e.size();

        List<Employee> employees = new ArrayList<Employee>();
        for(int i = 15 * (page - 1); i < 15 * page; i++){
            if(i == employees_count){
                break;
            }
            employees.add(e.get(i));
        }

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("followed", followed);
        request.setAttribute("page", page);
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("code", request.getParameter("code"));

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/show.jsp");
        rd.forward(request, response);

        System.out.println("******************");
        System.out.println(employees_byCode);
        System.out.println("******************");
        System.out.println(employees_byName);

    }
}
