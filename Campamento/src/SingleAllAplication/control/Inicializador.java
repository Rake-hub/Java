/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.control;

import java.util.ArrayList;
import java.util.List;

import SingleAllAplication.modelo.*;
import SingleAllAplication.vista.*;
import java.util.Map;
import java.util.logging.Level;

/**
 * Se genera una clase aparte , esto se hace con la idea de cuando se implemente
 * la parte 2 se haga un extends de esta clase y se modifique los metodos
 * correspondientes , ya que el codigo tiene que quedar cerrado para
 * modificaciones y abierto para extensiones siguiendo los principios SOLID
 *
 * @author carlos
 */
public class Inicializador
{

    Logger log;

    SingleAllAplication.modelo.Campamento camp;
    List<EntradaSalida> es;

    EntradaSalida entradaA, entradaB, salida;
    Thread t1;

    public Inicializador()
    {
        //inicializamos las variables del campamentos
        camp = new SingleAllAplication.modelo.Campamento();
        log = new Logger();
        //selecionamos el output de nuestro programa
        log.setModoEscribirPantalla(true);
        log.setModoEscribirFichero(false);

        //inicializamos las variables de entradas y salida entrada A =[0] entrada B = [1] salida =[3]
        es = new ArrayList<EntradaSalida>();
        entradaA = new EntradaSalida(camp);
        entradaB = new EntradaSalida(camp);
        salida = new EntradaSalida(camp);
        es.add(entradaA);
        es.add(entradaB);
        es.add(salida);

        camp.setEntradaSalidas(es);

        camp.setZonaComun(new ZonaComun(camp));

        camp.setMerendero(new Merendero(camp));

        camp.setSoga(new Soga(camp));

        camp.setTirolina(new Tirolina(camp));

    }

    /**
     * Este metodo es el principal es que se va encarga de generar la vista, los
     * monitores y los ninos
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException
    {

        generarVista(camp);
        t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    generarMonitores(camp, log);
                    generarNinos(camp, log);
                } catch (InterruptedException ex)
                {
                    java.util.logging.Logger.getLogger(Inicializador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();

    }

    /**
     * Genera los monitores
     *
     * @param camp
     * @param log
     */
    public void generarMonitores(SingleAllAplication.modelo.Campamento camp, Logger log)
    {
        log.escritura(Logger.TypeLog.INFO, "Se generan los monitores");
        System.out.println("se generan los monitores");
        int id = 0;
        for (int i = 0; i < 4; i++)
        {
            id = i;
            Monitor monitor = new Monitor("M" + id, camp, log);
            camp.getMonitores_total().put("M" + id, monitor);
            monitor.start();

        }
    }

    /**
     * Este metodo es el que genera los niños hasta 20_000 se ha decido poner id
     * que se vaya repitidiendo cada 10_000
     *
     * @param camp
     * @param log
     * @throws InterruptedException
     */
    public void generarNinos(SingleAllAplication.modelo.Campamento camp, Logger log) throws InterruptedException
    {
        log.escritura(Logger.TypeLog.INFO, "Se generan los ninos");
        System.out.println("se generan los ninos");
        int id;
        int numeroaleatorio;
        //en el momento que se cierra el campamento dejan de venir ninos y tambien una vez que llega hasta los 20_000 ninos
        for (int i = 0; (i < 20_000) || !camp.isCerrar(); i++)
        {
            id = i;
            Nino nino = new Nino("N" + id, camp, log);
            //Los niños creados se guardan un hash map para su posterior reutilización
            camp.getNinos_total().put("N" + id, nino);

            //Se generar un Sleep para que no se generen todos los niños a la vez
            numeroaleatorio = (int) (Math.random() * (3 + 1));

            Thread.sleep(numeroaleatorio * 1000);
            nino.start();

        }
    }

    /**
     * Se encarga de generar la interfaz gráfica, este metodo se sobrescrive
     * vacio en el servidor , para que no se genere la vista ya que el servidor
     * extiende de esta clase
     *
     * @param camp
     */
    public void generarVista(SingleAllAplication.modelo.Campamento camp)
    {
        Thread t1, t2, t3, t4, t5;
        VistaCampamento vc = new VistaCampamento(this);
        VistaEntradas ve = new VistaEntradas(vc, camp);
        VistaSoga vs = new VistaSoga(vc, camp);
        VistaMerendero vm = new VistaMerendero(vc, camp);
        VistaTirolina vt = new VistaTirolina(vc, camp);
        VistaZonaComun vzc = new VistaZonaComun(vc, camp);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                vc.setVisible(true);

            }

        });
        t1 = new Thread(ve);
        t2 = new Thread(vs);
        t3 = new Thread(vt);
        t4 = new Thread(vm);
        t5 = new Thread(vzc);
        //subimos la prioridad de los hilos que pinta en la pantalla para que tenga prioridad respecto a los otros hilos para que puedan pintar
        t1.setPriority(7);
        t2.setPriority(7);
        t3.setPriority(7);
        t4.setPriority(7);
        t5.setPriority(7);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }

    public void reanudar() throws InterruptedException
    {
        Map<String, Persona> mapnino = camp.getNinos_total();
        Map<String, Persona> mapamoni = camp.getMonitores_total();
        t1.resume();
        for (String key : mapnino.keySet())
        {
            mapnino.get(key).resume();
        }

        for (String key : mapamoni.keySet())
        {
            mapamoni.get(key).resume();
        }

    }

    public void pausar() throws InterruptedException
    {
        Map<String, Persona> mapnino = camp.getNinos_total();
        Map<String, Persona> mapamoni = camp.getMonitores_total();
        t1.suspend();
        for (String key : mapnino.keySet())
        {
            mapnino.get(key).suspend();
        }
        for (String key : mapamoni.keySet())
        {
            mapamoni.get(key).suspend();
        }

    }

    public SingleAllAplication.modelo.Campamento getCamp()
    {
        return camp;
    }

}
