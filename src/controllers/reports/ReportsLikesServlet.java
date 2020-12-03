package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsLikesServlet
 */
@WebServlet("/reports/likes")
public class ReportsLikesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @param likes
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _token = (String)request.getParameter("_token");
		if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report report = em.find(Report.class,Integer.parseInt(request.getParameter("reports_likes")));
            System.out.println(report.getLikes());

            // 既存のいいね数にプラス１
            report.setLikes(report.getLikes() + 1);

				em.getTransaction().begin();
                em.getTransaction().commit();
                em.close(); // EntityManagerの利用を終了
                request.getSession().setAttribute("flush", " いいねが完了しました。");


                // indexページへ遷移
                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
	}
}
