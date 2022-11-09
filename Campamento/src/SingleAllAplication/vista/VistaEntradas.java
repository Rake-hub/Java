/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.vista;

import SingleAllAplication.modelo.Campamento;


import SingleAllAplication.control.Persona;

import java.util.Map;

import javax.swing.JTextField;

/**
 *
 * @author carlos
 */
public class VistaEntradas extends HiloVista implements Runnable
{

    Map<String, Persona> ninosEntradaA, ninosEntradaB;

    public VistaEntradas(VistaCampamento vc, Campamento camp)
    {
        super(vc, camp);
        jtf = new JTextField[2];
        ninosEntradaA =  camp.getEntradaSalidas().get(0).getNinos();
        ninosEntradaB = camp.getEntradaSalidas().get(1).getNinos();
        jtf[0] = vc.getPuertaA_cola();
        jtf[1] = vc.getPuertaB_cola();
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
        jtf[0].setText(iterarMap(ninosEntradaB));
        jtf[1].setText(iterarMap(ninosEntradaA));

    }
}
