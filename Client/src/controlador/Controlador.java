package controlador;

import excepciones.MateriaAlumnoNoExisteException;
import excepciones.NoExisteAlmacenException;
import excepciones.NoExisteDatoException;
import excepciones.NoExisteMateriaException;
import excepciones.NoExisteOperandoException;

import excepciones.YaExisteDatoException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import modelo.Almacen;

import modelo.Alumno;
import modelo.ParserXML;
import modelo.Serializador;

import org.xml.sax.SAXException;

import vista.Ventana;

public class Controlador implements ActionListener, KeyListener
{
    private Ventana ventana;
    private Serializador sdA;
    private Almacen almacen;
    
    public Controlador(Ventana ventana)
    {
        this.ventana = ventana;
        this.sdA = new Serializador();
        this.almacen = null;
    }

    public void elmetodo()
    {
        
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.ventana.getJbEjecutar())
        {
            String cadenaOrig = this.ventana.getCadenaComando(); // texto ingresado en la ventana
            cadenaOrig = cadenaOrig.toLowerCase();
            String cadena[] = cadenaOrig.split(" "); // divide cadena para ver la opcion ingresada
            if (cadenaOrig.contains("crear"))
            {
                if (cadena[0].equals("crear"))
                {
                    if (cadena.length > 1 && cadena.length<3)
                    {
                        this.almacen = new Almacen(cadena[1]); // crea almacen si estan bien los parametros, sino tira error
                        this.ventana.addMensaje("Almacen creado exitosamente");
                    }
                    else
                        this.ventana.mensajeError(0); 
                }
            }    
            else
            {
                if (cadenaOrig.contains("cargar"))
                {
                    if (cadena[0].equals("cargar"))
                    {
                        if (cadena.length > 1 && cadena.length<3)
                            try
                            {
                                this.almacen = sdA.recuperarAlmacen(cadena[1]); // recupera almacen si estan bien los parametros, sino tira error
                                this.ventana.addMensaje("Almacen cargado exitosamente");
                            } catch (ClassNotFoundException | IOException f)
                            {
                                this.ventana.mensajeError(3);
                            }
                        else
                            this.ventana.mensajeError(0);
                    }
                }  
                else
                {
                    if (cadenaOrig.contains("guardar"))
                    {
                        if (cadena[0].equals("guardar"))
                        {
                            if (this.almacen != null)
                            {
                                if (cadena.length == 1)
                                    try
                                    {
                                        this.sdA.guardarAlmacen(this.almacen); 
                                        this.ventana.addMensaje("Almacen guardado exitosamente");
                                    } catch (IOException f)
                                    {
                                    }
                                else
                                    this.ventana.mensajeError(0);
                            }
                            else
                            {
                                this.ventana.mensajeError(4);
                            }
                            
                        }
                    }
                    else
                    {
                        if (cadenaOrig.contains("insertar")) 
                        {
                            if (this.almacen != null)
                            {
                                ParserXML p = new ParserXML();
                                String lineaXML = this.ventana.getCadenaComando();
                                lineaXML = lineaXML.substring(8);
                                try {
                                    String[][] Lista = p.leoArgumento(lineaXML);
                                    Alumno nuevoAlumno = new Alumno(Lista[0][2],Lista[1][2],Lista[2][2],Lista[3][2],Lista[4][2]);
                                    for (int i = 6; i<= 5+Integer.parseInt(Lista[5][2])*2;i+=2)
                                        nuevoAlumno.agregaMateria(Lista[i][2], Double.parseDouble(Lista[i+1][2])); 
                                    this.ventana.addMensaje(nuevoAlumno.toString());
                                    this.almacen.agregaAlumno(nuevoAlumno);
                                } catch (IOException | ParserConfigurationException | SAXException f) 
                                {
                                } catch (YaExisteDatoException f) 
                                {
                                    this.ventana.mensajeError(4);
                                }
                            }
                            else 
                            {
                                this.ventana.mensajeError(4);
                            }
                        }
                        else
                        {
                            if (cadenaOrig.contains("eliminar"))
                            {
                                if (this.almacen != null)
                                {
                                    if (cadena.length > 1 && cadena.length < 3)
                                    {
                                        try
                                        {
                                            this.almacen.eliminaAlumno(cadena[1]);
                                            this.ventana.addMensaje("Alumno eliminado exitosamente");
                                        } catch (NoExisteDatoException f)
                                        {
                                            this.ventana.mensajeError(5);
                                        }
                                    }
                                    else
                                    {
                                        this.ventana.mensajeError(0);
                                    }
                                }
                                else
                                {
                                    this.ventana.mensajeError(4);
                                }
                            }
                            else
                            {
                                if (cadenaOrig.contains("consultar"))
                                {
                                    if (this.almacen != null)
                                        {
                                            if (cadena.length == 4 || cadena.length == 6)
                                            {
                                                HashMap<Alumno,Double> aux = null;
                                                try
                                                {
                                                    aux = this.almacen.consulta(cadena[1], cadena[2], cadena[3]);
                                                } catch (NoExisteMateriaException | NoExisteOperandoException f)
                                                {
                                                }
                                                if (cadena.length == 4)
                                                    {
                                                        Iterator<Alumno> it = aux.keySet().iterator();
                                                        while(it.hasNext())
                                                        {
                                                            Alumno alumnoAux = it.next();
                                                            if (alumnoAux.contieneMateria(cadena[1].toUpperCase()))
                                                                this.ventana.addMensaje(alumnoAux.toString() + " - " + cadena[1] +" - Nota: " +alumnoAux.valorAtributo(cadena[1]));
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (cadena[4].equalsIgnoreCase("toFile"))
                                                        {
                                                            try
                                                            {
                                                                this.sdA.guardarConsulta(aux,cadena[5]);
                                                            } catch (IOException f)
                                                            {
                                                            }
                                                        }
                                                        else
                                                        {
                                                            this.ventana.mensajeError(1);
                                                        }
                                                    }
                                            }
                                            else
                                            {
                                                this.ventana.mensajeError(0);
                                            }
                                        }
                                    else
                                    {
                                        this.ventana.mensajeError(4);
                                    }
                                }
                                else
                                {
                                    this.ventana.mensajeError(1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        // TODO Implement this method
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        // TODO Implement this method
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        String comando = this.ventana.getCadenaComando();
        if (comando.equalsIgnoreCase("INSERTAR")) 
        {
            this.ventana.getJTextField().setText("insertar <Alumno><Campo><Nombre>Id</Nombre><Tipo>String</Tipo><Valor>0001</Valor></Campo><Campo><Nombre>Fecha_de_Nacimiento</Nombre><Tipo>String</Tipo><Valor>01-01-1990</Valor></Campo><Campo><Nombre>Apellido_y_Nombre</Nombre><Tipo>String</Tipo><Valor>Juan Perez</Valor></Campo><Campo><Nombre>Domicilio</Nombre><Tipo>String</Tipo><Valor>Reconquista 5004</Valor></Campo><Campo><Nombre>Carrera</Nombre><Tipo>String</Tipo><Valor>Licenciatura en Física</Valor></Campo><Campo><Nombre>Materias</Nombre><Tipo>ArrayList</Tipo><Value>2</Value><Valores_ArrayList><Materia><Campo><Nombre>Nombre_Materia</Nombre><Tipo>String</Tipo><Valores>AlgebraA</Valores></Campo><Campo><Nombre>Nota</Nombre><Tipo>Double</Tipo><Valor>6.5</Valor></Campo></Materia><Materia><Campo><Nombre>Nombre_Materia</Nombre><Tipo>String</Tipo><Valores>AlgebraB</Valores></Campo><Campo><Nombre>Nota</Nombre><Tipo>Double</Tipo><Valor>7.5</Valor></Campo></Materia></Valores_ArrayList></Campo></Alumno>");
        }
    }
}
