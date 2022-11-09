package SingleAllAplication.modelo;

import SingleAllAplication.control.Monitor;
import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:07:01
 */
public class ZonaComun
{

    Map<String, Persona> ninos;
    Map<String, Persona> monitores;

    //utilizamos lock de tipo lectura y escritura para poder hacer escrituras y poder bloquear cuando vayamos a escribir. hacemos tanto para el monitor como para  nino
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock r = lock.readLock();
    private Lock w = lock.writeLock();

    private ReadWriteLock lockm = new ReentrantReadWriteLock();
    private Lock rm = lockm.readLock();
    private Lock wm = lock.writeLock();
    private Campamento camp;

    public ZonaComun(Campamento camp)
    {
        ninos = new ConcurrentHashMap();
        monitores = new ConcurrentHashMap();
        this.camp = camp;
    }

    public void paseo(String id)
    {
        if (id.startsWith("N"))
        {
            try
            {

                ninos.put(id, camp.getNinos_dentro().get(id));

                Thread.sleep((long) (3000 * Math.random() + 2000));

                ninos.remove(id);

            } catch (InterruptedException ex)
            {
                Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (id.startsWith("M"))
        {
            try
            {

                monitores.put(id, camp.getMonitores_dentro().get(id));

                Thread.sleep((long) (2000 * Math.random() + 1000));

                monitores.remove(id);

            } catch (InterruptedException ex)
            {
                Logger.getLogger(ZonaComun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Map<String, Persona> getNinos()
    {
        return ninos;
    }

    public Map<String, Persona> getMonitores()
    {
        return monitores;
    }

}//end ZonaComun
