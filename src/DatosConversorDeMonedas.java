import java.util.Map;

public record DatosConversorDeMonedas(String result, String time_last_update_utc, Map<String, Double> conversion_rates){}
