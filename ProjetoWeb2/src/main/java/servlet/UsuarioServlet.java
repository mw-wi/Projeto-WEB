package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String setor = req.getParameter("setor");
        String senha = req.getParameter("senha");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Usuario (nome,email,setor,senha) VALUES (?,?,?,?)"
            );
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, setor);
            ps.setString(4, senha);
            ps.executeUpdate();

            resp.getWriter().println("<h2>Usuário cadastrado com sucesso!</h2>");
            resp.getWriter().println("<button onclick=\"window.location.href='login.html'\">Voltar para Login</button>");
            resp.sendRedirect("Login.html");

        } catch (Exception e) {
                   e.printStackTrace();
        }
    }
}