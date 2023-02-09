package SingleAllAplication.modelo;

import SingleAllAplication.control.Monitor;

import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import SingleAllAplication.control.Monitor;
import SingleAllAplication.control.Nino;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:07:44
 */


public class Tirolina
{

    private String idMonitor;
    private final Campamento camp;
    private String idpreparacion;
    private String idfinalizacion;
    private String idTirolina;
    private final Semaphore SemaforoTirolina;
    private CountDownLatch cl;
    private final CyclicBarrier clnino;
    private int numerodeusos;

    private final Map<String, Persona> ninos;

    public Tirolina(Campamento camp)
    {
        //para que accedan por el orden de entrada  hacemos que sea justo
        SemaforoTirolina = new Semaphore(1, true);
        idMonitor = "";
        idTirolina = "";
        idpreparacion = "";
        numerodeusos = 0;
        this.camp = camp;
        ninos = new ConcurrentHashMap();
        cl = new CountDownLatch(1);
        clnino = new CyclicBarrier(2);
    }

    /**
     * Metodo por el cual entran tanto los niños como lso monitores a tirolina
     *
     * @param id
     * @throws InterruptedException
     */
    public void entrar(String id) throws InterruptedException
    {
        if (id.startsWith("N"))
        {
            ninos.put(id, camp.getNinos_dentro().get(id));
            cl.await();
            SemaforoTirolina.acquire();
            ninos.remove(id);
            idpreparacion = id;
            Thread.sleep(1000);
            idpreparacion = "";

        }
        if (id.startsWith("M"))
        {
            cl.countDown();
            idMonitor = id;

        }
    }

    public void jugar(String id) throws InterruptedException
    {
        idTirolina = id;
        Thread.sleep(3000);
        idTirolina = "";

    }

    /**
     * En este metodo el monitor espera a que el niño termine y se suma uno el
     * numero de actividades que ha hecho y se reinicia el countDownLacth y se
     * utiliza un CycliBarrier para que el monitor espere a que el niño termine
     *
     * @param id
     * @throws InterruptedException
     */
    public void salir(String id) throws InterruptedException, BrokenBarrierException
    {
        if (id.startsWith("N"))
        {
            idfinalizacion = id;
            Thread.sleep(500);
            idfinalizacion = "";
            numerodeusos++;
            SemaforoTirolina.release();

            clnino.await();
        } else
        {
            clnino.await();
            Monitor monitor = (Monitor) camp.getMonitores_dentro().get(id);
            monitor.setContadorActividades(monitor.getContadorActividades() + 1);
            if (monitor.getContadorActividades() % 10 == 0)
            {
                idMonitor = "";
            }

            cl = new CountDownLatch(1);

        }

    }

    public String getIdMonitor()
    {
        return idMonitor;
    }

    public String getIdpreparacion()
    {
        return idpreparacion;
    }

    public String getIdfinalizacion()
    {
        return idfinalizacion;
    }

    public String getIdTirolina()
    {
        return idTirolina;
    }

    public Map<String, Persona> getNinos()
    {
        return ninos;
    }

    public int getNumerodeusos()
    {
        return numerodeusos;
    }

}//end Tirolina
