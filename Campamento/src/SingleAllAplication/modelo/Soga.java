package SingleAllAplication.modelo;

import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:05:55
 */
public class Soga
{

    private CyclicBarrier cycliBarrierA, colatotal;
    private CyclicBarrier cycliBarrierB;
    private CyclicBarrier escogerbarrier;
    private Map<String, Persona> equipoA, equipoB, ninos_colaEspera;
    private Map<String, Persona> monitor;
    private Semaphore scapacida, sequipoA, sequipoB;
    private CountDownLatch cdl, cdl_escoger;
    private char IdMonitor;
    private int maximoEquipo;

    private Campamento camp;
    private int ganador;
    private List jugadores;

    //creamos una variable bolean para ver si está lleno
    AtomicBoolean boleanlleno;
    AtomicInteger intLLeno;

    public Soga(Campamento camp)
    {
        this.camp = camp;
        cdl = new CountDownLatch(1);
        cdl_escoger = new CountDownLatch(1);
        equipoA = new ConcurrentHashMap();
        equipoB = new ConcurrentHashMap();
        monitor = new ConcurrentHashMap();
        ninos_colaEspera = new ConcurrentHashMap();
        scapacida = new Semaphore(10, true);
        sequipoA = new Semaphore(5, true);
        sequipoB = new Semaphore(5, true);

        colatotal = new CyclicBarrier(11);

        cycliBarrierA = new CyclicBarrier(5);
        cycliBarrierB = new CyclicBarrier(5);
        jugadores = Collections.synchronizedList(new ArrayList<Persona>());
        ganador = 0;
        boleanlleno = new AtomicBoolean();
        boleanlleno.set(false);
        intLLeno = new AtomicInteger();
        intLLeno.set(0);

        /*utilizamos una barrera ciclica para que esperen que termine todos los niños ç
        y el monitor y se le asigna una tarea en la cual se escoge el ganador con un int ;
        0=gana equipo A ; gana equipo B
         */
        escogerbarrier = new CyclicBarrier(11, new Runnable()
        {
            @Override
            public void run()
            {

                ganador = (int) (Math.random() * 1000 % 2);
            }
        });

    }

    /**
     * En este metodo entra los niños y los monitores para diferenciarlos se
     * utiliza el id y aquí los niños son seleccionados en que equipo entra se
     * evalua el numero de participantes que faltan y tambien que no esten
     * llenos los dos equipos
     *
     * @param id
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void entrar(String id) throws InterruptedException, BrokenBarrierException
    {
        if (id.startsWith("N"))
        {
            ninos_colaEspera.put(id, camp.getNinos_dentro().get(id));
            //llenamos un array para luego selcionar los ninos
            jugadores.add(ninos_colaEspera.get(id));
            //esperamos que la lista se cargue de ninos 
            colatotal.await();
            boleanlleno.set(true);
            //esperamos a que el monitor escoga a que equipo va cada uno
            cdl_escoger.await();
            Nino nino = (Nino) camp.getNinos_dentro().get(id);
            int n = nino.getNum_equipo_soga();
            ninos_colaEspera.remove(id);
            switch (n)
            {
                case 0:
                    if (sequipoA.tryAcquire())
                    {
                        equipoA.put(id, camp.getNinos_dentro().get(id));
                        cycliBarrierA.await();
                    } else if (sequipoB.tryAcquire())
                    {
                        equipoB.put(id, camp.getNinos_dentro().get(id));
                        cycliBarrierB.await();
                    }

                    break;
                case 1:
                    if (sequipoB.tryAcquire())
                    {
                        equipoB.put(id, camp.getNinos_dentro().get(id));
                        cycliBarrierB.await();
                    } else if (sequipoA.tryAcquire())
                    {
                        equipoA.put(id, camp.getNinos_dentro().get(id));
                        cycliBarrierA.await();
                    }
                    break;
            }
            cdl.await();

        }

        if (id.startsWith("M"))
        {
            monitor.put(id, camp.getMonitores_dentro().get(id));
            cdl.countDown();
        }
    }

    /**
     * En este metodo terminan de jugar y se elige el ganador
     *
     * @param id
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void jugar(String id) throws InterruptedException, BrokenBarrierException
    {
        escogerbarrier.await();
        if (id.startsWith("N"))
        {
            if (equipoA.containsKey(id) && ganador == 0)
            {
                Nino nino = (Nino) camp.getNinos_dentro().get(id);
                nino.setPuntuacion(nino.getPuntuacion() + 1);
            } else if (equipoB.containsKey(id) && ganador == 1)
            {
                Nino nino = (Nino) camp.getNinos_dentro().get(id);
                nino.setPuntuacion(nino.getPuntuacion() + 1);
            }
        }
    }

    /**
     * En este metodo se encarga de escoger el monitor en que equipo va cada
     * uno,
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void repartir_equipo() throws InterruptedException, BrokenBarrierException
    {
        colatotal.await();
        for (int i = 0; i < jugadores.size(); i++)
        {
            Nino n = (Nino) jugadores.get(i);
            n.setNum_equipo_soga(i % 2);
        }//borramos la lista
        jugadores = Collections.synchronizedList(new ArrayList<Persona>());
        cdl_escoger.countDown();
    }

    public void salir(String id)
    {
        if (id.startsWith("N"))
        {

            if (equipoB.containsKey(id))
            {   
                sequipoB.release();
                equipoB.remove(id);
            } else
            {
                sequipoA.release();
                equipoA.remove(id);
            }
            scapacida.release();
        } else if (id.startsWith("M"))
        {
            monitor.get(id).setContadorActividades(monitor.get(id).getContadorActividades() + 1);
            monitor.remove(id);
            intLLeno.set(0);
            cdl = new CountDownLatch(1);
        }
    }

    public Map<String, Persona> getEquipoA()
    {
        return equipoA;
    }

    public Map<String, Persona> getEquipoB()
    {
        return equipoB;
    }

    public Map<String, Persona> getNinos_colaEspera()
    {
        System.out.println("prueba");
        return ninos_colaEspera;
    }

    public Map<String, Persona> getMonitor()
    {
        return monitor;
    }

    public Semaphore getScapacida()
    {
        return scapacida;
    }

}//end Soga
