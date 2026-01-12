class ControladorIluminacion {
    private final SensorLuz sensor;
    private final Reloj reloj;
    private final Interruptor interruptor;

    // Inyectamos las dependencias para poder sustituirlas por Mocks en los tests
    public ControladorIluminacion(SensorLuz sensor, Reloj reloj, Interruptor interruptor) {
        this.sensor = sensor;
        this.reloj = reloj;
        this.interruptor = interruptor;
    }

    public void actualizarEstado() {
        try {
            int lux = sensor.obtenerLux();
            int hora = reloj.obtenerHora();

            // REGLA: Poca luz (<50) Y es de noche (entre las 18:00 y las 08:00)
            boolean esOscuro = lux < 50;
            boolean esDeNoche = (hora >= 18 || hora < 8);

            if (esOscuro && esDeNoche) {
                interruptor.encender();
            } else {
                interruptor.apagar();
            }
        } catch (Exception e) {
            // Si algo falla (el sensor se rompe), apagamos por seguridad
            interruptor.apagar();
        }
    }
}