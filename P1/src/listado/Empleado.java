package listado;

/**
 * Clase que crea la figura de empleado
 *  @author Miguel Ángel Torres López
 */
public class Empleado {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private Division division;
    private Departamento departamento;

    /**
     * Método constructor de la clase empleado
     * @param dni Contiene un string con el DNI del objeto empleado que se esta construyendo
     * @param nombre Contiene un string con el Nombre del objeto empleado que se esta construyendo
     * @param apellidos Contiene un string con los Apellidos del objeto empleado que se esta construyendo
     * @param email Contiene un string con el Email del objeto empleado que se esta construyendo
     */
    public Empleado(String dni, String nombre, String apellidos, String email) {
        this.dni=dni;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.email=email;
        this.division=Division.DIVNA;
        this.departamento=Departamento.DEPNA;
    }

    /**
     * Método getter para el DNI del empleado
     * @return Devuelve un string con el dni del empleado
     */
    public String getDni() {
        return dni;
    }

    /**
     * Método getter para el Nombre del empleado
     * @return Devuelve un string con el nombre del empleado
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método getter para los Apellidos del empleado
     * @return Devuelve un string con los apellidos del empleado
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Método getter para el Email del empleado
     * @return Devuelve un string con el Email del empleado
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método getter para la Division a la que pertenece el empleado
     * @return Devuelve un objeto de tipo Division con la Division a la que pertenece el empleado
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Método getter para el Departamento al que pertenece el empleado
     * @return Devuelve un objeto de tipo Departamento con el Departamento al que pertenece el empleado
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Método setter para el DNI del empleado
     * @param dni Contiene un string con el nuevo DNI que va a tener el empleado
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Método setter para el Nombre del empleado
     * @param nombre Contiene un string con el nuevo Nombre que va a tener el empleado
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método setter para los Apellidos del empleado
     * @param apellidos Contiene un string con los nuevos Apellidos que va a tener el empleado
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Método setter para el Email del empleado
     * @param email Contiene un string con el nuevo Email que va a tener el empleado
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método setter para la Division del empleado
     * @param division Contiene un objeto de tipo Division con la nueva Division que va a tener el empleado
     */
    public void setDivision(Division division) {
        this.division = division;
    }

    /**
     * Método setter para el Departamento del empleado
     * @param departamento Contiene un objeto de tipo Departamento con el nuevo Departamento que va a tener el empleado
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * Método toString de empleado sobre-escrito para que muestre los datos como deseamos
     * @return Devuelve un string con la información del empleado
     */
    @Override
    public String toString() {
        return String.format("%-8s %-8s %-8s %-8s %s", getNombre(), getApellidos(), getEmail(), getDivision().toString(),getDepartamento().toString());
    }
}
