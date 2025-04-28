import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // VARIABLES
        double valorAConvertir = 1;
        String monedaBase = "";
        String monedaConversion = "";

        // ENTRADA DE DATOS
        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.US);

        try {
        // ENTRADAS USUARIO
        // Moneda Base
        System.out.println("""
                            ==========================================
                            
                            Ingrese la moneda base
                            Recuerde que las monedas disponibles son:
                                🔹 USD (Dólar estadounidense)
                                🔹 EUR (Euro)
                                🔹 COP (Peso colombiano)
                                🔹 GBP (Libra esterlina)
                                🔹 JPY (Yen japonés)
                                🔹 CAD (Dólar canadiense)
                                🔹 AUD (Dólar australiano)
                                🔹 MXN (Peso mexicano)
                                🔹 CNY (Yuan chino)
                                
                            ==========================================
                            """);

        // EVALUAMOS DATO
        monedaBase = obtenerMoneda(input);

        // Valor a covvertir
        while (true){
            try{
                System.out.println(String.format("Cuantos %s desea convertir?", monedaBase));
                valorAConvertir = input.nextDouble();
                input.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.println("Debes ingresar un valor valido");
                input.nextLine();
            }
        }

        System.out.println("""
                            ==========================================
                            
                            Ingrese la moneda a la que desea hacer la conversión
                            Recuerde que las monedas disponibles son:
                                🔹 USD (Dólar estadounidense)
                                🔹 EUR (Euro)
                                🔹 COP (Peso colombiano)
                                🔹 GBP (Libra esterlina)
                                🔹 JPY (Yen japonés)
                                🔹 CAD (Dólar canadiense)
                                🔹 AUD (Dólar australiano)
                                🔹 MXN (Peso mexicano)
                                🔹 CNY (Yuan chino)
                                
                            ==========================================
                            """);

        // EVALUAMOS DATO
        monedaConversion = obtenerMoneda(input);

        // DEFINIMOS LA URL
        final String KEY = "e8dff7b80d91b314a238b445";
        final String URL = "https://v6.exchangerate-api.com/v6/"+ KEY + "/latest/" + monedaBase;
        // CREAMOS EL CLIENTE
        HttpClient client = HttpClient.newHttpClient();
        // CREAMOS EL REQUERIMIENTO
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        // CÓDIGO DE LA RESPUESTA (ENVÍO DE SOLICITUD Y OBTENCIÓN DE RESPUESTA)

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString()); // BodyHandlers.ofString() -> es un manejador de cuerpo (body handler) que convierte el contenido de la respuesta en un String. Sin embargo, esto solo define cómo manejar el cuerpo de la respuesta al obtenerla, y no lo convierte automáticamente en un String
            // OBTENER EL CUERPO DE LA RESPUESTA EN FORMATO STRING
            String responseBody = response.body(); // ya que BodyHandlers.ofString() solo indica al HttpClient que debe manejar la respuesta y convertirla en una cadena de texto (String). Pero el contenido real de la respuesta se guarda en el HttpResponse

            if (response.statusCode() != 200) {
                System.out.println("Error al obtener los datos de la API. Código de estado: " + response.statusCode());
                return; // Cerramos el método main si el código de estado de la respuesta no es 200
            }

            // ANALIZANDO LA RESPUESTA DEL FORMATO JSON (GSON)
            // Deserealizamos
            Gson gson = new Gson();
            DatosConversorDeMonedas info = gson.fromJson(responseBody, DatosConversorDeMonedas.class);
            ConversorDeMonedas conversor = new ConversorDeMonedas(info);

            // CÁLCULO E IMPRESIÓN
            Double tasaCambio = conversor.getMonedas().get(monedaConversion);

            double valorConversion = valorAConvertir * tasaCambio;
            System.out.println(String.format(Locale.US, "%,.2f %s equivalen a %,.2f %s", valorAConvertir, monedaBase, valorConversion, monedaConversion));
            System.out.println("La actualizacion de la converion es del " + conversor.getFechaActualizacion());

        } catch (IOException e) {
            System.out.println("Error de red o entrada/salida: " + e.getMessage()); // optenemos el mensaje de la excepción
            e.printStackTrace(); // Para imprimir  toda la traza de la pila de la excepción
        } catch (InterruptedException e) {
            System.out.println("La solicitud fue interrumpida: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Lo siento, caiste en un error: " + e);
        } finally {
            input.close();

        }
    }

    private static String obtenerMoneda(Scanner input) {
        while (true) {
            try {
                String moneda = input.nextLine().toUpperCase();
                MonedasDisponibles monedaValida = MonedasDisponibles.valueOf(moneda);
                return moneda;
            } catch (IllegalArgumentException e) {
                System.out.println("Moneda no válida. Por favor ingrese una moneda válida.");
            }
        }
    }
}

