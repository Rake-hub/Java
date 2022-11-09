package SingleAllAplication.control;

import SingleAllAplication.modelo.Campamento;
import SingleAllAplication.modelo.EntradaSalida;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

/**
 * @author carlos
 * @version 1.0
 * @created 12-abr.-2022 19:09:17
 */
public class Monitor extends Persona
{

    private int numeroMeriendas;

    public Monitor(String identificador, Campamento camp, Logger log)
    {
        super(identificador, camp, log);
        numeroMeriendas = 0;

    }

    /**
     * Es el metodo principal del monitor escoge una entrada dependiendo de su
     * id y luego entran en un bucle que no para hasta que no este interrumpido
     */
    @Override
    public void run()
    {
        try
        {
            List<EntradaSalida> entradas = camp.getEntradaSalidas();
            numberid = Integer.parseInt(identificador.substring(1));

            EntradaSalida entrada = entradas.get(numberid % 2);
            EntradaSalida salida = entradas.get(2);

            entrada.esperarcola(identificador);

            entrada.entrar(identificador);
            while (!Thread.interrupted())
            {
                if (((contadorActividades != 0) && (contadorActividades % 10 == 0)) || ((numeroMeriendas != 0) && (numeroMeriendas % 10 == 0)))
                {
                    camp.getZonaComun().paseo(identificador);
                    mostrarPantalla_Debuger("el monitor con id pasea " + identificador);

                }
                escogerActividad();

            }
            salida.salir(identificador);
        } catch (InterruptedException ex)
        {

        } catch (BrokenBarrierException ex)
        {

        }
    }

    /**
     * Escoge una atividad dependiendo del id que tengan
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    @Override
    public void escogerActividad() throws InterruptedException, BrokenBarrierException
    {
        int numero = (numberid) % 4;
        switch (numero)
        {
            //merendero
            case 0:
                merienda();
                break;
            case 1:
                merienda();
                break;
            //tirolina            
            case 2:
                mostrarPantalla_Debuger("El monitor con id " + identificador + " entra en la tirolina");
                camp.getTirolina().entrar(identificador);
                Thread.sleep(4500);
                mostrarPantalla_Debuger("El monitor con id " + identificador + " sale de la tirolina");
                camp.getTirolina().salir(identificador);
                break;
            //soga
            case 3:
                mostrarPantalla_Debuger("El monitor con id " + identificador + " entra en la soga");
                camp.getSoga().entrar(identificador);
                mostrarPantalla_Debuger("El monitor con id " + identificador + " repartir equipo");
                camp.getSoga().repartir_equipo();
                mostrarPantalla_Debuger("El monitor con id " + identificador + " eligen un ganador en la soga");
                camp.getSoga().jugar(identificador);
                mostrarPantalla_Debuger("El monitor con id " + identificador + " sale de la soga");
                camp.getSoga().salir(identificador);
                break;
        }

    }

    /**
     * metodo en cual los monitores entra en el merendero
     *
     * @throws InterruptedException
     */
    private void merienda() throws InterruptedException
    {
        mostrarPantalla_Debuger("El monitor con id " + identificador + " entra en el merendero");
        camp.getMerendero().entrar(identificador);
        numeroMeriendas = 0;
        while (numeroMeriendas < 10)
        {

            mostrarPantalla_Debuger("El monitor con id " + identificador + " está limpiando las bandejas");
            camp.getMerendero().limpiar(identificador);
            Thread.sleep((long) (1000 * (Math.random() * 1000 % 3) + 3000));
            numeroMeriendas++;

        }
        camp.getMerendero().salir(identificador);
    }

    public int getNumeroMeriendas()
    {
        return numeroMeriendas;
    }

    public void setNumeroMeriendas(int numeroMeriendas)
    {
        this.numeroMeriendas = numeroMeriendas;
    }

}//end Monitor
