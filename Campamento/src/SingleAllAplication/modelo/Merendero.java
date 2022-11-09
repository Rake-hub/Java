package SingleAllAplication.modelo;

import SingleAllAplication.control.Monitor;
import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:07:49
 */
public class Merendero
{

    private int capacidad;
    private Semaphore scapacidad, sbandejasLimpia, sbandejasSucia;
    private char idMonitor;

    private Map<String, Persona> ninosComiendo, ninosColaEspera;
    private Map<String, Persona> monitores;
    private Campamento camp;

    public Merendero(Campamento camp)
    {
        this.camp = camp;
        capacidad = 20;

        scapacidad = new Semaphore(capacidad);
        sbandejasLimpia = new Semaphore(0);
        sbandejasSucia = new Semaphore(25);
        ninosComiendo = new ConcurrentHashMap();
        ninosColaEspera = new ConcurrentHashMap();
        monitores = new ConcurrentHashMap();
    }
/**
 * Es metodo representa la entrada en el merendero se utiliza un id, para saber si es un monitor o un nino
 * @param id
 * @throws InterruptedException 
 */
    public void entrar(String id) throws InterruptedException
    {
        if (id.startsWith("N"))
        {
            ninosColaEspera.put(id, camp.getNinos_dentro().get(id));
            scapacidad.acquire();

        }
        if (id.startsWith("M"))
        {
            monitores.put(id, camp.getMonitores_dentro().get(id));

        }
    }

    public void comer(String id) throws InterruptedException
    {
        if (id.startsWith("N"))
        {
            sbandejasLimpia.acquire();
            ninosColaEspera.remove(id);
            ninosComiendo.put(id, camp.getNinos_dentro().get(id));
        }
    }

    public void limpiar(String id) throws InterruptedException
    {
        if (id.startsWith("M"))
        {
            sbandejasSucia.acquire();
            sbandejasLimpia.release();
            // monitores.get(id).setNumeroMeriendas(monitores.get(id).getNumeroMeriendas()+1);
        }
    }

    public void salir(String id)
    {
        if (id.startsWith("N"))
        {
            sbandejasSucia.release();
            ninosComiendo.remove(id);
            scapacidad.release();
        }
        if (id.startsWith("M"))
        {
            monitores.remove(id);
        }
    }

    public Map<String, Persona> getNinosComiendo()
    {
        return ninosComiendo;
    }

    public Map<String, Persona> getNinosColaEspera()
    {
        return ninosColaEspera;
    }

    public Map<String, Persona> getMonitores()
    {
        return monitores;
    }

    public int getBandejasLimpias()
    {
        return sbandejasLimpia.availablePermits();
    }

    public int getBandejasSucias()
    {
        return sbandejasSucia.availablePermits();
    }

}//end Merienda
