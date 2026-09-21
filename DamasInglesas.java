import java.util.Scanner;
import java.util.ArrayList;

public class DamasInglesas {

    static Scanner entrada = new Scanner(System.in);
    static char[][] tablero = new char[8][8];
    static String[] movimientos = new String[1000];
    static int cantidadMovimientos = 0;
    static char jugadorActual = 'X';

    // Códigos ANSI de alta intensidad para visibilidad en FONDO OSCURO
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String COLOR_X = "\u001B[91m"; // Rojo brillante para Jugador 1 (X)
    public static final String COLOR_O = "\u001B[96m"; // Cian brillante para Jugador 2 (O)

    // Clase auxiliar para representar una acción disponible
    static class Accion {
        int filaDestino;
        int columnaDestino;
        boolean esCaptura;
        String descripcion;

        Accion(int filaDestino, int columnaDestino, boolean esCaptura, String descripcion) {
            this.filaDestino = filaDestino;
            this.columnaDestino = columnaDestino;
            this.esCaptura = esCaptura;
            this.descripcion = descripcion;
        }
    }

    public static void main(String[] args) {

        inicializarTablero();

        boolean partidaTerminada = false;

        System.out.println("====================================");
        System.out.println("          DAMAS INGLESAS");
        System.out.println("====================================");
        System.out.println("Jugador 1: fichas " + COLOR_X + "X" + ANSI_RESET);
        System.out.println("Jugador 2: fichas " + COLOR_O + "O" + ANSI_RESET);
        System.out.println();

        while (!partidaTerminada) {

            mostrarTablero();

            System.out.println();
            String colorJugador = (jugadorActual == 'X') ? COLOR_X : COLOR_O;
            System.out.println("Turno del jugador: " + colorJugador + jugadorActual + ANSI_RESET);
            System.out.println();

            boolean movimientoRealizado = realizarMovimiento();

            if (movimientoRealizado) {

                if (!hayMovimientosDisponibles(cambiarJugador(jugadorActual))) {

                    mostrarTablero();

                    System.out.println();
                    System.out.println("************************************");
                    System.out.println("          PARTIDA TERMINADA");
                    System.out.println("************************************");
                    System.out.println("Ganador: jugador " + colorJugador + jugadorActual + ANSI_RESET);
                    System.out.println();

                    partidaTerminada = true;
                }
                else {
                    jugadorActual = cambiarJugador(jugadorActual);
                }
            }
        }

        preguntarGuardarPartida();

        System.out.println();
        System.out.println("---------------fin del programa--------------");

        entrada.close();
    }

    public static void inicializarTablero() {
        int fila;
        int columna;
        for (fila = 0; fila < 8; fila++) {
            for (columna = 0; columna < 8; columna++) {
                tablero[fila][columna] = ' ';
            }
        }
        for (fila = 0; fila < 3; fila++) {
            for (columna = 0; columna < 8; columna++) {
                if ((fila + columna) % 2 == 1) {
                    tablero[fila][columna] = 'O';
                }
            }
        }

        for (fila = 5; fila < 8; fila++) {
            for (columna = 0; columna < 8; columna++) {
                if ((fila + columna) % 2 == 1) {
                    tablero[fila][columna] = 'X';
                }
            }
        }
    }

    public static void mostrarTablero() {

        System.out.println();
        System.out.println("       1   2   3   4   5   6   7   8");
        System.out.println("     +---+---+---+---+---+---+---+---+");

        for (int fila = 0; fila < 8; fila++) {

            System.out.print(" " + (fila + 1) + "   |");

            for (int columna = 0; columna < 8; columna++) {
                char c = tablero[fila][columna];
                String representacion = " " + c + " ";

                if (c == 'X' || c == 'x') {
                    representacion = COLOR_X + " " + c + " " + ANSI_RESET;
                } else if (c == 'O' || c == 'o') {
                    representacion = COLOR_O + " " + c + " " + ANSI_RESET;
                }

                System.out.print(representacion + "|");
            }

            System.out.println();
            System.out.println("     +---+---+---+---+---+---+---+---+");
        }

        System.out.println();
    }

    public static boolean realizarMovimiento() {

        int filaOrigen;
        int columnaOrigen;

        System.out.print("Fila inicial de la ficha: ");
        filaOrigen = entrada.nextInt() - 1;

        System.out.print("Columna inicial de la ficha: ");
        columnaOrigen = entrada.nextInt() - 1;

        if (!posicionValida(filaOrigen, columnaOrigen)) {
            System.out.println("Posicion fuera del tablero.");
            return false;
        }

        if (tablero[filaOrigen][columnaOrigen] != jugadorActual &&
            tablero[filaOrigen][columnaOrigen] != Character.toLowerCase(jugadorActual)) {
            System.out.println("No hay una ficha tuya en esa posicion.");
            return false;
        }

        // Obtener las acciones posibles para la ficha seleccionada
        ArrayList<Accion> accionesDisponibles = obtenerAccionesDisponibles(filaOrigen, columnaOrigen);

        if (accionesDisponibles.isEmpty()) {
            System.out.println("La ficha seleccionada no tiene movimientos posibles.");
            return false;
        }

        // Desplegar menú de posibles movimientos
        System.out.println("\nSelecciona la accion a realizar:");
        for (int i = 0; i < accionesDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + accionesDisponibles.get(i).descripcion);
        }

