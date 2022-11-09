/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DistributedAplication.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Se define los metodos que se van a compartir por rmi
 *
 * @author carlos
 */
public interface SI_Merendero extends Remote
{

    int consultar_ninos_merendando() throws RemoteException;

    int consultar_bandejas_sucias() throws RemoteException;

    int consultar_bandejas_limpias() throws RemoteException;

}
