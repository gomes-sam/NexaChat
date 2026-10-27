
package msgPrivada;

public class MensagemPrivada {

    private final String destinatario;
    private final String conteudo;

    public MensagemPrivada(String destinatario, String conteudo) {
        this.destinatario = destinatario;
        this.conteudo = conteudo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public static MensagemPrivada interpretar(String texto) {
        if (texto == null || !texto.trim().startsWith("/msg ")) {
            return null;
        }

        String[] partes = texto.trim().split("\\s+", 3);

        if (partes.length < 3) {
            return null;
        }

        String destinatario = partes[1].trim();
        String conteudo = partes[2].trim();

        if (destinatario.isEmpty() || conteudo.isEmpty()) {
            return null;
        }

        return new MensagemPrivada(destinatario, conteudo);
    }
}
