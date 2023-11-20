package lab4;

import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static ArrayList<Producto> productos = new ArrayList<>();
    static Usuario usuarioActual = null;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        while (true) {
            System.out.println("1. Modo Registro");
            System.out.println("2. Ingresar/Salir");
            System.out.println("3. Modo Selección");
            System.out.println("4. Modo Préstamo");
            System.out.println("5. Modo Perfil");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    biblioteca.modoRegistro();
                    break;
                case 2:
                    biblioteca.modoIngresarSalir();
                    break;
                case 3:
                    biblioteca.modoSeleccion();
                    break;
                case 4:
                    biblioteca.modoPrestamo();
                    break;
                case 5:
                    biblioteca.modoPerfil();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private void modoRegistro() {
        System.out.println("Modo Registro");

        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();

        if (usuarioExistente(nombreUsuario)) {
            System.out.println("El nombre de usuario ya está en uso. Inténtelo de nuevo.");
            return;
        }

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Seleccione el plan (base/premium): ");
        String plan = scanner.nextLine();

        boolean esPremium = plan.equalsIgnoreCase("premium");

        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasena, esPremium);
        usuarios.add(nuevoUsuario);

        System.out.println("¡Registro exitoso!");
    }

    private boolean usuarioExistente(String nombreUsuario) {
        return false;
    }

    private void modoIngresarSalir() {
        if (usuarioActual == null) {
            ingresar();
        } else {
            salir();
        }
    }

    private void ingresar() {
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario usuario = buscarUsuario(nombreUsuario, contrasena);

        if (usuario != null) {
            usuarioActual = usuario;
            System.out.println("¡Bienvenido, " + usuarioActual.nombre + "!");
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo.");
        }
    }

    private void salir() {
        if (usuarioActual != null) {
            System.out.println("¡Hasta luego, " + usuarioActual.nombre + "!");
            usuarioActual = null;
        } else {
            System.out.println("No ha iniciado sesión.");
        }
    }

    private void modoSeleccion() {
        System.out.println("Modo Selección");

        if (usuarioActual == null) {
            System.out.println("Debe iniciar sesión para seleccionar libros o revistas.");
            return;
        }

        while (true) {
            System.out.println("1. Agregar libro");
            System.out.println("2. Agregar revista");
            System.out.println("3. Vaciar lista de selección");
            System.out.println("0. Salir del modo Selección");
            System.out.print("Seleccione una opción: ");
            int opcionSeleccion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcionSeleccion) {
                case 1:
                    if (usuarioActual.esPremium && usuarioActualLibrosPrestados() >= 5) {
                        System.out.println("¡Ha alcanzado el límite de libros para usuarios premium!");
                    } else if (!usuarioActual.esPremium && usuarioActualLibrosPrestados() >= 3) {
                        System.out.println("¡Ha alcanzado el límite de libros para usuarios base!");
                    } else {
                        agregarLibro();
                    }
                    break;
                case 2:
                    agregarRevista();
                    break;
                case 3:
                    vaciarListaSeleccion();
                    break;
                case 0:
                    return; 
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private void agregarLibro() {
        System.out.print("Ingrese el nombre del libro: ");
        String nombreLibro = scanner.nextLine();

        Producto nuevoLibro = new Producto(nombreLibro);

        productos.add(nuevoLibro);
        usuarioActual.productosSeleccionados.add(nuevoLibro);

        System.out.println("Libro agregado a la lista de selección.");
    }

    private void agregarRevista() {
        System.out.print("Ingrese el nombre de la revista: ");
        String nombreRevista = scanner.nextLine();

        Producto nuevaRevista = new Producto(nombreRevista);

        productos.add(nuevaRevista);
        usuarioActual.productosSeleccionados.add(nuevaRevista);

        System.out.println("Revista agregada a la lista de selección.");
    }

    private void vaciarListaSeleccion() {
        usuarioActual.productosSeleccionados.clear();
        System.out.println("Lista de selección vaciada.");
    }
    
    private int usuarioActualLibrosPrestados() {
        int contadorLibros = 0;
        for (Producto producto : usuarioActual.productosSeleccionados) {
            
            contadorLibros++;
        }
        return contadorLibros;
    }
    private void modoPrestamo() {
        System.out.println("Modo Préstamo");

        if (usuarioActual == null) {
            System.out.println("Debe iniciar sesión para realizar préstamos.");
            return;
        }

        while (true) {
            System.out.println("a. Definir días de entrega");
            System.out.println("b. Definir horario de entrega (Premium)");
            System.out.println("c. Definir sucursal para recoger el préstamo (No premium)");
            System.out.println("d. Imprimir listado de préstamo");
            System.out.println("e. Seleccionar dirección de envío (Premium)");
            System.out.println("f. Definir si va a pasar por el préstamo a las 12 o 24 horas de haber realizado la solicitud (No premium)");
            System.out.println("0. Salir del modo Préstamo");
            System.out.print("Seleccione una opción: ");
            char opcionPrestamo = scanner.nextLine().charAt(0);

            switch (opcionPrestamo) {
                case 'a':
                    definirDiasEntrega();
                    break;
                case 'b':
                    if (usuarioActual.esPremium) {
                        definirHorarioEntregaPremium();
                    } else {
                        System.out.println("Opción válida solo para usuarios premium.");
                    }
                    break;
                case 'c':
                    if (!usuarioActual.esPremium) {
                        definirSucursalRecoger();
                    } else {
                        System.out.println("Opción válida solo para usuarios no premium.");
                    }
                    break;
                case 'd':
                    imprimirListadoPrestamo();
                    break;
                case 'e':
                    if (usuarioActual.esPremium) {
                        seleccionarDireccionEnvio();
                    } else {
                        System.out.println("Opción válida solo para usuarios premium.");
                    }
                    break;
                case 'f':
                    if (!usuarioActual.esPremium) {
                        definirHoraRecoger();
                    } else {
                        System.out.println("Opción válida solo para usuarios no premium.");
                    }
                    break;
                case '0':
                    return; 
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private void definirDiasEntrega() {
        int maxDias = usuarioActual.esPremium ? 50 : 30;
        System.out.print("Ingrese la cantidad de días de entrega (máximo " + maxDias + " días): ");
        int diasEntrega = scanner.nextInt();
        scanner.nextLine(); 

        if (diasEntrega <= maxDias) {
            System.out.println("Días de entrega definidos: " + diasEntrega + " días.");
        } else {
            System.out.println("La cantidad de días ingresada supera el límite permitido.");
        }
    }

    private void definirHorarioEntregaPremium() {
        System.out.print("Ingrese el horario de entrega (AM/PM): ");
        String horarioEntrega = scanner.nextLine();

        System.out.println("Horario de entrega premium definido: " + horarioEntrega);
    }

    private void definirSucursalRecoger() {
        System.out.print("Ingrese la sucursal para recoger el préstamo: ");
        String sucursalRecoger = scanner.nextLine();

        System.out.println("Sucursal para recoger definida: " + sucursalRecoger);
    }

    private void imprimirListadoPrestamo() {
        System.out.println("Listado de préstamo");
    }

    private void seleccionarDireccionEnvio() {
        System.out.print("Ingrese la dirección de envío: ");
        String direccionEnvio = scanner.nextLine();

        System.out.println("Dirección de envío seleccionada: " + direccionEnvio);
    }

    private void definirHoraRecoger() {
        System.out.print("Seleccione la hora para recoger el préstamo (12 o 24 horas después): ");
        int horasDespues = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Hora para recoger definida: " + horasDespues + " horas después.");
    }

    private void modoPerfil() {
        System.out.println("Modo Perfil");

        if (usuarioActual == null) {
            System.out.println("Debe iniciar sesión para acceder al modo Perfil.");
            return;
        }

        while (true) {
            System.out.println("a. Modificar el tipo de cliente (No premium)");
            System.out.println("b. Aplicar cupón de 15 días adicionales (Premium)");
            System.out.println("c. Cambiar contraseña");
            System.out.println("0. Salir del modo Perfil");
            System.out.print("Seleccione una opción: ");
            char opcionPerfil = scanner.nextLine().charAt(0);

            switch (opcionPerfil) {
                case 'a':
                    if (!usuarioActual.esPremium) {
                        modificarTipoCliente();
                    } else {
                        System.out.println("Opción válida solo para usuarios no premium.");
                    }
                    break;
                case 'b':
                    if (usuarioActual.esPremium) {
                        aplicarCupon();
                    } else {
                        System.out.println("Opción válida solo para usuarios premium.");
                    }
                    break;
                case 'c':
                    cambiarContrasena();
                    break;
                case '0':
                    return;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private void modificarTipoCliente() {
        System.out.print("¿Desea cambiar a tipo de cliente premium? (Sí/No): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("Sí")) {
            usuarioActual.esPremium = true;
            System.out.println("¡Ahora eres un usuario premium!");
        } else if (respuesta.equalsIgnoreCase("No")) {
            System.out.println("No se realizaron cambios en el tipo de cliente.");
        } else {
            System.out.println("Respuesta no válida. Inténtelo de nuevo.");
        }
    }

    private void aplicarCupon() {
        System.out.print("¿Desea aplicar un cupón de 15 días adicionales? (Sí/No): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("Sí")) {
            System.out.println("¡Cupón aplicado! 15 días adicionales agregados a tu plan premium.");
        } else if (respuesta.equalsIgnoreCase("No")) {
            System.out.println("No se aplicó ningún cupón.");
        } else {
            System.out.println("Respuesta no válida. Inténtelo de nuevo.");
        }
    }

    private void cambiarContrasena() {
        System.out.print("Ingrese la nueva contraseña: ");
        String nuevaContrasena = scanner.nextLine();


        usuarioActual.contrasena = nuevaContrasena;
        System.out.println("Contraseña cambiada exitosamente.");
    }

    private Usuario buscarUsuario(String nombreUsuario, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.nombre.equals(nombreUsuario) && usuario.contrasena.equals(contrasena)) {
                return usuario;
            }
        }
        return null; 
    }

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        while (true) {
            System.out.println("1. Modo Registro");
            System.out.println("2. Ingresar/Salir");
            System.out.println("3. Modo Selección");
            System.out.println("4. Modo Préstamo");
            System.out.println("5. Modo Perfil");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    biblioteca.modoRegistro();
                    break;
                case 2:
                    biblioteca.modoIngresarSalir();
                    break;
                case 3:
                    biblioteca.modoSeleccion();
                    break;
                case 4:
                    biblioteca.modoPrestamo();
                    break;
                case 5:
                    biblioteca.modoPerfil();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }
}