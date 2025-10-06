package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;

@WebServlet("/IncidenteServlet")
public class IncidenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String descricao = req.getParameter("descricao");
        String prioridade = req.getParameter("prioridade");
        String idStatus = req.getParameter("id_status");
        String idUsuario = req.getParameter("id_usuario");
        String idTecnico = req.getParameter("id_tecnico");
        String idServico = req.getParameter("id_servico");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Incidente (descricao, prioridade, id_status, id_usuario, id_tecnico, id_servico) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, descricao);
            ps.setString(2, prioridade);
            ps.setInt(3, Integer.parseInt(idStatus));
            ps.setInt(4, Integer.parseInt(idUsuario));
            ps.setInt(5, Integer.parseInt(idTecnico));
            ps.setInt(6, Integer.parseInt(idServico));
            ps.executeUpdate();

            resp.sendRedirect("MenuIncidente.html");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h2>Erro ao cadastrar incidente: " + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Incidente")) {

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html lang='pt-br'><head><meta charset='UTF-8'><title>Incidentes</title></head><body>");
            html.append("<h1>Lista de Incidentes</h1>");
            html.append("<table border='1'><tr><th>ID</th><th>Descrição</th><th>Prioridade</th><th>ID Status</th><th>ID Usuário</th><th>ID Técnico</th><th>ID Serviço</th></tr>");

            while (rs.next()) {
                html.append("<tr>");
                html.append("<td>").append(rs.getInt("id_incidente")).append("</td>");
                html.append("<td>").append(rs.getString("descricao")).append("</td>");
                html.append("<td>").append(rs.getString("prioridade")).append("</td>");
                html.append("<td>").append(rs.getInt("id_status")).append("</td>");
                html.append("<td>").append(rs.getInt("id_usuario")).append("</td>");
                html.append("<td>").append(rs.getInt("id_tecnico")).append("</td>");
                html.append("<td>").append(rs.getInt("id_servico")).append("</td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("<br><button onclick=\"window.location.href='MenuIncidente.html'\">Voltar ao Menu</button>");
            html.append("</body></html>");

            resp.getWriter().println(html.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<h2>Erro ao listar incidentes: " + e.getMessage() + "</h2>");
        }
    }
}
