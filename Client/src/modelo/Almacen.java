package modelo;

import excepciones.MateriaAlumnoNoExisteException;
import excepciones.NoExisteDatoException;
import excepciones.NoExisteMateriaException;
import excepciones.NoExisteOperandoException;
import excepciones.YaExisteDatoException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Almacen implements Serializable{
    
    private String nombre;
    private HashMap <String,Alumno> alumnos;
    private ArrayList<String> materiasAlmacen;
    
    public Almacen()
    {
    }
    
    public Almacen(String nombre) {
        this.nombre = nombre;
        this.alumnos = new HashMap <String,Alumno>();
        this.materiasAlmacen = new ArrayList<String>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setAlumnos(HashMap<String, Alumno> alumnos)
    {
        this.alumnos = alumnos;
    }

    public HashMap<String, Alumno> getAlumnos()
    {
        return alumnos;
    }

    public void setMateriasAlmacen(ArrayList<String> materiasAlmacen)
    {
        this.materiasAlmacen = materiasAlmacen;
    }

    public ArrayList<String> getMateriasAlmacen()
    {
        return materiasAlmacen;
    }

    public void agregaAlumno(Alumno alumno) throws YaExisteDatoException
    {
        if (this.alumnos.containsKey(alumno.getId()))
            throw new YaExisteDatoException();
        else
        {
            this.alumnos.put(alumno.getId(),alumno);
            Iterator<String> it = alumno.getMaterias().keySet().iterator();
            while (it.hasNext())
            {
                String aux = it.next();
                if (!this.materiasAlmacen.contains(aux)) // carga la materia a una "base de datos" de materias del almacen, solo nombre de materia
                    this.materiasAlmacen.add(aux.toUpperCase());
            }
        }
    }
    
    public void eliminaAlumno(String id) throws NoExisteDatoException
    {
        if (this.alumnos.containsKey(id))
            this.alumnos.remove(id);
        else
            throw new NoExisteDatoException();
    }
    
    // metodo que devuelve HashMap con clave Alumno y valor nota de los alumnos que tienen la materia ingresada por paramtetro
    public HashMap<Alumno,Double> consulta(String materia, String operando, String valor) throws NoExisteMateriaException,NoExisteOperandoException
    {
        HashMap<Alumno,Double> aux= new HashMap<Alumno,Double>();
        if (this.materiasAlmacen.contains(materia.toUpperCase())) // revisa en todas las materias del almacen si existe la materia ingresada por parametro
        {
            Iterator<Alumno> it = this.alumnos.values().iterator();
            int val = Integer.parseInt(valor);
            while (it.hasNext())
            {
                Alumno auxiliar = it.next();
                if (auxiliar.contieneMateria(materia.toUpperCase()))
                {
                    double nota = auxiliar.valorAtributo(materia.toUpperCase());
                    switch (operando)
                    {
                        case ("=="): if (nota == val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        case ("!="): if (nota != val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        case (">"): if (nota > val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        case ("<"): if (nota < val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        case (">="): if (nota >= val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        case ("<="): if (nota <= val)
                                            aux.put(auxiliar, new Double(val));
                        break;
                        default: throw new NoExisteOperandoException();
                    }
                }
            }
            return aux;
        }
        else
            throw new NoExisteMateriaException();
    }
}