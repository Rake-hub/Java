package SingleAllAplication.modelo;

import SingleAllAplication.control.Monitor;
import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:07:47
 */
public class Campamento
{

    private boolean pausa, cerrar;
    private List<EntradaSalida> entradaSalidas;

    private int maximoNinos;
    private int numeroMonitores;
    private Semaphore entradas;
    private int numeroNinos;
    private Soga soga;
    private Tirolina tirolina;
    private ZonaComun zonaComun;
    private Merendero merendero;

    private Map<String, Persona> ninos_total, ninos_dentro, monitores_total, monitores_dentro;

    public Campamento()
    {
        entradaSalidas = new ArrayList<EntradaSalida>();
        maximoNinos = 50;
        numeroMonitores = 0;
        numeroNinos = 0;
        entradas = new Semaphore(50);
        ninos_total = new ConcurrentHashMap();
        monitores_total = new ConcurrentHashMap();
        ninos_dentro = new ConcurrentHashMap();
        monitores_dentro = new ConcurrentHashMap();
        //inicializamos dos variable bolean que manejan los dos botones de cerrar y pausar el campamento
        pausa = false;
        cerrar = false;

    }

    /**
     * Pausa o renaude el campmento dependiendo de una variable boolena
     *
     * @throws InterruptedException
     */
    public void pausar_renaudar() throws InterruptedException
    {
        Map<String, Persona> mapnino = ninos_total;
        Map<String, Persona> mapamoni = monitores_total;
        if (pausa)
        {

            for (String key : mapnino.keySet())
            {
                mapnino.get(key).suspend();
            }
            for (String key : mapamoni.keySet())
            {
                mapamoni.get(key).suspend();
            }
        } else
        {

            for (String key : mapnino.keySet())
            {
                mapnino.get(key).resume();
            }

            for (String key : mapamoni.keySet())
            {
                mapamoni.get(key).resume();
            }
        }

    }

    



    public List<EntradaSalida> getEntradaSalidas()
    {
        return entradaSalidas;
    }

    public void setEntradaSalidas(List<EntradaSalida> entradaSalidas)
    {
        this.entradaSalidas = entradaSalidas;
    }

    public int getMaximoNinos()
    {
        return maximoNinos;
    }

    public void setMaximoNinos(int maximoNinos)
    {
        this.maximoNinos = maximoNinos;
    }

    public int getNumeroMonitores()
    {
        return numeroMonitores;
    }

    public void setNumeroMonitores(int numeroMonitores)
    {
        this.numeroMonitores = numeroMonitores;
    }

    public int getNumeroNinos()
    {
        return numeroNinos;
    }

    public void setNumeroNinos(int numeroNinos)
    {
        this.numeroNinos = numeroNinos;
    }

    public Soga getSoga()
    {
        return soga;
    }

    public void setSoga(Soga soga)
    {
        this.soga = soga;
    }

    public Tirolina getTirolina()
    {
        return tirolina;
    }

    public void setTirolina(Tirolina tirolina)
    {
        this.tirolina = tirolina;
    }

    public ZonaComun getZonaComun()
    {
        return zonaComun;
    }

    public void setZonaComun(ZonaComun zonaComun)
    {
        this.zonaComun = zonaComun;
    }

    public void setNinos_total(Map<String, Persona> ninos_total)
    {
        this.ninos_total = ninos_total;
    }

    public Map<String, Persona> getMonitores_total()
    {
        return monitores_total;
    }

    public void setMonitores_total(Map<String, Persona> monitores_total)
    {
        this.monitores_total = monitores_total;
    }

    public Map<String, Persona> getNinos_dentro()
    {
        return ninos_dentro;
    }

    public void setNinos_dentro(Map<String, Persona> ninos_dentro)
    {
        this.ninos_dentro = ninos_dentro;
    }

    public Map<String, Persona> getMonitores_dentro()
    {
        return monitores_dentro;
    }

    public void setMonitores_dentro(Map<String, Persona> monitores_dentro)
    {
        this.monitores_dentro = monitores_dentro;
    }

    public Merendero getMerendero()
    {
        return merendero;
    }

    public void setMerendero(Merendero merendero)
    {
        this.merendero = merendero;
    }

    public boolean isPausa()
    {
        return pausa;
    }

    public void setPausa(boolean pausa)
    {
        this.pausa = pausa;
    }

    public boolean isCerrar()
    {
        return cerrar;
    }

    public void setCerrar(boolean cerrar)
    {
        this.cerrar = cerrar;
    }

    public Map<String, Persona> getNinos_total()
    {
        return ninos_total;
    }

    /**
     * este getter es para que las entradas compartan el mismo semaforo y se
     * comparte la capacidad
     */
    public Semaphore getEntradas()
    {
        return entradas;
    }

}//end Campamento
