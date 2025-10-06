package servlet;

import java.io.*;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Conexao;
@WebServlet("/ServicoServlet")
public class ServicoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String nome = req.getParameter("nome");
        String descricao = req.getParameter("descricao");

        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Servico (nome,descricao) VALUES (?,?)"
            );
            ps.setString(1, nome);
            ps.setString(2, descricao);
            ps.executeUpdate();

            resp.getWriter().println("<h2>Serviço cadastrado com sucesso!</h2>");
            resp.sendRedirect("MenuIncidente.html");
        } catch (Exception e) {
            resp.getWriter().println("<h2>Erro: " + e.getMessage() + "</h2>");
        }
    }
}