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
 * Created by m_ang on 09/03/2017.
 */
public class Listado {
    private Map<String, Empleado> lista=new HashMap<>();

    public Listado(String ruta) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(ruta));
        lineas.forEach(linea -> {
            crearEmpleado(linea);
        });
    }

    public void crearEmpleado(String linea){
        Pattern pattern = Pattern.compile("(,)");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

        lista.put(infos.get(0),new Empleado(infos.get(0),infos.get(1),infos.get(2),infos.get(3)));
    }

    public void cargarArchivoAsignacionDivision(String ruta)  throws IOException {
        Stream<String> lineasDivision = Files.lines(Paths.get(ruta));
        String division = lineasDivision.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDivision(linea,Division.valueOf(division));
        });
    }

    public void asignarDivision(String linea, Division div)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDivision(div);
    }

    public void cargarArchivoAsignacionDepartamento(String ruta) throws IOException {
        Stream<String> lineasDepartamento = Files.lines(Paths.get(ruta));
        String departamento = lineasDepartamento.findFirst().orElseGet(null);
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            asignarDepartamento(linea,Departamento.valueOf(departamento));
        });
    }

    public void asignarDepartamento(String linea, Departamento dep)
    {
        Pattern pattern = Pattern.compile("\\s");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        lista.get(infos.get(0)).setDepartamento(dep);
    }

    public int obtenerNumeroEmpleados(){
        return lista.size();
    }

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

    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento(){
        Map<Division, Map<Departamento, Long>> contadoresDivisionDepartamento = new HashMap<>();
        Stream<Division> divisionStream=Stream.of(Division.values());
        divisionStream.forEach(division -> contadoresDivisionDepartamento.put(division, obtenerContadoresDepartamento(division)));
        return contadoresDivisionDepartamento;
    }

    public List<Empleado> buscarEmpleadosConDivisionSinDepartamento(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() != Division.DIVNA && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    public List<Empleado> buscarEmpleadosSinDivision(){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == Division.DIVNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    public List<Empleado> buscarEmpleadosSinDepartamento(Division div){
        List<Empleado> listaEmpleadosSD=new ArrayList<>();
        lista.entrySet().stream().filter(empleado -> (
                empleado.getValue().getDivision() == div && empleado.getValue().getDepartamento() == Departamento.DEPNA
        )).forEach(empleado -> listaEmpleadosSD.add(empleado.getValue()));
        return listaEmpleadosSD;
    }

    @Override
    public String toString(){
       return lista.toString();
    }

    public boolean hayDnisRepetidos(){
        List<String> listaDNI = lista.entrySet().stream().map(empleado -> empleado.getValue().getDni()).distinct().collect(Collectors.toList());
        if(listaDNI.size()!=lista.size())
            return true;
        else
            return false;
    }

    /*public Map<String,List<Empleado>> obtenerDnisRepetidos(){

    }*/

    public boolean hayCorreosRepetidos(){
        List<String> listaEmail = lista.entrySet().stream().map(empleado -> empleado.getValue().getEmail()).distinct().collect(Collectors.toList());
        if(listaEmail.size()!=lista.size())
            return true;
        else
            return false;
    }

    public Map<String,List<Empleado>> obtenerCorreosRepetidos(){
        Map<String,List<Empleado>> correos = new HashMap<>();
        Map<String,List<Empleado>> correosRepetidos = new HashMap<>();
        lista.entrySet().stream().map(empleado -> empleado.getValue().getEmail()).distinct().forEach(email -> {
            List<Empleado> lista_empleados=new ArrayList<>();
            lista.entrySet().stream().filter(empleado -> (
                    empleado.getValue().getEmail() == email
            )).forEach(empleado -> lista_empleados.add(empleado.getValue()));
            correos.put(email,lista_empleados);
        });

        correos.entrySet().stream().filter(correo -> (
                correo.getValue().size() > 1
        )).forEach(correo -> correosRepetidos.put(correo.getKey(),correo.getValue()));
        System.out.println(correosRepetidos.toString());
        return correosRepetidos;
    }
}
