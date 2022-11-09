/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.modelo;

import SingleAllAplication.control.Monitor;
import SingleAllAplication.control.Nino;
import SingleAllAplication.control.Persona;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class EntradaSalida
{

    private boolean abierta;
    //estas dos listas son las que se mostrarán en pantalla 
    Map<String, Persona> ninos, monitores;

    private Campamento camp;

    private ReadWriteLock lock;
    private Lock r;
    private Lock w;
    Semaphore entradas;
    CountDownLatch cl = new CountDownLatch(1);

    public EntradaSalida(Campamento camp)
    {

        ninos = new ConcurrentHashMap();
        monitores = new ConcurrentHashMap();
        entradas = camp.getEntradas();
        lock = new ReentrantReadWriteLock();
        this.camp = camp;
        w = lock.writeLock();
        r = lock.readLock();

    }

    /**
     * Abre la entrada y al abrirla tarda entre 0,5 y 1 segundo
     *
     * @throws InterruptedException
     */
    public void abrirentrada() throws InterruptedException
    {
        int numeroaleatorio = (int) (Math.random() * (5 + 1));
        Thread.sleep(numeroaleatorio * 100);
        cl.countDown();
        abierta = true;

    }

    /**
     * Se utiliza que sea syncronice porque por cada entrada solamente puede
     * pasar una persona
     *
     * @param id
     */
    public synchronized void entrar(String id)
    {

        if (id.startsWith("N"))
        {
            camp.getNinos_dentro().put(id, camp.getNinos_total().get(id));
            ninos.remove(id);

        }
        if (id.startsWith("M"))
        {
            camp.getMonitores_dentro().put(id, camp.getMonitores_total().get(id));
            monitores.remove(id);
        }

    }

    /**
     * Metodo en el que tanto los ninos y monitores esperan en la cola para
     * entrar por la entrada
     *
     * @param id
     * @throws InterruptedException
     */
    public void esperarcola(String id) throws InterruptedException
    {

        if (id.startsWith("M"))
        {
            if (!abierta)
            {
                monitores.put(id, camp.getMonitores_total().get(id));
                abrirentrada();
            }

        }
        if (id.startsWith("N"))
        {
            try
            {
                ninos.put(id, camp.getNinos_total().get(id));
                if (!abierta)
                {
                    cl.await();
                }
                entradas.acquire();

            } catch (InterruptedException ex)
            {
                Logger.getLogger(EntradaSalida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo para que salgan tanto los ninos como los monitores del campamento
     *
     * @param id
     */
    public synchronized void salir(String id)
    {
        if (id.startsWith("M"))
        {
            camp.setNumeroNinos(camp.getNumeroNinos() - 1);
            camp.getMonitores_dentro().remove(id);
        }
        if (id.startsWith("N"))
        {
            camp.setNumeroMonitores(camp.getNumeroMonitores() - 1);
            entradas.release();
            camp.getNinos_dentro().remove(id);

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

}
