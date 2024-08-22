package dados;

import java.util.HashMap;
import java.util.Map;

public class Dados {
    private static Map<String, String> usuariosSenha;  // Mapa para armazenar nomes de usuário e senhas

    static {
        usuariosSenha = new HashMap<>();
        // Você pode adicionar alguns usuários e senhas padrão para teste
        usuariosSenha.put("usuario1", "senha1");
        usuariosSenha.put("usuario2", "senha2");
    }

    public static boolean validarCredenciais(String usuario, String senha) {
        // Verifica se o nome de usuário e a senha fornecidos correspondem aos dados armazenados
        return usuariosSenha.containsKey(usuario) && usuariosSenha.get(usuario).equals(senha);
    }

    public static void cadastrarUsuario(String usuario, String senha) {
        // Adiciona um novo usuário ao "banco de dados"
        usuariosSenha.put(usuario, senha);
    }

    public static void excluirUsuario(String usuario) {
        // Remove um usuário do "banco de dados"
        usuariosSenha.remove(usuario);
    }
}
