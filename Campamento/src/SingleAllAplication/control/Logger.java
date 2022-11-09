/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleAllAplication.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

/**
 *
 * @author carlos
 */
public class Logger
{

    File f;
    PrintWriter pw;
    ReadWriteLock lock;
    Lock r, w;
    Date date;
    boolean modoEscribirFichero;
    boolean modoEscribirPantalla;

    public Logger()
    {
        try
        {
            modoEscribirPantalla=false;
            modoEscribirFichero=false;
            lock = new ReentrantReadWriteLock();
            r = lock.readLock();
            w = lock.writeLock();
            f = new File("logs/evolucionCampamento.txt");
            pw = new PrintWriter(f);
        } catch (FileNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Se escoge el tipo de mensaje que se va amostrar 
 */
    public enum TypeLog
    {
        WARNING, INFO, ERROR
    }

    public void escritura(TypeLog t, String mensaje)
    {
        try
        {

            w.lock();
            date = new Date();
            pw.print("\n Time: " + new Timestamp(date.getTime()) + ": " + t.toString() + " ; " + mensaje);

        } catch (Exception e)
        {
        } finally
        {
            pw.flush();
            w.unlock();
        }

    }

    public boolean isModoEscribirFichero()
    {
        return modoEscribirFichero;
    }

    public void setModoEscribirFichero(boolean modoEscribirFichero)
    {
        this.modoEscribirFichero = modoEscribirFichero;
    }

    public boolean isModoEscribirPantalla()
    {
        return modoEscribirPantalla;
    }

    public void setModoEscribirPantalla(boolean modoEscribirPantalla)
    {
        this.modoEscribirPantalla = modoEscribirPantalla;
    }

}
