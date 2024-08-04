package com.sathwik.project;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JPanel;

public class PanelPrinter implements Printable {

    private JPanel panelToPrint;

    public PanelPrinter(JPanel panelToPrint) {
        this.panelToPrint = panelToPrint;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        panelToPrint.print(g2d);

        return Printable.PAGE_EXISTS;
    }

    public static void printPanel(JPanel panel) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new PanelPrinter(panel));

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }
}
