package modelo;

import excepciones.MateriaAlumnoNoExisteException;
import excepciones.NoExisteMateriaException;

import java.io.Serializable;

import java.util.HashMap;

public class Alumno implements Serializable{
    
    private String id;
    private String fecha_de_nacimiento;
    private String apellido_nombre;
    private String domicilio;
    private String carrera;
    private HashMap <String,Double> materias;
    
    public Alumno(String id, String fecha_de_nacimiento, String apellido_nombre, String domicilio, String carrera) {
        this.id = id;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
        this.apellido_nombre = apellido_nombre;
        this.domicilio = domicilio;
        this.carrera = carrera;
        this.materias = new HashMap<String,Double>();
    }
    
    public Alumno()
    {
    }

    public String getId() {
        return id;
    }

    public String getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    public String getApellido_nombre() {
        return apellido_nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getCarrera() {
        return carrera;
    }

    public HashMap<String, Double> getMaterias() {
        return materias;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento)
    {
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public void setApellido_nombre(String apellido_nombre)
    {
        this.apellido_nombre = apellido_nombre;
    }

    public void setDomicilio(String domicilio)
    {
        this.domicilio = domicilio;
    }

    public void setCarrera(String carrera)
    {
        this.carrera = carrera;
    }
    
    public void agregaMateria(String nombreMateria, Double nota)
    {
        this.materias.put(nombreMateria.toUpperCase(), nota);
    }
    
    public boolean contieneMateria(String materia)
    {
        return this.materias.containsKey(materia);
    }
    
    public double valorAtributo(String materia)
    {
        Double aux = this.materias.get(materia.toUpperCase());
        return aux.doubleValue();
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Apellido y nombre: " + this.apellido_nombre;
    }

    public void setMaterias(HashMap<String, Double> materias)
    {
        this.materias = materias;
    }
}
