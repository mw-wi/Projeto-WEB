package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;

@WebServlet("/TecnicoServlet")
public class TecnicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String nome = req.getParameter("nome");
        String especialidade = req.getParameter("especialidade");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Tecnico (nome, especialidade, email, senha) VALUES (?,?,?,?)"
            );
            ps.setString(1, nome);
            ps.setString(2, especialidade);
            ps.setString(3, email);
            ps.setString(4, senha);
            ps.executeUpdate();

            resp.sendRedirect("Login.html");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("Login.html");
        }
    }
}