        System.out.print("Opcion: ");
        int opcion = entrada.nextInt();

        if (opcion < 1 || opcion > accionesDisponibles.size()) {
            System.out.println("Opcion invalida.");
            return false;
        }

        Accion accionElegida = accionesDisponibles.get(opcion - 1);

        int filaDestino = accionElegida.filaDestino;
        int columnaDestino = accionElegida.columnaDestino;
        boolean esCaptura = accionElegida.esCaptura;

        if (esCaptura) {
            int filaMedia = (filaOrigen + filaDestino) / 2;
            int columnaMedia = (columnaOrigen + columnaDestino) / 2;
            tablero[filaMedia][columnaMedia] = ' ';
        }

        tablero[filaDestino][columnaDestino] = tablero[filaOrigen][columnaOrigen];
        tablero[filaOrigen][columnaOrigen] = ' ';

        coronarFicha(filaDestino, columnaDestino);

        guardarMovimiento(
                filaOrigen,
                columnaOrigen,
                filaDestino,
                columnaDestino,
                esCaptura
        );

        if (esCaptura) {
            continuarCaptura(filaDestino, columnaDestino);
        }

        return true;
    }

    public static ArrayList<Accion> obtenerAccionesDisponibles(int fila, int columna) {
        ArrayList<Accion> acciones = new ArrayList<>();
        char ficha = tablero[fila][columna];

        int[] df;
        if (ficha == 'X') {
            df = new int[]{-1}; // X normal avanza hacia arriba
        } else if (ficha == 'O') {
            df = new int[]{1};  // O normal avanza hacia abajo
        } else {
            df = new int[]{-1, 1}; // Dama (x u o) avanza y retrocede
        }

        int[] dc = new int[]{-1, 1}; // Izquierda y Derecha

        // Buscar primero movimientos de captura
        for (int f : df) {
            for (int c : dc) {
                int fDestino = fila + (f * 2);
                int cDestino = columna + (c * 2);
                if (posicionValida(fDestino, cDestino) && esMovimientoDeCaptura(fila, columna, fDestino, cDestino)) {
                    String dirFila = (f == -1) ? "Adelante" : "Atras";
                    if (ficha == 'O') dirFila = (f == 1) ? "Adelante" : "Atras";
                    String dirCol = (c == -1) ? "Izquierda" : "Derecha";

                    String desc = "Capturar " + dirFila + "-" + dirCol + " a (" + (fDestino + 1) + "," + (cDestino + 1) + ")";
                    acciones.add(new Accion(fDestino, cDestino, true, desc));
                }
            }
        }

        // Buscar movimientos normales
        for (int f : df) {
            for (int c : dc) {
                int fDestino = fila + f;
                int cDestino = columna + c;
                if (posicionValida(fDestino, cDestino) && tablero[fDestino][cDestino] == ' ' && esMovimientoNormal(fila, columna, fDestino, cDestino)) {
                    String dirFila = (f == -1) ? "Adelante" : "Atras";
                    if (ficha == 'O') dirFila = (f == 1) ? "Adelante" : "Atras";
                    String dirCol = (c == -1) ? "Izquierda" : "Derecha";

                    String desc = "Mover " + dirFila + "-" + dirCol + " a (" + (fDestino + 1) + "," + (cDestino + 1) + ")";
                    acciones.add(new Accion(fDestino, cDestino, false, desc));
                }
            }
        }

        return acciones;
    }

    public static boolean esMovimientoNormal(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino) {

        int diferenciaFila = filaDestino - filaOrigen;
        int diferenciaColumna = columnaDestino - columnaOrigen;

        if (Math.abs(diferenciaFila) != 1 || Math.abs(diferenciaColumna) != 1) {
            return false;
        }

        char ficha = tablero[filaOrigen][columnaOrigen];

        if (ficha == 'X' && diferenciaFila != -1) return false;
        if (ficha == 'O' && diferenciaFila != 1) return false;

        return true;
    }

    public static boolean esMovimientoDeCaptura(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino) {

        int diferenciaFila = filaDestino - filaOrigen;
        int diferenciaColumna = columnaDestino - columnaOrigen;

        if (Math.abs(diferenciaFila) != 2 || Math.abs(diferenciaColumna) != 2) {
            return false;
        }

        int filaMedia = (filaOrigen + filaDestino) / 2;
        int columnaMedia = (columnaOrigen + columnaDestino) / 2;

        char fichaEnMedio = tablero[filaMedia][columnaMedia];

        if (!esFichaContraria(fichaEnMedio)) {
            return false;
        }

        char ficha = tablero[filaOrigen][columnaOrigen];
        if (ficha == 'X' && diferenciaFila != -2) return false;
        if (ficha == 'O' && diferenciaFila != 2) return false;

        return true;
    }

    public static boolean esFichaContraria(char ficha) {
        if (jugadorActual == 'X') {
            return (ficha == 'O' || ficha == 'o');
        }
        if (jugadorActual == 'O') {
            return (ficha == 'X' || ficha == 'x');
        }
        return false;
    }

    public static void coronarFicha(int fila, int columna) {
        if (tablero[fila][columna] == 'X' && fila == 0) {
            tablero[fila][columna] = 'x';
            System.out.println("La ficha X se ha convertido en dama.");
        }

        if (tablero[fila][columna] == 'O' && fila == 7) {
            tablero[fila][columna] = 'o';
            System.out.println("La ficha O se ha convertido en dama.");
        }
    }

    public static void continuarCaptura(int fila, int columna) {
        while (puedeCapturarDesde(fila, columna)) {

            System.out.println("\nPuedes realizar otra captura.");
            mostrarTablero();

            ArrayList<Accion> capturasDisponibles = new ArrayList<>();
            ArrayList<Accion> todas = obtenerAccionesDisponibles(fila, columna);
            for (Accion a : todas) {
                if (a.esCaptura) capturasDisponibles.add(a);
            }

            System.out.println("Selecciona tu siguiente captura:");
            for (int i = 0; i < capturasDisponibles.size(); i++) {
                System.out.println((i + 1) + ". " + capturasDisponibles.get(i).descripcion);
            }

            System.out.print("Opcion: ");
            int opcion = entrada.nextInt();

            if (opcion < 1 || opcion > capturasDisponibles.size()) {
                System.out.println("Opcion invalida.");
                continue;
            }

            Accion a = capturasDisponibles.get(opcion - 1);
            int nuevaFila = a.filaDestino;
            int nuevaColumna = a.columnaDestino;

            int filaMedia = (fila + nuevaFila) / 2;
            int columnaMedia = (columna + nuevaColumna) / 2;

            tablero[filaMedia][columnaMedia] = ' ';
            tablero[nuevaFila][nuevaColumna] = tablero[fila][columna];
            tablero[fila][columna] = ' ';

            coronarFicha(nuevaFila, nuevaColumna);
            guardarMovimiento(fila, columna, nuevaFila, nuevaColumna, true);

            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }

    public static boolean puedeCapturarDesde(int fila, int columna) {
        int[] movimientosFila = {-2, -2, 2, 2};
        int[] movimientosColumna = {-2, 2, -2, 2};

        for (int i = 0; i < 4; i++) {
            int nuevaFila = fila + movimientosFila[i];
            int nuevaColumna = columna + movimientosColumna[i];

            if (posicionValida(nuevaFila, nuevaColumna)) {
                if (tablero[nuevaFila][nuevaColumna] == ' ') {
                    if (esMovimientoDeCaptura(fila, columna, nuevaFila, nuevaColumna)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hayMovimientosDisponibles(char jugador) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (tablero[fila][columna] == jugador || tablero[fila][columna] == Character.toLowerCase(jugador)) {
                    if (!obtenerAccionesDisponibles(fila, columna).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static char cambiarJugador(char jugador) {
        return (jugador == 'X') ? 'O' : 'X';
    }

    public static boolean posicionValida(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }

    public static void guardarMovimiento(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino,
            boolean captura) {

        if (cantidadMovimientos < movimientos.length) {
            String movimiento = "Jugador " + jugadorActual + ": (" + (filaOrigen + 1) + "," + (columnaOrigen + 1) + ")"
                    + " -> (" + (filaDestino + 1) + "," + (columnaDestino + 1) + ")"
                    + (captura ? " [CAPTURA]" : " [MOVIMIENTO]");

            movimientos[cantidadMovimientos] = movimiento;
            cantidadMovimientos++;
        }
    }

    public static void preguntarGuardarPartida() {
        System.out.print("Quieres guardar la partida? (S/N): ");
        String respuesta = entrada.next();

        if (respuesta.equalsIgnoreCase("S")) {
            mostrarRegistro();
        } else {
            System.out.println("No se guardo el registro.");
        }
    }

    public static void mostrarRegistro() {
        System.out.println("\n************************************");
        System.out.println("       REGISTRO DE MOVIMIENTOS");
        System.out.println("************************************");

        if (cantidadMovimientos == 0) {
            System.out.println("No hay movimientos registrados.");
        }

        for (int i = 0; i < cantidadMovimientos; i++) {
            System.out.println((i + 1) + ". " + movimientos[i]);
        }

        System.out.println("************************************");
    }
}