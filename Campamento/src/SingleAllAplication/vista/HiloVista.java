/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.vista;

import SingleAllAplication.control.*;

import SingleAllAplication.modelo.*;

import java.util.Map;
import java.util.logging.Level;

import javax.swing.JTextField;

/**
 *
 * @author Alvaro
 */
public abstract class HiloVista
{

    VistaCampamento vc;
    Campamento camp;

    JTextField[] jtf;

    public HiloVista(VistaCampamento vc, Campamento camp)
    {
        this.vc = vc;
        this.camp = camp;

    }

    public String iterarMap(Map<String, Persona> mapa)
    {
        StringBuffer sb = new StringBuffer();
        for (String key : mapa.keySet())
        {
            sb.append(key);
            sb.append(" ");
        }

        return sb.toString();

    }

 

    public abstract void actulizarVista();

    public void esperar()
    {
            try
            {
                Thread.sleep(0);
                //tasa de refresco de la pantalla  60 fps 
//                Thread.sleep(1000/60);
            } catch (InterruptedException ex)
            {
                java.util.logging.Logger.getLogger(VistaMerendero.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

}
