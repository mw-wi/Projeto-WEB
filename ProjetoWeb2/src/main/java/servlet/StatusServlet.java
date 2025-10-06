package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;

@WebServlet("/StatusServlet")
public class StatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Status")) {

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html lang='pt-br'><head><meta charset='UTF-8'><title>Status</title></head><body>");
            html.append("<h1>Lista de Status</h1>");
            html.append("<table border='1'><tr><th>ID</th><th>Descrição</th></tr>");

            while (rs.next()) {
                html.append("<tr>");
                html.append("<td>").append(rs.getInt("id_status")).append("</td>");
                html.append("<td>").append(rs.getString("descricao")).append("</td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("<br><button onclick=\"window.location.href='MenuIncidente.html'\">Voltar ao Menu</button>");
            html.append("</body></html>");

            resp.getWriter().println(html.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h2>Erro ao listar status: " + e.getMessage() + "</h2>");
        }
    }
}
