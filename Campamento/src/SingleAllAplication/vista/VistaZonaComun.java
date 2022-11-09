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
public class VistaZonaComun extends HiloVista implements Runnable
{

    Map<String, Persona> ninosZC, monitoresZC;

    public VistaZonaComun(VistaCampamento vc, Campamento camp)
    {
        super(vc, camp);
        jtf = new JTextField[2];
        jtf[0] = vc.getZona_monitores();
        jtf[1] = vc.getZona_ninos();
        ninosZC = camp.getZonaComun().getNinos();
        monitoresZC = camp.getZonaComun().getMonitores();
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
        jtf[0].setText(iterarMap(monitoresZC));
        jtf[1].setText(iterarMap(ninosZC));
    }

}
