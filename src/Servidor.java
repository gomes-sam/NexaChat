import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {

    private static final int PORTA = 5000;

    private static final ConcurrentHashMap<String, ClienteHandler> clientes =
            new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("=== NexaChat - Servidor ===");

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            System.out.println("Aguardando conexoes...\n");

            while (true) {
                Socket socket = servidor.accept();

                ClienteHandler cliente = new ClienteHandler(socket);
                Thread thread = new Thread(cliente);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static synchronized boolean adicionarCliente(
            String nome, ClienteHandler cliente) {

        if (clientes.containsKey(nome)) {
            return false;
        }

        clientes.put(nome, cliente);
        return true;
    }

    private static void removerCliente(
            String nome, ClienteHandler cliente) {

        if (nome != null && clientes.remove(nome, cliente)) {
            System.out.println(nome + " desconectou.");
            enviarMensagem("[Sistema] " + nome + " saiu do chat.");
        }
    }

    private static void enviarMensagem(String mensagem) {
        for (ClienteHandler cliente : clientes.values()) {
            cliente.enviar(mensagem);
        }
    }

    private static class ClienteHandler implements Runnable {

        private final Socket socket;
        private PrintWriter saida;
        private String nome;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8));

                saida = new PrintWriter(
                        socket.getOutputStream(), true,
                        StandardCharsets.UTF_8);

                saida.println("Digite seu nome:");

                nome = entrada.readLine();

                if (nome == null || nome.trim().isEmpty()
                        || nome.length() > 20
                        || nome.contains("[Sistema]")) {
                    saida.println("ERRO: nome invalido.");
                    return;
                }

                nome = nome.trim();

                if (!adicionarCliente(nome, this)) {
                    saida.println("ERRO: esse nome ja esta em uso.");
                    return;
                }

                System.out.println(nome + " conectou.");
                saida.println("[Sistema] Bem-vindo ao NexaChat, " + nome + "!");
                enviarMensagem("[Sistema] " + nome + " entrou no chat.");

                String mensagem;

                while ((mensagem = entrada.readLine()) != null) {
                    if (mensagem.equalsIgnoreCase("/sair")) {
                        break;
                    }

                    if (!mensagem.trim().isEmpty()) {
                        String mensagemFormatada = nome + ": " + mensagem;
                        System.out.println(mensagemFormatada);
                        enviarMensagem(mensagemFormatada);
                    }
                }

            } catch (IOException e) {
                System.out.println("Conexao encerrada.");
            } finally {
                removerCliente(nome, this);

                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar conexao.");
                }
            }
        }

        public synchronized void enviar(String mensagem) {
            if (saida != null) {
                saida.println(mensagem);
            }
        }
    }
}