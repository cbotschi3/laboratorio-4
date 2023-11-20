package lab4;
class Usuario {
    String nombre;
    String contrasena;
    boolean esPremium;
    
    public Object productosSeleccionados;

    public Usuario(String nombre, String contrasena, boolean esPremium) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.esPremium = esPremium;
    }
}