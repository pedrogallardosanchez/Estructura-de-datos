import java.util.Scanner;

public class DamasInglesas {

    static Scanner entrada = new Scanner(System.in);

    static char[][] tablero = new char[8][8];

    static String[] movimientos = new String[1000];

    static int cantidadMovimientos = 0;

    static char jugadorActual = 'X';
    public static void main(String[] args) {

        inicializarTablero();

        boolean partidaTerminada = false;

        System.out.println("====================================");
        System.out.println("          DAMAS INGLESAS");
        System.out.println("====================================");
        System.out.println("Jugador 1: fichas X");
        System.out.println("Jugador 2: fichas O");
        System.out.println();

        while (!partidaTerminada) {

            mostrarTablero();

            System.out.println();
            System.out.println("Turno del jugador: " + jugadorActual);
            System.out.println();

            boolean movimientoRealizado = realizarMovimiento();

            if (movimientoRealizado) {

                if (!hayMovimientosDisponibles(
                        cambiarJugador(jugadorActual))) {

                    mostrarTablero();

                    System.out.println();
                    System.out.println("************************************");
                    System.out.println("          PARTIDA TERMINADA");
                    System.out.println("************************************");
                    System.out.println("Ganador: jugador " + jugadorActual);
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

                System.out.print(" " + tablero[fila][columna] + " |");
            }

            System.out.println();
            System.out.println(
                "     +---+---+---+---+---+---+---+---+"
            );
        }

        System.out.println();
    }

    public static boolean realizarMovimiento() {

        int filaOrigen;
        int columnaOrigen;
        int filaDestino;
        int columnaDestino;

        System.out.print("fila inicial de la ficha: ");
        filaOrigen = entrada.nextInt() - 1;

        System.out.print("columna inicial de la ficha: ");
        columnaOrigen = entrada.nextInt() - 1;

        if (!posicionValida(filaOrigen, columnaOrigen)) {

            System.out.println("posicion fuera del tablero.");
            return false;
        }

        if (tablero[filaOrigen][columnaOrigen] != jugadorActual &&
            tablero[filaOrigen][columnaOrigen] !=
            Character.toLowerCase(jugadorActual)) {

            System.out.println("no hay una ficha en esa posicion");
            return false;
        }

        System.out.print("fila de destino: ");
        filaDestino = entrada.nextInt() - 1;

        System.out.print("columna de destino: ");
        columnaDestino = entrada.nextInt() - 1;

        if (!posicionValida(filaDestino, columnaDestino)) {

            System.out.println("fuera del tablero");
            return false;
        }

        if (tablero[filaDestino][columnaDestino] != ' ') {

            System.out.println("ya esta ocupada");
            return false;
        }

        boolean esCaptura =
                esMovimientoDeCaptura(
                        filaOrigen,
                        columnaOrigen,
                        filaDestino,
                        columnaDestino
                );

        boolean movimientoNormal =
                esMovimientoNormal(
                        filaOrigen,
                        columnaOrigen,
                        filaDestino,
                        columnaDestino
                );

        if (!esCaptura && !movimientoNormal) {

            System.out.println("Movimiento invalido");
            return false;
        }

        if (esCaptura) {

            int filaMedia =
                    (filaOrigen + filaDestino) / 2;

            int columnaMedia =
                    (columnaOrigen + columnaDestino) / 2;

            tablero[filaMedia][columnaMedia] = ' ';
        }

        tablero[filaDestino][columnaDestino] =
                tablero[filaOrigen][columnaOrigen];

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

            continuarCaptura(
                    filaDestino,
                    columnaDestino
            );
        }

        return true;
    }
    public static boolean esMovimientoNormal(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino) {

        int diferenciaFila =
                filaDestino - filaOrigen;

        int diferenciaColumna =
                columnaDestino - columnaOrigen;

        if (Math.abs(diferenciaFila) != 1 ||
            Math.abs(diferenciaColumna) != 1) {

            return false;
        }

        char ficha =
                tablero[filaOrigen][columnaOrigen];

        if (ficha == 'X') {

            if (diferenciaFila != -1) {

                return false;
            }
        }

        if (ficha == 'O') {

            if (diferenciaFila != 1) {

                return false;
            }
        }
        if (ficha == 'x' || ficha == 'o') {

            return true;
        }

        return true;
    }

