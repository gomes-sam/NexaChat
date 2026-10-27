
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Cliente {

    private static final int PORTA = 5000;

    public static void main(String[] args) {
        BufferedReader teclado = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("=== NexaChat - Cliente ===");

        try {
            System.out.print("IP do servidor (Enter para 127.0.0.1): ");
            String ip = teclado.readLine();

            if (ip == null || ip.trim().isEmpty()) {
                ip = "127.0.0.1";
            }

            try (Socket socket = new Socket(ip.trim(), PORTA);
                 BufferedReader entrada = new BufferedReader(
                         new InputStreamReader(
                                 socket.getInputStream(),
                                 StandardCharsets.UTF_8));
                 PrintWriter saida = new PrintWriter(
                         socket.getOutputStream(), true,
                         StandardCharsets.UTF_8)) {

                String solicitacaoNome = entrada.readLine();
                System.out.println(solicitacaoNome);

                System.out.print("Seu nome: ");
                String nome = teclado.readLine();

                if (nome == null || nome.trim().isEmpty()) {
                    System.out.println("Nome invalido.");
                    return;
                }

                saida.println(nome.trim());

                Thread receptor = new Thread(() -> {
                    try {
                        String mensagemServidor;

                        while ((mensagemServidor = entrada.readLine()) != null) {
                            System.out.println("\n" + mensagemServidor);
                            System.out.print("> ");
                        }

                        System.out.println("\nConexao encerrada pelo servidor.");

                    } catch (IOException e) {
                        System.out.println("\nConexao encerrada.");
                    }
                });

                receptor.setDaemon(true);
                receptor.start();

                System.out.println("\nComandos do NexaChat:");
                System.out.println("  Digite uma mensagem para o chat publico.");
                System.out.println("  /msg NOME mensagem - Envia mensagem privada.");
                System.out.println("  /sair - Sai do chat.");
                System.out.println();

                String mensagem;

                while ((mensagem = teclado.readLine()) != null) {
                    if (mensagem.trim().isEmpty()) {
                        continue;
                    }

                    saida.println(mensagem);

                    if (mensagem.trim().equalsIgnoreCase("/sair")) {
                        break;
                    }

                    System.out.print("> ");
                }
            }

        } catch (IOException e) {
            System.err.println(
                    "Nao foi possivel conectar ao servidor: " + e.getMessage());
            System.err.println("Verifique se o Servidor esta iniciado.");
        }
    }
}
