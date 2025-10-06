package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;

@WebServlet("/AtualizacaoServlet")
public class AtualizacaoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        int id_incidente = Integer.parseInt(req.getParameter("id_incidente"));
        String descricao = req.getParameter("descricao");
        String autor = req.getParameter("autor");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Atualizacao (id_incidente,descricao,autor) VALUES (?,?,?)"
            );
            ps.setInt(1, id_incidente);
            ps.setString(2, descricao);
            ps.setString(3, autor);
            ps.executeUpdate();

            resp.sendRedirect("MenuIncidente.html");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("MenuIncidente.html"); 
        }
    }
}
