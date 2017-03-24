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
 * @author Miguel Ángel Torres López
 */
public class Listado {
    private Map<String, Empleado> lista=new HashMap<>();

    //Metodo constructor de la clase
    public Listado(String ruta) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(ruta));
        lineas.forEach(linea -> {
            crearEmpleado(linea);
        });
    }

    //Metodo auxiliar para el constructor de la clase
    public void crearEmpleado(String linea){
        Pattern pattern = Pattern.compile("(,)");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

        lista.put(infos.get(0),new Empleado(infos.get(0),infos.get(1),infos.get(2),infos.get(3)));
    }

    //Metodo para cargar los archivos con las divisiones
    public void cargarArchivoAsignacionDivision(String ruta)  throws IOException {
        Stream<String> lineasDivision = Files.lines(Paths.get(ruta));
        String division = lineasDivision.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDivision(linea,Division.valueOf(division));
        });
    }

    //Metodo auxiliar para el que carga los archivos con las divisiones
    public void asignarDivision(String linea, Division div)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDivision(div);
    }

    //Metodo para cargar los archivos con los departamentos
    public void cargarArchivoAsignacionDepartamento(String ruta) throws IOException {
        Stream<String> lineasDepartamento = Files.lines(Paths.get(ruta));
        String departamento = lineasDepartamento.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDepartamento(linea,Departamento.valueOf(departamento));
        });
    }

    //Metodo auxiliar para el que carga los archivos con los departamentos
    public void asignarDepartamento(String linea, Departamento dep)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDepartamento(dep);
    }

    //Metodo que devuelve el numero de empleados existentes
    public int obtenerNumeroEmpleados()
    {
        return lista.size();
    }

    //Metodo que obtiene los contadores de empleados para cada departamento en funcion de
    //la division que se le pasa como parametro
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

    //Metodo que obtiene los contadores de empleados para cada division, y cada departamento,
    //es decir, para cada departamento de cada division devuelve un contador de empleados que hay en este
    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento(){
        Map<Division, Map<Departamento, Long>> contadoresDivisionDepartamento = new HashMap<>();
        Stream<Division> divisionStream=Stream.of(Division.values());
        divisionStream.forEach(division -> contadoresDivisionDepartamento.put(division, obtenerContadoresDepartamento(division)));
        return contadoresDivisionDepartamento;
    }

    //Metodo que se encarga de buscar los empleados que tienen una division asignada pero no un departamento
    //y que devuelve una lista con esos empleados
    public List<Empleado> buscarEmpleadosConDivisionSinDepartamento(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() != Division.DIVNA && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    //Metodo que se encarga de buscar los empleados que no tienen una division asignada
    //y que devuelve una lista con esos empleados
    public List<Empleado> buscarEmpleadosSinDivision(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == Division.DIVNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    //Metodo que se encarga de buscar los empleados con una division especifica asignada
    //que no tienen un departamento asignado y que devuelve una lista con esos empleados
    public List<Empleado> buscarEmpleadosSinDepartamento(Division div){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == div && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    //Metodo que devuelve el contenido de la lista en un string
    @Override
    public String toString()
    {
       return lista.toString();
    }

    //Metodo que comprueba si hay dnis repetidos entre los empleados y devuelve un booleano
    public boolean hayDnisRepetidos(){
        List<String> listaDNI = lista.entrySet().stream().map(empleado -> empleado.getValue().getDni()).distinct().collect(Collectors.toList());
        if(listaDNI.size()!=lista.size())
            return true;
        else
            return false;
    }

    //Metodo que busca los empleados con dnis repetidos y los devuelve en un map que asocia el dni repetido
    //con una lista que contiene los empleados que compartian ese dni repetido
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

    //Metodo que comprueba si hay emails repetidos entre los empleados y devuelve un booleano
    public boolean hayCorreosRepetidos(){
        List<String> listaEmail = lista.entrySet().stream().map(empleado -> empleado.getValue().getEmail()).distinct().collect(Collectors.toList());
        if(listaEmail.size()!=lista.size())
            return true;
        else
            return false;
    }

    //Metodo que busca los empleados con emails repetidos y los devuelve en un map que asocia el email repetido
    //con una lista que contiene los empleados que compartian ese email repetido
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
