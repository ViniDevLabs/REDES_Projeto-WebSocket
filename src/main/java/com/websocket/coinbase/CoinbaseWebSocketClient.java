package com.websocket.coinbase;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CoinbaseWebSocketClient {
  private static final String COINBASE_WEBSOCKET_URL = "wss://ws-feed.exchange.coinbase.com";

  private CoinbaseWebSocketClient() {
  }

  public static void main(String[] args) {
    try (HttpClient client = HttpClient.newHttpClient(); Scanner scanner = new Scanner(System.in)) {
      System.out.println("WebSocket da Coinbase");
      System.out.println("-------------------------------------------");
      System.out.println("Para encerrar o programa, basta digitar Crtl+C.\n");

      List<String> productIds = new ArrayList<>();
      List<String> channels = new ArrayList<>();

      while (productIds.isEmpty() && channels.isEmpty()) {
        String productIdsInput = "";
        String channelsInput = "";
        System.out.println("Alguns dos IDs de produtos disponíveis para monitoramento são:");
        System.out.println("BTC-USD, ETH-USD, XRP-USD, SOL-USD, SUI-USD, MOODENG-USD, DOGE-USD");
        while (productIdsInput.trim().isEmpty()) {
          System.out.print("Digite os IDs dos produtos separados por vírgula: ");
          if (!scanner.hasNextLine())
            return;
          productIdsInput = scanner.nextLine();
        }

        productIds = Arrays.stream(productIdsInput.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();

        System.out.println("\nCanais disponíveis: ticker, ticker_batch");

        while (channelsInput.trim().isEmpty()) {
          System.out.print("Digite os canais desejados separados por vírgula: ");
          if (!scanner.hasNextLine())
            return;
          channelsInput = scanner.nextLine();
        }

        channels = Arrays.stream(channelsInput.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();

        if (productIds.isEmpty()) {
          System.out.println("É necessário fornecer ao menos um ID de produto.");
        }

        if (channels.isEmpty()) {
          System.out.println("É necessário fornecer ao menos um canal.");
        }
      }

      WebSocket webSocket = client.newWebSocketBuilder()
          .buildAsync(URI.create(COINBASE_WEBSOCKET_URL), new CoinbaseWebSocketListener())
          .join();

      String productsIdsString = String.join("\", \"", productIds);
      String channelsString = String.join("\", \"", channels);
      String subscribeMessage = String.format("""
          {
            "type": "subscribe",
            "product_ids": ["%s"],
            "channels": ["%s"]
          }
          """, productsIdsString, channelsString);

      webSocket.sendText(subscribeMessage, true);
      System.out.println("Mensagem de assinatura enviada.");

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        System.out.println("Encerrando conexão WebSocket...");
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Encerrando").join();
        System.out.println("Conexão WebSocket encerrada.");
      }));

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Erro: " + e.getMessage());
      Thread.currentThread().interrupt();
    }

  }

}
