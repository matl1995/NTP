package listado;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by m_ang on 09/03/2017.
 */
public class Listado {
    private Map<String, Empleado> lista;

    public Listado(String ruta) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(ruta));
        lineas.forEach(linea -> {

        });
    }

    public void crearEmpleado(String linea){
        Pattern pattern = Pattern.compile("(,)");
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());

        lista.put(infos.get(0),new Empleado(infos.get(0),infos.get(1),infos.get(2),infos.get(3)));
    }

    public void cargarArchivoAsignacionDivision(String ruta)  throws IOException {
        Stream<String> lineasDivision = Files.lines(Paths.get(ruta));
        String division = lineasDivision.findFirst().get();
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            lista.forEach((dni,empleado) -> {
                if(dni==linea.toString())
                    empleado.setDivision(Division.valueOf(division));
            });
        });
    }

    public void cargarArchivoAsignacionDepartamento(String ruta) throws IOException {
        Stream<String> lineasDepartamento = Files.lines(Paths.get(ruta));
        String departamento = lineasDepartamento.findFirst().get();
        Stream<String> lineasEmpleados = Files.lines(Paths.get(ruta)).skip(2);
        lineasEmpleados.forEach(linea -> {
            lista.forEach((dni,empleado) -> {
                if(dni==linea.toString())
                    empleado.setDepartamento(Departamento.valueOf(departamento));
            });
        });
    }

    public int obtenerNumeroEmpleados(){
        return lista.size();
    }

    public List<Empleado> buscarEmpleadosSinDepartamento(Division div){

    }

    public Map<Departamento, Long> obtenerContadoresDepartamento(Division div){

    }

    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento(){

    }

    @Override
    public String toString(){
       return lista.toString();
    }

    public boolean hayDnisRepetidos(){

    }

    public Map<String,List<Empleado>> obtenerDnisRepetidos(){

    }

    public boolean hayCorreosRepetidos(){

    }

    public Map<String,List<Empleado>> obtenerCorreosRepetidos(){

    }
}