    public static boolean esMovimientoDeCaptura(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino) {

        int diferenciaFila =
                filaDestino - filaOrigen;

        int diferenciaColumna =
                columnaDestino - columnaOrigen;

        if (Math.abs(diferenciaFila) != 2 ||
            Math.abs(diferenciaColumna) != 2) {

            return false;
        }

        int filaMedia =
                (filaOrigen + filaDestino) / 2;

        int columnaMedia =
                (columnaOrigen + columnaDestino) / 2;

        char fichaEnMedio =
                tablero[filaMedia][columnaMedia];

        if (!esFichaContraria(fichaEnMedio)) {

            return false;
        }

        char ficha =
                tablero[filaOrigen][columnaOrigen];
        if (ficha == 'X') {

            if (diferenciaFila != -2) {

                return false;
            }
        }
        if (ficha == 'O') {

            if (diferenciaFila != 2) {

                return false;
            }
        }

        return true;
    }

    public static boolean esFichaContraria(char ficha) {

        if (jugadorActual == 'X') {

            if (ficha == 'O' || ficha == 'o') {

                return true;
            }
        }

        if (jugadorActual == 'O') {

            if (ficha == 'X' || ficha == 'x') {

                return true;
            }
        }

        return false;
    }

    public static void coronarFicha(
            int fila,
            int columna) {

        if (tablero[fila][columna] == 'X' &&
            fila == 0) {

            tablero[fila][columna] = 'x';

            System.out.println(
                    "La ficha X se ha convertido en dama."
            );
        }

        if (tablero[fila][columna] == 'O' &&
            fila == 7) {

            tablero[fila][columna] = 'o';

            System.out.println(
                    "La ficha O se ha convertido en dama."
            );
        }
    }

    public static void continuarCaptura(
            int fila,
            int columna) {

        boolean puedeCapturar =
                puedeCapturarDesde(fila, columna);

        while (puedeCapturar) {

            System.out.println();
            System.out.println(
                    "Puedes realizar otra captura."
            );

            mostrarTablero();

            System.out.print(
                    "Nueva fila destino: "
            );

            int nuevaFila =
                    entrada.nextInt() - 1;

            System.out.print(
                    "Nueva columna destino: "
            );

            int nuevaColumna =
                    entrada.nextInt() - 1;

            if (!posicionValida(
                    nuevaFila,
                    nuevaColumna)) {

                System.out.println(
                        "Posicion invalida."
                );

                continue;
            }

            if (tablero[nuevaFila][nuevaColumna]
                    != ' ') {

                System.out.println(
                        "La posicion esta ocupada."
                );

                continue;
            }

            if (!esMovimientoDeCaptura(
                    fila,
                    columna,
                    nuevaFila,
                    nuevaColumna)) {

                System.out.println(
                        "Debes realizar una captura valida."
                );

                continue;
            }

            int filaMedia =
                    (fila + nuevaFila) / 2;

            int columnaMedia =
                    (columna + nuevaColumna) / 2;

            tablero[filaMedia][columnaMedia] = ' ';

            tablero[nuevaFila][nuevaColumna] =
                    tablero[fila][columna];

            tablero[fila][columna] = ' ';

            coronarFicha(
                    nuevaFila,
                    nuevaColumna
            );

            guardarMovimiento(
                    fila,
                    columna,
                    nuevaFila,
                    nuevaColumna,
                    true
            );

            fila = nuevaFila;
            columna = nuevaColumna;

            puedeCapturar =
                    puedeCapturarDesde(
                            fila,
                            columna
                    );
        }
    }

