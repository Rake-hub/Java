package SingleAllAplication.control;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import SingleAllAplication.modelo.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:09:15
 */
public class Nino extends Persona
{

    private int num_equipo_soga;
    private int puntuacion;
    private Semaphore tamanoyo_equipo;

    /**
     * El constructor del niño al cual se le pasa la clase campamento para que
     * pueda acceder y la clase logger para que pueda dejar un registro de su
     * actividad
     *
     * @param id
     * @param camp
     * @param log
     */
    public Nino(String id, Campamento camp, Logger log)
    {
        super(id, camp, log);
        tamanoyo_equipo = camp.getSoga().getScapacida();
        puntuacion = 0;

    }

    public void run()
    {
        ZonaComun zc = camp.getZonaComun();
        List<EntradaSalida> entradas = camp.getEntradaSalidas();
        EntradaSalida entrada = entradas.get((int) ((Math.random() * entradas.size())));
        EntradaSalida salida = entradas.get(entradas.size() - 1);

        //el niño escoge una cola aleatoriamente para entrear
        try
        {
            entrada.esperarcola(identificador);
            mostrarPantalla_Debuger("el nino con id " + identificador + " está en la cola");
            entrada.entrar(identificador);
            mostrarPantalla_Debuger("el nino con id " + identificador + " ha entrado");
            while (contadorActividades < 15)
            {

                zc.paseo(identificador);

                mostrarPantalla_Debuger("nino numeroActividades " + contadorActividades + "con id: " + identificador);
                escogerActividad();

            }
            salida.salir(identificador);
        } catch (InterruptedException e)
        {
        } catch (BrokenBarrierException ex)
        {
        }

    }

    @Override
    public void escogerActividad() throws InterruptedException, BrokenBarrierException
    {

        int numeroaletorio = (int) (Math.random() * 3);
        switch (numeroaletorio)
        {
            //soga
            case 0:
                if (tamanoyo_equipo.tryAcquire())
                {

                    log.escritura(Logger.TypeLog.INFO, "El nino con id " + identificador + " ha entrado en la soga");
                    mostrarPantalla_Debuger("nino entrando en la  soga " + "con id: " + identificador);
                    camp.getSoga().entrar(identificador);

                    camp.getSoga().jugar(identificador);
                    mostrarPantalla_Debuger("nino jugando en la  soga " + "con id: " + identificador);

                    Thread.sleep(7000);
                    camp.getSoga().salir(identificador);
                    mostrarPantalla_Debuger("nino saliendo de la  soga " + "con id: " + identificador);
                    contadorActividades++;

                }

                break;
            //tirolina
            case 1:

                camp.getTirolina().entrar(identificador);
                mostrarPantalla_Debuger("nino entrando en la  tirolina " + "con identificador: " + identificador);
                camp.getTirolina().jugar(identificador);
                mostrarPantalla_Debuger("nino jugando en la  tirolina " + "con identificador: " + identificador);
                camp.getTirolina().salir(identificador);
                mostrarPantalla_Debuger("nino saliendo en la  tirolina " + "con identificador: " + identificador);
                contadorActividades++;
                break;

            //Merendero
            case 2:
                //comprobamos si llega a 3 de puntuacion o si ha hecho 3 actividentificadorades
                if (((puntuacion + contadorActividades) == 3) || (contadorActividades == 3))
                {
                    camp.getMerendero().entrar(identificador);
                    mostrarPantalla_Debuger("nino entrar a comedero " + "con identificador: " + identificador);
                    camp.getMerendero().comer(identificador);
                    mostrarPantalla_Debuger("nino comiendo " + "con identificador: " + identificador);
                    Thread.sleep(7000);
                    camp.getMerendero().salir(identificador);
                    mostrarPantalla_Debuger("nino sale de comedero " + "con identificador: " + identificador);
                    contadorActividades++;
                }
                break;

        }

    }

    public int getPuntuacion()
    {
        return puntuacion;
    }

    public void setPuntuacion(int Puntuacion)
    {
        this.puntuacion = Puntuacion;
    }

    public int getNum_equipo_soga()
    {
        return num_equipo_soga;
    }

    public void setNum_equipo_soga(int num_equipo_soga)
    {
        this.num_equipo_soga = num_equipo_soga;
    }

}//end Nino
