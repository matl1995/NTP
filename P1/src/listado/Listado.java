package listado;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase que crea la figura de Listado
 * @author Miguel Ángel Torres López
 */
public class Listado {
    private Map<String, Empleado> lista=new HashMap<>();

    /**
     * Método constructor de la clase Listado
     * @param ruta Contiene un string con la ruta al archivo con el listado de los empleados
     * @throws IOException
     */
    public Listado(String ruta) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(ruta));
        lineas.forEach(linea -> {
            crearEmpleado(linea);
        });
    }

    /**
     * Método auxiliar para el constructor de la clase
     * @param linea Contiene un string con una linea con los datos de un empleado
     */
    public void crearEmpleado(String linea){
        Pattern pattern = Pattern.compile("(,)");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

        lista.put(infos.get(0),new Empleado(infos.get(0),infos.get(1),infos.get(2),infos.get(3)));
    }

    /**
     * Método para cargar los archivos con las divisiones
     * @param ruta Contiene un string con la ruta al archivo que contiene la division y los dni que pertenecen a esta
     * @throws IOException
     */
    public void cargarArchivoAsignacionDivision(String ruta)  throws IOException {
        Stream<String> lineasDivision = Files.lines(Paths.get(ruta));
        String division = lineasDivision.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDivision(linea,Division.valueOf(division));
        });
    }

    /**
     * Método auxiliar para la carga de los archivos con las divisiones
     * @param linea Contiene un string con la linea con un dni
     * @param div Contiene un objeto de tipo Division con la division que se asignará los dnis
     */
    public void asignarDivision(String linea, Division div)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDivision(div);
    }

    /**
     * Método para cargar los archivos con los departamentos
     * @param ruta Contiene un string con la ruta al archivo que contiene el departamento y los dni que pertenecen a este
     * @throws IOException
     */
    public void cargarArchivoAsignacionDepartamento(String ruta) throws IOException {
        Stream<String> lineasDepartamento = Files.lines(Paths.get(ruta));
        String departamento = lineasDepartamento.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDepartamento(linea,Departamento.valueOf(departamento));
        });
    }

    /**
     * Método auxiliar para la carga de los archivos con los departementos
     * @param linea Contiene un string con la linea con un dni
     * @param dep Contiene un objeto de tipo Departamento con el departamento que se asignará los dnis
     */
    public void asignarDepartamento(String linea, Departamento dep)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDepartamento(dep);
    }

    /**
     * Método que devuelve el número de empleados existentes
     * @return Devuelve un entero con el numero de empleados que hay en la lista
     */
    public int obtenerNumeroEmpleados()
    {
        return lista.size();
    }

    /**
     * Método que obtiene los contadores de empleados para cada departamento en funcion de
     * la division que se le pasa como parametro
     * @param div Contiene un objeto de tipo Division con la division para la que calcularemos los contadores
     * @return Devuelve un mapa con el contador asociado a cada departamento perteneciente a la division recibida como parametro
     */
    public Map<Departamento, Long> obtenerContadoresDepartamento(Division div){
        Map<Departamento, Long> contedoresDepartamento=new HashMap<>();
        Stream<Departamento> departamentoStream=Stream.of(Departamento.values());
        departamentoStream.forEach(departamento -> {
            contedoresDepartamento.put(departamento, lista.entrySet().stream().filter(empleado -> (
                    empleado.getValue().getDivision() == div && empleado.getValue().getDepartamento() == departamento
            )).count());

        });
        return contedoresDepartamento;
    }

    /**
     * Método que obtiene los contadores de empleados para cada division, y cada departamento, es decir,
     * para cada departamento de cada division devuelve un contador de empleados que hay en este
     * @return Devuelve un mapa con cada division asociada a otro mapa con los departamentos de dicha division asociados a un contador
     */
    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento(){
        Map<Division, Map<Departamento, Long>> contadoresDivisionDepartamento = new HashMap<>();
        Stream<Division> divisionStream=Stream.of(Division.values());
        divisionStream.forEach(division -> contadoresDivisionDepartamento.put(division, obtenerContadoresDepartamento(division)));
        return contadoresDivisionDepartamento;
    }

    /**
     * Método que se encarga de buscar los empleados que tienen una division asignada pero no un departamento
     * @return Devuelve una lista con los empleados que tienen una division asignada pero no un departamento
     */
    public List<Empleado> buscarEmpleadosConDivisionSinDepartamento(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() != Division.DIVNA && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    /**
     * Método que se encarga de buscar los empleados que no tienen una division asignada
     * @return Devuelve una lista con los empleados que no tienen una division asignada
     */
    public List<Empleado> buscarEmpleadosSinDivision(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == Division.DIVNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    /**
     * Método que se encarga de buscar los empleados con una division especifica asignada que no tienen un departamento asignado
     * @param div Contiene un objeto de tipo Division con la division para la que hay que buscar los empleados sin departamento
     * @return Devuelve una lista con los empleados que pertenecen a la division especificada y que no tienen un departamento asignado
     */
    public List<Empleado> buscarEmpleadosSinDepartamento(Division div){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == div && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    /**
     * Método toString de el Listado sobre escrito para que muestre los datos como deseamos
     * @return Devuelve un string con la información del listado
     */
    @Override
    public String toString()
    {
       return lista.toString();
    }

    /**
     * Método que comprueba si hay dnis repetdos entre los empleados
     * @return Devuelve un booleano con true en caso de que haya dnis repetidos y false en caso contrario
     */
    public boolean hayDnisRepetidos(){
        List<String> listaDNI = lista.entrySet().stream().map(empleado -> empleado.getValue().getDni()).distinct().collect(Collectors.toList());
        if(listaDNI.size()!=lista.size())
            return true;
        else
            return false;
    }

    /**
     * Método que busca los empleados con dnis repetidos
     * @return Devuelve un map que asocia el dni repetido con una lista que contiene a los empleados que comparten ese dni repetido
     */
    public Map<String,List<Empleado>> obtenerDnisRepetidos(){
        Map<String,List<Empleado>> dnis = new HashMap<>();
        Map<String,List<Empleado>> dnisRepetidos = new HashMap<>();
        lista.entrySet().stream().forEach(empleado -> {
            List<Empleado> lista_empleados=new ArrayList<>();
            lista.values().stream().filter(emp -> (
                    emp.getDni().compareTo(empleado.getValue().getDni()) == 0
            )).forEach(emp -> lista_empleados.add(emp));
            dnis.put(empleado.getValue().getDni(),lista_empleados);
        });

        dnis.entrySet().stream().filter(dni -> (
                dni.getValue().size() > 1
        )).forEach(dni -> dnisRepetidos.put(dni.getKey(),dni.getValue()));
        return dnisRepetidos;
    }

    /**
     * Método que comprueba si hay emails repetdos entre los empleados
     * @return Devuelve un booleano con true en caso de que haya emails repetidos y false en caso contrario
     */
    public boolean hayCorreosRepetidos(){
        List<String> listaEmail = lista.entrySet().stream().map(empleado -> empleado.getValue().getEmail()).distinct().collect(Collectors.toList());
        if(listaEmail.size()!=lista.size())
            return true;
        else
            return false;
    }

    /**
     * Método que busca los empleados con emails repetidos
     * @return Devuelve un map que asocia el email repetido con una lista que contiene a los empleados que comparten ese email repetido
     */
    public Map<String,List<Empleado>> obtenerCorreosRepetidos(){
        Map<String,List<Empleado>> correos = new HashMap<>();
        Map<String,List<Empleado>> correosRepetidos = new HashMap<>();
        lista.entrySet().stream().forEach(empleado -> {
            List<Empleado> lista_empleados=new ArrayList<>();
            lista.values().stream().filter(emp -> (
                    emp.getEmail().compareTo(empleado.getValue().getEmail()) == 0
            )).forEach(emp -> lista_empleados.add(emp));
            correos.put(empleado.getValue().getEmail(),lista_empleados);
        });

        correos.entrySet().stream().filter(correo -> (
                correo.getValue().size() > 1
        )).forEach(correo -> correosRepetidos.put(correo.getKey(),correo.getValue()));

        return correosRepetidos;
    }
}
