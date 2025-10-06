package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Usuario WHERE email = ? AND senha = ?"
            );
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resp.sendRedirect("MenuIncidente.html");
                return;
            }

            ps = conn.prepareStatement(
                "SELECT * FROM Tecnico WHERE email = ? AND senha = ?"
            );
            ps.setString(1, email);
            ps.setString(2, senha);
            rs = ps.executeQuery();

            if (rs.next()) {
                resp.sendRedirect("MenuIncidente.html");
            } else {
                resp.sendRedirect("Login.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("Login.html");
        }
    }
}
