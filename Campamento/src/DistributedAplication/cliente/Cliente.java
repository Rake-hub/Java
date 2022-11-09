/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DistributedAplication.cliente;

import DistributedAplication.interfaces.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *es la clase cliente
 * @author carlos
 */
public class Cliente
{

    SI_Actividades si_actividades;
    SI_Merendero si_merendero;
    SI_Soga si_soga;
    SI_Tirolina si_tirolina;

    public Cliente(String ip,String servicio) throws NotBoundException, MalformedURLException, RemoteException
    {
        si_actividades=(SI_Actividades) Naming.lookup("//"+ip+"/"+servicio);
        si_merendero=(SI_Merendero) Naming.lookup("//"+ip+"/"+servicio);
       si_soga=(SI_Soga)Naming.lookup("//"+ip+"/"+servicio);
       si_tirolina=(SI_Tirolina) Naming.lookup("//"+ip+"/"+servicio);
    }


    public SI_Actividades getSi_actividades()
    {
        return si_actividades;
    }

    public SI_Merendero getSi_merendero()
    {
        return si_merendero;
    }

    public SI_Soga getSi_soga()
    {
        return si_soga;
    }

    public SI_Tirolina getSi_tirolina()
    {
        return si_tirolina;
    }
    
}
