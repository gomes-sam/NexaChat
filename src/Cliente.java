import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {

    private static volatile boolean conectado = true;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== NexaChat - Cliente ===");
        System.out.print("IP do servidor (Enter para localhost): ");

        String endereco = teclado.nextLine().trim();

        if (endereco.isEmpty()) {
            endereco = "127.0.0.1";
        }

        try (Socket socket = new Socket(endereco, 5000)) {

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.UTF_8));

            PrintWriter saida = new PrintWriter(
                    socket.getOutputStream(), true,
                    StandardCharsets.UTF_8);

            System.out.println(entrada.readLine());

            System.out.print("Seu nome: ");
            String nome = teclado.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("O nome nao pode ficar vazio.");
                return;
            }

            saida.println(nome);

            Thread receptor = new Thread(() -> {
                try {
                    String mensagem;

                    while ((mensagem = entrada.readLine()) != null) {
                        System.out.println(mensagem);

                        if (mensagem.startsWith("ERRO:")) {
                            conectado = false;
                            break;
                        }
                    }

                } catch (IOException e) {
                    if (conectado) {
                        System.out.println("Conexao com o servidor encerrada.");
                    }
                } finally {
                    conectado = false;
                }
            });

            receptor.start();

            System.out.println("Digite suas mensagens.");
            System.out.println("Para sair, digite /sair.\n");

            while (conectado && teclado.hasNextLine()) {
                String mensagem = teclado.nextLine();

                if (mensagem.trim().isEmpty()) {
                    continue;
                }

                saida.println(mensagem);

                if (mensagem.equalsIgnoreCase("/sair")) {
                    conectado = false;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Nao foi possivel conectar ao servidor: "
                            + e.getMessage());
        } finally {
            conectado = false;
            System.out.println("Cliente encerrado.");
        }
    }
}