    public static boolean puedeCapturarDesde(
            int fila,
            int columna) {

        int[] movimientosFila =
                {-2, -2, 2, 2};

        int[] movimientosColumna =
                {-2, 2, -2, 2};

        for (int i = 0; i < 4; i++) {

            int nuevaFila =
                    fila + movimientosFila[i];

            int nuevaColumna =
                    columna + movimientosColumna[i];

            if (posicionValida(
                    nuevaFila,
                    nuevaColumna)) {

                if (tablero[nuevaFila][nuevaColumna]
                        == ' ') {

                    if (esMovimientoDeCaptura(
                            fila,
                            columna,
                            nuevaFila,
                            nuevaColumna)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean hayMovimientosDisponibles(
            char jugador) {

        for (int fila = 0; fila < 8; fila++) {

            for (int columna = 0; columna < 8; columna++) {

                if (tablero[fila][columna] == jugador ||
                    tablero[fila][columna] ==
                    Character.toLowerCase(jugador)) {

                    if (puedeMoverNormalmente(
                            fila,
                            columna)) {

                        return true;
                    }

                    if (puedeCapturarConJugador(
                            fila,
                            columna,
                            jugador)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean puedeMoverNormalmente(
            int fila,
            int columna) {

        char ficha =
                tablero[fila][columna];

        int[] direccionesColumna =
                {-1, 1};

        for (int i = 0; i < 2; i++) {

            int nuevaColumna =
                    columna + direccionesColumna[i];
            if (ficha == 'X') {

                int nuevaFila = fila - 1;

                if (posicionValida(
                        nuevaFila,
                        nuevaColumna)) {

                    if (tablero[nuevaFila][nuevaColumna]
                            == ' ') {

                        return true;
                    }
                }
            }
            if (ficha == 'O') {

                int nuevaFila = fila + 1;

                if (posicionValida(
                        nuevaFila,
                        nuevaColumna)) {

                    if (tablero[nuevaFila][nuevaColumna]
                            == ' ') {

                        return true;
                    }
                }
            }

            if (ficha == 'x' ||
                ficha == 'o') {

                int nuevaFila = fila - 1;

                if (posicionValida(
                        nuevaFila,
                        nuevaColumna)) {

                    if (tablero[nuevaFila][nuevaColumna]
                            == ' ') {

                        return true;
                    }
                }

                nuevaFila = fila + 1;

                if (posicionValida(
                        nuevaFila,
                        nuevaColumna)) {

                    if (tablero[nuevaFila][nuevaColumna]
                            == ' ') {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean puedeCapturarConJugador(
            int fila,
            int columna,
            char jugador) {

        int[] cambiosFila =
                {-2, -2, 2, 2};

        int[] cambiosColumna =
                {-2, 2, -2, 2};

        char ficha =
                tablero[fila][columna];

        for (int i = 0; i < 4; i++) {

            int nuevaFila =
                    fila + cambiosFila[i];

            int nuevaColumna =
                    columna + cambiosColumna[i];

            if (posicionValida(
                    nuevaFila,
                    nuevaColumna)) {

                if (tablero[nuevaFila][nuevaColumna]
                        == ' ') {

                    int filaMedia =
                            (fila + nuevaFila) / 2;

                    int columnaMedia =
                            (columna + nuevaColumna) / 2;

                    char fichaMedia =
                            tablero[filaMedia][columnaMedia];

                    if (jugador == 'X') {

                        if (fichaMedia == 'O' ||
                            fichaMedia == 'o') {

                            if (ficha == 'x' ||
                                cambiosFila[i] == -2) {

                                return true;
                            }
                        }
                    }

                    if (jugador == 'O') {

                        if (fichaMedia == 'X' ||
                            fichaMedia == 'x') {

                            if (ficha == 'o' ||
                                cambiosFila[i] == 2) {

                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static char cambiarJugador(
            char jugador) {

        if (jugador == 'X') {

            return 'O';
        }

        return 'X';
    }

    public static boolean posicionValida(
            int fila,
            int columna) {

        if (fila < 0 ||
            fila >= 8) {

            return false;
        }

        if (columna < 0 ||
            columna >= 8) {

            return false;
        }

        return true;
    }

    public static void guardarMovimiento(
            int filaOrigen,
            int columnaOrigen,
            int filaDestino,
            int columnaDestino,
            boolean captura) {

        if (cantidadMovimientos <
                movimientos.length) {

            String movimiento = "";

            movimiento = movimiento
                    + "Jugador "
                    + jugadorActual
                    + ": ";

            movimiento = movimiento
                    + "("
                    + (filaOrigen + 1)
                    + ","
                    + (columnaOrigen + 1)
                    + ")";

            movimiento = movimiento
                    + " -> ";

            movimiento = movimiento
                    + "("
                    + (filaDestino + 1)
                    + ","
                    + (columnaDestino + 1)
                    + ")";

            if (captura) {

                movimiento =
                        movimiento
                        + " [CAPTURA]";
            }
            else {

                movimiento =
                        movimiento
                        + " [MOVIMIENTO]";
            }

            movimientos[cantidadMovimientos] =
                    movimiento;

            cantidadMovimientos++;
        }
    }
    public static void preguntarGuardarPartida() {

        System.out.print(
                "Quieres guardar la partida? (S/N): "
        );

        String respuesta =
                entrada.next();

        if (respuesta.equalsIgnoreCase("S")) {

            mostrarRegistro();
        }
        else {

            System.out.println(
                    "no se guardo el registro"
            );
        }
    }

    public static void mostrarRegistro() {

        System.out.println();
        System.out.println(
                "************************************"
        );

        System.out.println(
                "       REGISTRO DE MOVIMIENTOS"
        );

        System.out.println(
                "************************************"
        );

        if (cantidadMovimientos == 0) {

            System.out.println(
                    "no hay movimientos"
            );
        }

        for (int i = 0;
             i < cantidadMovimientos;
             i++) {

            System.out.println(
                    (i + 1)
                    + ". "
                    + movimientos[i]
            );
        }

        System.out.println(
                "**********************************"
        );
    }
}