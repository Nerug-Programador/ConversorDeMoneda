import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

// Clase principal que maneja el menú y la interacción con el usuario
public class CurrencyConverter {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/516aeee7b7cac871ef25b53a/latest/USD";
    private static List<CurrencyConversion> conversions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        try {
            // Obtener las tasas de cambio desde la API
            JSONObject exchangeRates = getExchangeRates();
            double usdToArs = exchangeRates.getJSONObject("conversion_rates").getDouble("ARS");
            double usdToBrl = exchangeRates.getJSONObject("conversion_rates").getDouble("BRL");
            double usdToCop = exchangeRates.getJSONObject("conversion_rates").getDouble("COP");
            double usdToBob = exchangeRates.getJSONObject("conversion_rates").getDouble("BOB");
            double usdToClp = exchangeRates.getJSONObject("conversion_rates").getDouble("CLP");

            // Crear objetos de conversión y agregarlos a la lista
            conversions.add(new UsdToArsConversion(usdToArs));
            conversions.add(new ArsToUsdConversion(usdToArs));
            conversions.add(new UsdToBrlConversion(usdToBrl));
            conversions.add(new BrlToUsdConversion(usdToBrl));
            conversions.add(new UsdToCopConversion(usdToCop));
            conversions.add(new CopToUsdConversion(usdToCop));
            conversions.add(new UsdToBobConversion(usdToBob));
            conversions.add(new BobToUsdConversion(usdToBob));
            conversions.add(new UsdToClpConversion(usdToClp));
            conversions.add(new ClpToUsdConversion(usdToClp));
        } catch (IOException e) {
            System.out.println("Error fetching exchange rates: " + e.getMessage());
            return;
        }

        do {
            // Mostrar el menú de opciones al usuario
            System.out.println("*********************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda");
            System.out.println("");
            System.out.println("1) Dolar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dolar");
            System.out.println("3) Dolar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dolar");
            System.out.println("5) Dolar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dolar");
            System.out.println("7) Dolar =>> Boliviano");
            System.out.println("8) Boliviano =>> Dolar");
            System.out.println("9) Dolar =>> Peso chileno");
            System.out.println("10) Peso chileno =>> Dolar");
            System.out.println("11) Salir");
            System.out.println("Elija una opción válida: ");
            System.out.println("*********************************************************");

            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 10) {
                conversions.get(opcion - 1).convert(scanner);
            } else if (opcion == 11) {
                System.out.println("Gracias por usar el Conversor de Moneda. ¡Hasta luego!");
            } else {
                System.out.println("Opción no válida. Por favor, elija una opción del 1 al 11.");
            }
        } while (opcion != 11);

        scanner.close();
    }

    private static JSONObject getExchangeRates() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        StringBuilder json = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            json.append((char) c);
        }
        reader.close();

        return new JSONObject(json.toString());
    }
}

// Clase abstracta para las conversiones de moneda
abstract class CurrencyConversion {
    protected double rate;

    public CurrencyConversion(double rate) {
        this.rate = rate;
    }

    public abstract void convert(Scanner scanner);
}

// Subclase para convertir de USD a ARS
class UsdToArsConversion extends CurrencyConversion {
    public UsdToArsConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Dólares: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * rate;
        System.out.println("Equivalente en Pesos Argentinos: " + resultado);
    }
}

// Subclase para convertir de ARS a USD
class ArsToUsdConversion extends CurrencyConversion {
    public ArsToUsdConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Pesos Argentinos: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad / rate;
        System.out.println("Equivalente en Dólares: " + resultado);
    }
}

// Subclase para convertir de USD a BRL
class UsdToBrlConversion extends CurrencyConversion {
    public UsdToBrlConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Dólares: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * rate;
        System.out.println("Equivalente en Reales Brasileños: " + resultado);
    }
}

// Subclase para convertir de BRL a USD
class BrlToUsdConversion extends CurrencyConversion {
    public BrlToUsdConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Reales Brasileños: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad / rate;
        System.out.println("Equivalente en Dólares: " + resultado);
    }
}

// Subclase para convertir de USD a COP
class UsdToCopConversion extends CurrencyConversion {
    public UsdToCopConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Dólares: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * rate;
        System.out.println("Equivalente en Pesos Colombianos: " + resultado);
    }
}

// Subclase para convertir de COP a USD
class CopToUsdConversion extends CurrencyConversion {
    public CopToUsdConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Pesos Colombianos: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad / rate;
        System.out.println("Equivalente en Dólares: " + resultado);
    }
}

// Subclase para convertir de USD a BOB
class UsdToBobConversion extends CurrencyConversion {
    public UsdToBobConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Dólares: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * rate;
        System.out.println("Equivalente en Bolivianos: " + resultado);
    }
}

// Subclase para convertir de BOB a USD
class BobToUsdConversion extends CurrencyConversion {
    public BobToUsdConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Bolivianos: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad / rate;
        System.out.println("Equivalente en Dólares: " + resultado);
    }
}

// Subclase para convertir de USD a CLP
class UsdToClpConversion extends CurrencyConversion {
    public UsdToClpConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Dólares: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * rate;
        System.out.println("Equivalente en Pesos Chilenos: " + resultado);
    }
}

// Subclase para convertir de CLP a USD
class ClpToUsdConversion extends CurrencyConversion {
    public ClpToUsdConversion(double rate) {
        super(rate);
    }

    @Override
    public void convert(Scanner scanner) {
        System.out.print("Ingrese la cantidad en Pesos Chilenos: ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad / rate;
        System.out.println("Equivalente en Dólares: " + resultado);
    }
}
