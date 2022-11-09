/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DistributedAplication.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author carlos
 */
public interface SI_Soga extends Remote
{

    int consultar_cola_espera_soga() throws RemoteException;

}
