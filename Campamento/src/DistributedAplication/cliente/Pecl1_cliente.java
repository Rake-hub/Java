/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DistributedAplication.cliente;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Alvaro
 */
public class Pecl1_cliente
{

    public static void main(String args[]) throws InterruptedException, NotBoundException, MalformedURLException, RemoteException
    {
        Cliente clie = new Cliente("127.0.0.1", "Dispatcher");
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new VistaCliente(clie).setVisible(true);
            }
        });

    }

}
