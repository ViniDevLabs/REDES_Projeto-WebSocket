# DIM0438 - Redes de Computadores

## Descrição

Este projeto foi desenvolvido para a disciplina **DIM0438 - Redes de Computadores** (UFRN). Trata-se de um cliente WebSocket em Java que se conecta ao servidor WebSocket da Coinbase para receber atualizações em tempo real do mercado de criptomoedas. A aplicação permite ao usuário escolher quias produtos (pares de moedas) e canais deseja monitorar, recebendo atualizações em tempo real conforme o feed da Coinbase.

## Funcionalidades

- Conexão via WebSocket ao servidor oficial da Coinbase.
- Inscrição dinâmica em produtos (exemplo: BTC-USD, ETH-USD) e canais (exemplo: ticker, ticker_batch).
- Recebimento e exibição formatada das mensagens JSON enviadas pelo servidor.
- Encerramento seguro da conexão via comando Ctrl+C.
- Suporte para múltiplas instâncias do cliente rodando simultaneamente.

## Compilação e execução

Para realizar o build, utilize o comando:

```bash
mvn clean package
```

Após isso, para executar o WebSocket Cliente, utilize o comando:

```bash
java -jar target/coinbase_websocket-1.0.jar
```

Durante a execução, o cliente solicitará que você informe os IDs dos produtos e os canais que deseja monitorar, separados por vírgula.

## Exemplo de Uso

1. Execute o cliente.
2. Quando solicitado, informe os IDs dos produtos que deseja acompanhar (ex: BTC-USD, ETH-USD).
3. Informe os canais desejados (ex: ticker).
4. O cliente irá se inscrever no feed da Coinbase e exibirá as mensagens recebidas em tempo real.
5. Para encerrar a aplicação e fechar a conexão WebSocket, pressione Ctrl+C.

## Documentação relacionada

WebSockets em Java:

1. [Exemplo oficial WebSocket com HttpClient](https://github.com/twosigma/OpenJDK/blob/master/test/jdk/java/net/httpclient/examples/WebSocketExample.java)
2. [API java.net.http.WebSocket](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/WebSocket.html)
3. [API java.net.http.WebSocket.Listener](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/WebSocket.Listener.html)

WebSocket da Coinbase:

1. [Visão geral do WebSocket da Coinbase](https://docs.cdp.coinbase.com/exchange/docs/websocket-overview)
2. [Documentação do WebSocket da Coinbase](https://docs.cdp.coinbase.com/coinbase-app/docs/trade/ws-overview)
3. [Guia de configuração do WebSocket da Coinbase](https://docs.cdp.coinbase.com/coinbase-app/docs/trade/ws-setup-guide)
