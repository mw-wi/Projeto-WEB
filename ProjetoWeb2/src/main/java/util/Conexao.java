package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public static Connection getConexao() throws Exception {
        Class.forName("org.h2.Driver");

        String url = "jdbc:h2:file:C:/Users/Aluno/OneDrive/Documentos/witoria/programas/h2-2024-08-11 (2)/h2/bin/meubd";
        String user = "sa";
        String senha = "";
        
        return DriverManager.getConnection(url, user, senha);
    }
}
