package com.websocket.coinbase;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CoinbaseWebSocketListener implements WebSocket.Listener {

  private static ObjectMapper mapper = new ObjectMapper();
  private static ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
  private int messageCount = 1;

  @Override
  public void onOpen(WebSocket webSocket) {
    System.out.println("Conectado ao WebSocket da Coinbase");
    WebSocket.Listener.super.onOpen(webSocket);
  }

  @Override
  public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
    System.out.println(messageCount + " - Mensagem recebida: ");
    messageCount++;
    try {
      Object json = mapper.readValue(data.toString(), Object.class);
      String prettyJson = writer.writeValueAsString(json);
      System.out.println(prettyJson);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      System.err.println("Erro: " + e.getMessage());
    }

    return WebSocket.Listener.super.onText(webSocket, data, last);
  }

  @Override
  public void onError(WebSocket webSocket, Throwable error) {
    System.out.println("Erro ao processar JSON: " + error.getMessage());
    WebSocket.Listener.super.onError(webSocket, error);
  }

  @Override
  public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
    System.out.println("Conexão fechada. Código: " + statusCode + ", Motivo: " + reason);
    return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
  }
}
