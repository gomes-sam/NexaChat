# NexaChat

Aplicação de chat em tempo real desenvolvida em Java, utilizando sockets TCP para permitir a comunicação entre vários clientes conectados a um servidor.

O projeto oferece mensagens públicas e privadas diretamente pelo terminal, além de um comando para listar os usuários conectados.

## Funcionalidades

- Conexão de vários clientes simultaneamente.
- Envio de mensagens públicas para todos os clientes conectados.
- Envio de mensagens privadas para um destinatário específico.
- Listagem dos usuários conectados por meio do comando `/listar`.
- Identificação dos usuários por nome.
- Validação de nomes duplicados.
- Avisos para destinatários que não estão conectados.
- Recebimento de mensagens em paralelo com a digitação.
- Desconexão de clientes usando um comando no terminal.

## 🛠️ Tecnologias utilizadas

- Java
- Sockets TCP (`ServerSocket` e `Socket`)
- Threads para conexões simultâneas
- `ConcurrentHashMap` para gerenciar os clientes conectados
- IntelliJ IDEA
- Git e GitHub

## Estrutura do projeto

```text
NexaChat/
├── src/
│   ├── Cliente.java
│   ├── Servidor.java
│   └── msgPrivada/
│       └── MensagemPrivada.java
└── README.md
```

### Responsabilidade das classes

**`Servidor.java`**

- Aguarda novas conexões na porta 5000.
- Cria uma thread para cada cliente.
- Registra e remove os clientes conectados.
- Distribui mensagens públicas.
- Encaminha mensagens privadas ao destinatário.
- Lista os usuários conectados quando recebe o comando `/listar`.

**`Cliente.java`**

- Conecta-se ao servidor.
- Solicita o nome do usuário.
- Envia mensagens digitadas no terminal.
- Recebe mensagens sem bloquear a digitação.
- Permite enviar mensagens públicas e privadas.
- Permite listar os usuários conectados.
- Permite desconectar-se do chat.

**`privada/MensagemPrivada.java`**

- Interpreta o comando `/msg`.
- Identifica o destinatário.
- Separa o nome do destinatário do conteúdo da mensagem.

## Pré-requisitos

- JDK instalado e configurado.
- IntelliJ IDEA ou outra IDE Java.
- Porta TCP 5000 disponível no computador que executará o servidor.

## Como executar

### 1. Clonar e abrir o projeto

```bash
git clone https://github.com/gomes-sam/NexaChat.git
cd NexaChat
```

Abra a pasta do projeto no IntelliJ IDEA e configure o JDK.

### 2. Iniciar o servidor

Execute a classe `Servidor.java`.

O servidor ficará aguardando conexões na porta 5000.

**Execute o servidor apenas uma vez.**

### 3. Iniciar os clientes

Execute a classe `Cliente.java` em diferentes instâncias.

No IntelliJ IDEA:

1. Acesse `Run → Edit Configurations...`.
2. Selecione a configuração `Cliente`.
3. Em `Modify options`, habilite `Allow multiple instances`, se disponível.
4. Clique em `Apply` e `OK`.
5. Execute o cliente várias vezes.

Informe um nome diferente em cada terminal.

Para testar no mesmo computador, pressione Enter quando o cliente solicitar o IP do servidor. O endereço padrão será `127.0.0.1`.

## Comandos disponíveis

| Comando ou entrada | Descrição |
|---|---|
| `Olá, pessoal!` | Envia uma mensagem pública para todos os clientes conectados. |
| `/msg Samuel Oi, tudo bem?` | Envia uma mensagem privada para Samuel. |
| `/msg Carlos Olá!` | Envia uma mensagem privada para Carlos, caso esteja conectado. |
| `/listar` | Exibe a lista de usuários conectados. |
| `/sair` | Encerra a conexão do cliente. |

Qualquer texto que não seja reconhecido como comando será tratado como mensagem pública, conforme as regras implementadas no servidor.

## Como funcionam as mensagens privadas?

Quando um usuário digita:

```text
/msg Samuel Oi, tudo bem?
```

O servidor interpreta o comando, procura o destinatário pelo nome e encaminha o conteúdo somente para ele.

O remetente também recebe uma confirmação da mensagem enviada.

Se o destinatário não estiver conectado, o servidor informa que o envio não foi possível.

As mensagens privadas são encaminhadas pelo servidor, mas não possuem criptografia de ponta a ponta nesta versão.

## Como funciona o comando `/listar`?

Quando um usuário digita:

```text
/listar
```

O servidor deve consultar os clientes conectados e retornar a lista ao usuário que solicitou o comando.

Exemplo de resposta:

```text
[ Sistema ] Usuários conectados:
- Rai
- Samuel
- Maria
```

A lista deve refletir as conexões ativas no momento da solicitação.

## Testes recomendados

### Teste 1 — Conexões simultâneas

1. Inicie o servidor.
2. Conecte três clientes com nomes diferentes.
3. Verifique no terminal do servidor se os três foram registrados.

### Teste 2 — Mensagens públicas

Envie uma mensagem por qualquer cliente e confirme que os demais a recebem.

### Teste 3 — Mensagens privadas

No terminal de Rai, digite:

```text
/msg Samuel Mensagem de teste
```

Verifique se apenas Rai e Samuel visualizam o conteúdo privado.

### Teste 4 — Listagem de usuários

Digite:

```text
/listar
```

Confira se o servidor retorna os nomes dos clientes conectados.

### Teste 5 — Destinatário desconectado

Envie uma mensagem privada para um nome que não esteja conectado e confirme que o remetente recebe um aviso.

### Teste 6 — Desconexão

Digite `/sair` e verifique se o servidor registra a saída do cliente.

## Conexão entre computadores

Para conectar clientes de computadores diferentes:

1. Execute o servidor em um computador acessível pela rede.
2. Descubra o endereço IP local desse computador.
3. Permita conexões TCP pela porta 5000 no firewall, conforme necessário.
4. Informe o IP do servidor ao iniciar cada cliente.

Os computadores precisam ter conectividade de rede entre si. Para conexões pela internet, também será necessário configurar uma forma segura de acesso ao servidor.

## Melhorias futuras

- Histórico de mensagens.
- Interface gráfica.
- Autenticação de usuários.
- Criptografia das comunicações.
- Salas de conversa.
- Notificações de entrega e leitura.

## Objetivo do projeto

O NexaChat tem como objetivo estudar comunicação em rede com Java, programação concorrente, gerenciamento de conexões TCP e troca de mensagens entre múltiplos clientes.

---

Desenvolvido como projeto de estudo em Java.