/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DistributedAplication.servidor;

import SingleAllAplication.control.Inicializador;
import SingleAllAplication.modelo.Campamento;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Para agregar funcionalidad hacemos un extend de la clase inicliación para
 * seguir el principio de Open close en el cual codigo tiene que quedar cerrado
 * para modificaciones y abierto para extensiones
 *
 * @author carlos
 */
public class Servidor extends Inicializador
{

    boolean activarventana;

    public Servidor()
    {
        super();
        activarventana = false;
    }
//sobrescribimos el metodo run para incorporar la nueva funcionalidad

    @Override
    public void run() throws InterruptedException
    {

        try
        {
            Dispatcher di = new Dispatcher(super.getCamp());
            Registry registry = LocateRegistry.createRegistry(1099); //Arranca rmiregistry local en el puerto 1099
            Naming.rebind("//localhost/Dispatcher", di);

            //llamamos al metodo run para que empiece a ejecutarse el metodo de la clase padre
            super.run();
        } catch (Exception e)
        {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    /**
     * Se sobreescribe este metodo para que sea vacio y no se genere la vista
     */
    @Override
    public void generarVista(Campamento camp)
    {
        if (activarventana)
        {
            super.generarVista(camp);
        }
    }

}
