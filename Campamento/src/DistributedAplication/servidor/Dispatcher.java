package DistributedAplication.servidor;

import DistributedAplication.interfaces.*;
import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;
import SingleAllAplication.modelo.Campamento;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 *
 * @author carlos
 */
public class Dispatcher extends UnicastRemoteObject implements SI_Tirolina,SI_Soga, SI_Actividades, SI_Merendero {

    Campamento camp;

    public Dispatcher(Campamento camp)throws RemoteException {
        this.camp = camp;
    }

    @Override
    public int consultar_cola_espera_soga() throws RemoteException {
        
        return camp.getSoga().getNinos_colaEspera().size();
    }

    @Override
    public int cosultar_actividades_nino(String id) throws RemoteException {
        int numero_actividades=0;
        Map<String, Persona> ninos = camp.getNinos_total();
        if(ninos.containsKey(id)){
            numero_actividades= ninos.get(id).getContadorActividades();
        }
        return numero_actividades;
    }

    @Override
    public int consultar_ninos_merendando() throws RemoteException {
        return Math.abs(camp.getMerendero().getBandejasLimpias()-camp.getMerendero().getBandejasSucias());
    }

    @Override
    public int consultar_bandejas_sucias() throws RemoteException {
        return camp.getMerendero().getBandejasSucias();
    }

    @Override
    public int consultar_bandejas_limpias() throws RemoteException {
        return camp.getMerendero().getBandejasLimpias();
    }

    @Override
    public int consultar_cola_espera_tirolina() throws RemoteException {
       return camp.getTirolina().getNinos().size();
    }

    @Override
    public int consultar_numero_usos_tirolina() throws RemoteException {
        return camp.getTirolina().getNumerodeusos();
    }

}
