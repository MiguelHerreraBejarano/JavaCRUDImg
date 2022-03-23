package models;

public class Cliente {
    private int id;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;
    private int salario;
    private String foto;
    private String fotoOld;
    
    
    public Cliente(int id, String nombre, String direccion, String correo, String telefono, int salario, String foto, String fotoOld) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.salario = salario;
        this.foto = foto;
        this.fotoOld = fotoOld;
    }

    
    
    
    public String getfotoOld() {
        return fotoOld;
    }

    public void setfotoOld(String foto0id) {
        this.fotoOld = fotoOld;
    }
    
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    
    public void setSalario(int salario) {
        this.salario = salario;
    }

    public int getSalario() {
        return salario;
    }

    public Cliente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  

    @Override
    public String toString() {
        return "{" + "id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", correo=" + correo + ", telefono=" + telefono + ", salario=" + salario + '}';
    }
    
    
}
