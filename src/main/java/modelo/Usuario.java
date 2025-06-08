package modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String username;
    private String password;
    private int estado;
    private int idRol;
    private Rol rol; 

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String dni, String telefono, String username, String password,
                   int estado, int idRol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.idRol = idRol;
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String dni, String telefono, String username,
                   String password, int estado, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.idRol = idRol;
    }

    // Getters y Setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
