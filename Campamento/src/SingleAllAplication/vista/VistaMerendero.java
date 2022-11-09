/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.vista;

import SingleAllAplication.control.*;

import SingleAllAplication.modelo.*;

import java.util.Map;
import javax.swing.JTextField;

;

/**
 *
 * @author carlos
 */
public class VistaMerendero extends HiloVista implements Runnable
{

    Map<String, Persona> ninosComiendo, ninosMerenderoColaEspera, monitoresM;

    public VistaMerendero(VistaCampamento vc, Campamento camp)
    {
        super(vc, camp);
        jtf = new JTextField[5];
        jtf[0] = vc.getMerendero_cola();
        jtf[1] = vc.getBandejas_limpias();
        jtf[2] = vc.getBandejas_sucias();
        jtf[3] = vc.getMonitores_merendero();
        jtf[4] = vc.getNinos_merendero();
        ninosComiendo = camp.getMerendero().getNinosComiendo();
        ninosMerenderoColaEspera = camp.getMerendero().getNinosColaEspera();
        monitoresM = camp.getMerendero().getMonitores();
    }

    @Override
    public void run()
    {
        while (true)
        {
            actulizarVista();
            esperar();
        }
    }

    @Override
    public void actulizarVista()
    {
        jtf[0].setText(iterarMap(ninosMerenderoColaEspera));
        jtf[1].setText(camp.getMerendero().getBandejasLimpias() + "");
        jtf[2].setText(camp.getMerendero().getBandejasSucias() + "");
        jtf[3].setText(iterarMap(monitoresM));
        jtf[4].setText(iterarMap(ninosComiendo));

    }

}
