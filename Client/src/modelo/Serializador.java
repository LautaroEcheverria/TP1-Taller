package modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;

public class Serializador implements Serializable
{
    public Serializador()
    {
        super();
    }
    
    public void guardarAlmacen(Almacen almacen) throws IOException
    {
        String path = System.getProperty("user.dir") + "/Datos/";
        File folder = new File(path);
        if (!folder.isDirectory())
            folder.mkdir();
        String nombreAlmacen = almacen.getNombre();
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path+nombreAlmacen+".xml"))); // guarda en subdirectorio Datos
        encoder.writeObject(almacen);
        encoder.close();
    }
    
    public Almacen recuperarAlmacen(String nombreAlmacen) throws IOException, ClassNotFoundException
    {
        Almacen almacenAux = null;
        String path = System.getProperty("user.dir") + "/Datos/";
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path+nombreAlmacen+".xml"))); // // recupera de subdirectorio Datos
        if (decoder != null)
        {
            almacenAux = (Almacen) decoder.readObject();
            decoder.close();
        }
        return almacenAux;
    }
    
    public void guardarConsulta(HashMap consulta,String nombreArch) throws IOException
    {
        String path = System.getProperty("user.dir") + "/Datos/";
        File folder = new File(path);
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path+nombreArch+".xml"))); // guarda en subdirectorio Datos
        encoder.writeObject(consulta);
        encoder.close();
    }
}
