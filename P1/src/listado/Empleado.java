package listado;

/**
 * Created by m_ang on 09/03/2017.
 */
public class Empleado {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private Division division;
    private Departamento departamento;

    public Empleado(String dni, String nombre, String apellidos, String email) {
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.email=email;
        this.division=Division.DIVNA;
        this.departamento=Departamento.DEPNA;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public Division getDivision() {
        return division;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-8s %-8s %-8s %s", getNombre(), getApellidos(), getEmail(), getDivision().toString(),getDepartamento().toString());
    }
}
