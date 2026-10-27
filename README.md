NexaChat
Sistema de mensageria em tempo real desenvolvido em Java para a disciplina de programação, fundamentado na arquitetura cliente-servidor multithread.

Funcionalidades
Comunicação em Rede: Troca de mensagens estruturada via protocolo TCP/IP utilizando as classes Socket e ServerSocket.
Arquitetura Multithread: Gestão de conexões através de fluxos de execução independentes (Threads), permitindo atendimento concorrente sem bloqueios no servidor.
Transmissão Contínua: Tratamento de fluxos de entrada e saída com buffering (BufferedReader e PrintWriter) para leitura e envio de dados em tempo real.
Suporte a Múltiplos Clientes: Operação simultânea com vários terminais conectados partilhando o mesmo ambiente de conversa.
Ciclo de Vida de Sessões: Identificação e controlo dinâmico da entrada, permanência e saída dos utilizadores.

Tecnologias Utilizadas
Linguagem: Java (JDK 17+)
Bibliotecas Padrão: java.net.*, java.io.*, java.lang.Thread
Estruturas Concorrentes: java.util.concurrent.ConcurrentHashMap
Ambiente de Desenvolvimento: IntelliJ IDEA

Como Executar
Pré-requisitos
Java Development Kit (JDK) instalado e configurado no terminal ou na IDE.

Instruções
Clone o repositório ou faça o download dos ficheiros:

Bash
git clone https://github.com/gomes-sam/NexaChat.git
Abra o projeto no IntelliJ IDEA e confirme a configuração do JDK em File > Project Structure > Project.
Execute primeiro a classe Servidor.java para iniciar a escuta de portas na rede local.
Execute a classe Cliente.java. Para simular utilizadores em simultâneo no IntelliJ, ative a opção Allow multiple instances nas configurações de execução (Run > Edit Configurations) e inicie novas instâncias.
Introduza as credenciais ou identificadores solicitados no terminal para iniciar o envio de mensagens.

Autores
Samuel Gomes
Rai Soares