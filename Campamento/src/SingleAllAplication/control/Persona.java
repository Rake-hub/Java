/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.control;

import SingleAllAplication.modelo.Campamento;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author carlo
 */
public abstract class Persona extends Thread
{

    protected String identificador;
    protected int contadorActividades;
   
    protected int numberid;
    Campamento camp;
    Logger log;
    boolean pantalla, fichero;

    public Persona(String identificador, Campamento camp, Logger log)
    {

        this.log = log;
        this.identificador = identificador;
       
        contadorActividades = 0;
        this.camp = camp;
        pantalla = this.log.isModoEscribirPantalla();
        fichero = this.log.isModoEscribirFichero();
        numberid = Integer.parseInt(identificador.substring(1));

    }

public abstract void escogerActividad()throws InterruptedException, BrokenBarrierException;

    /**
     * Este metodo imprime en el loger o también puede mostrar la salida desde
     * la linea de comando
     *
     * @param msg
     */
    public void mostrarPantalla_Debuger(String msg)
    {
        if (pantalla)
        {
            System.out.println(msg);
        }
        if (fichero)
        {
            log.escritura(Logger.TypeLog.INFO, msg);

        }
    }

    public String getIdentificador()
    {
        return identificador;
    }

    public void setIdentificador(String identificador)
    {
        this.identificador = identificador;
    }

    public int getContadorActividades()
    {
        return contadorActividades;
    }

    public void setContadorActividades(int contadorActividades)
    {
        this.contadorActividades = contadorActividades;
    }
    
    
    
}
