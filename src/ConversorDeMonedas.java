import java.util.Map;

public class ConversorDeMonedas {
    private String estado;
    private String fechaActualizacion;
    private Map<String, Double> monedas;

    public ConversorDeMonedas(DatosConversorDeMonedas datos) {
        this.estado = datos.result();
        this.fechaActualizacion = datos.time_last_update_utc();
        this.monedas = datos.conversion_rates();
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion.substring(5, 25);
    }

    public Map<String, Double> getMonedas() {
        return monedas;
    }
}
