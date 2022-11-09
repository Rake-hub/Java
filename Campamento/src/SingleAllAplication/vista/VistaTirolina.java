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
public class VistaTirolina extends HiloVista implements Runnable
{

    Map<String, Persona> ninosTirolina, monitorTirolina;

    public VistaTirolina(VistaCampamento vc, Campamento camp)
    {
        super(vc, camp);
        jtf = new JTextField[5];
        jtf[0] = vc.getTirolina_cola();
        jtf[1] = vc.getTirolina_monitor();
        jtf[2] = vc.getTirolina_preparacion();
        jtf[3] = vc.getTirolina_jugando();
        jtf[4] = vc.getTirolina_finalizacion();
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
        jtf[0].setText(iterarMap(camp.getTirolina().getNinos()));
        jtf[1].setText(camp.getTirolina().getIdMonitor());
        jtf[2].setText(camp.getTirolina().getIdpreparacion());
        jtf[3].setText(camp.getTirolina().getIdTirolina());
        jtf[4].setText(camp.getTirolina().getIdfinalizacion());
    }

}
