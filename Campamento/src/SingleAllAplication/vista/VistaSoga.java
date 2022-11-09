/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.vista;

import SingleAllAplication.control.*;
import SingleAllAplication.modelo.*;
import java.util.Map;
import javax.swing.JTextField;

/**
 *
 * @author carlos
 */
public class VistaSoga extends HiloVista implements Runnable
{

    Map<String, Persona> monitorSoga, ninosSoga, equipoA, equipoB;

    public VistaSoga(VistaCampamento vc, Campamento camp)
    {
        super(vc, camp);

        jtf = new JTextField[4];
        jtf[0] = vc.getSoga_cola();
        jtf[1] = vc.getSoga_monitor();
        jtf[2] = vc.getEquipoA();
        jtf[3] = vc.getEquipoB();
        ninosSoga = camp.getSoga().getNinos_colaEspera();
        equipoA = camp.getSoga().getEquipoA();
        equipoB = camp.getSoga().getEquipoB();
        monitorSoga = camp.getSoga().getMonitor();
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
        //soga cola
        jtf[0].setText(iterarMap(ninosSoga));
        //soga monitor
        jtf[1].setText(iterarMap(monitorSoga));
        //soga 
        jtf[2].setText(iterarMap(equipoA));
        jtf[3].setText(iterarMap(equipoB));
    }
}
