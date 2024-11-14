package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import controller.ConsomeApi;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Solicitando que o usuário informe o limite de preço
        System.out.print("Informe o preço limite: ");
        double precoLimite = scanner.nextDouble(); // Lê o preço limite informado pelo usuário

        ConsomeApi res = new ConsomeApi();
        String str_json = res.obterJson("https://api.escuelajs.co/api/v1/products/");

        JsonArray jsonArray = JsonParser.parseString(str_json).getAsJsonArray();
        // System.out.println(jsonArray);

        List<String> produtos = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            String preco_produto = element.getAsJsonObject().get("price").getAsString();
            try {
                double preco = Double.parseDouble(preco_produto);
                if (preco <= precoLimite) {
                    String nome_produto = element.getAsJsonObject().get("title").getAsString();
                    produtos.add(nome_produto.toUpperCase());
                }
            } catch (NumberFormatException e) {
                // Ignora produtos com preço inválido
                System.out.println("Preço inválido para produto: " + preco_produto);
            }
        }
        produtos.forEach(System.out::println);
    }
}
