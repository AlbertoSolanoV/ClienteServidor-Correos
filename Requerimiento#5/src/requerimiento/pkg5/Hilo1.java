package requerimiento.pkg5;

public class Hilo1 extends Thread {

    ClaseCorreo correo = new ClaseCorreo();
    ServerCorreos serverlista = new ServerCorreos();
    int numeroh = 0;
    int finh = 0;

    public Hilo1(int numero, int fin) {
        numeroh = numero;
        finh = fin;

    }

    public void run() {
        try {
            do {
                correo.mandarCorreoATodos((String) serverlista.listaContactos.get(numeroh));
                numeroh++;
            } while (numeroh != finh);

        } catch (Exception e) {
            
        }

    }
}
