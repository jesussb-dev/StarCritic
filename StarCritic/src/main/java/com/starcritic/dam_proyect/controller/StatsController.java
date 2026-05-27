/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.starcritic.dam_proyect.controller;

import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.data.database.EstadisticasDB;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.view.StatsDialog;
import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author jsb
 */
public class StatsController {

    private StatsDialog view;
    private Model model;
    private static final Color CHART_BG = UIStyle.BG_PRIMARY;
    private static final Color PLOT_BG = UIStyle.BG_CARD;
    private static final Color GRID_LINE = UIStyle.BORDER;
    private static final Color AXIS_TEXT = UIStyle.TEXT;
    private static final Color TITLE_TEXT = UIStyle.TITLE;
    private static final Color GOLD = UIStyle.ACCENT;
    private static final Color GOLD_DARK = UIStyle.ACCENT_DARK;
    private static final Color GOLD_ALPHA = new Color(GOLD.getRed(), GOLD.getGreen(), GOLD.getBlue(), 100);
    private static final Color[] PIE_COLORS = {GOLD, UIStyle.SUCCESS, new Color(0x60A5FA), new Color(0xA78BFA), new Color(0xF87171),};

    public StatsController(StatsDialog view, Model model) {
        this.view = view;
        this.model = model;
        this.createdMonthsGraphic();
        this.crearOrigenContenidosGraph();
        this.crearDistribucionPuntuacionesGraph();
    }

    private void applyCommonChartStyle(JFreeChart chart) {
        chart.setBackgroundPaint(CHART_BG);
        chart.setAntiAlias(true);
        chart.setBorderVisible(false);
        if (chart.getTitle() != null) {
            chart.getTitle().setPaint(TITLE_TEXT);
            chart.getTitle().setFont(UIStyle.FONT_SUBTITLE);
            chart.getTitle().setPadding(10, 0, 6, 0);
        }
        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setBackgroundPaint(UIStyle.BG_SUBTLE);
            legend.setItemFont(UIStyle.FONT_BODY);
            legend.setItemPaint(AXIS_TEXT);
            legend.setFrame(new BlockBorder(GRID_LINE));
        }
    }

    private void createdMonthsGraphic() {
        BackgroundWork.run(
                () -> EstadisticasDB.topContenidoMasVisitado(10),
                stats -> {
                    HashMap<String, Double> visitas = (HashMap<String, Double>) stats;
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (Map.Entry<String, Double> entry : visitas.entrySet()) {
                        dataset.addValue(entry.getValue(), "Visitas", entry.getKey());
                    }

                    JFreeChart chart = ChartFactory.createBarChart(
                            "Contenidos mas vistos", "Contenido", "Visitas",
                            dataset, PlotOrientation.HORIZONTAL, false, true, false);
                    applyCommonChartStyle(chart);

                    CategoryPlot plot = chart.getCategoryPlot();
                    plot.setBackgroundPaint(PLOT_BG);
                    plot.setOutlinePaint(GRID_LINE);
                    plot.setDomainGridlinePaint(GRID_LINE);
                    plot.setRangeGridlinePaint(GRID_LINE);
                    plot.setRangeGridlineStroke(new BasicStroke(0.7f));
                    plot.setDomainGridlinesVisible(false);
                    plot.setOutlineVisible(false);

                    BarRenderer renderer = (BarRenderer) plot.getRenderer();
                    renderer.setBarPainter(new StandardBarPainter());
                    renderer.setShadowVisible(false);
                    renderer.setSeriesPaint(0, GOLD);
                    renderer.setSeriesOutlinePaint(0, GOLD_DARK);
                    renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0f));
                    renderer.setDrawBarOutline(true);
                    renderer.setMaximumBarWidth(0.08);
                    renderer.setItemMargin(0.06);

                    CategoryAxis domainAxis = plot.getDomainAxis();
                    domainAxis.setLabelFont(UIStyle.FONT_LABEL);
                    domainAxis.setLabelPaint(AXIS_TEXT);
                    domainAxis.setTickLabelFont(UIStyle.FONT_BODY);
                    domainAxis.setTickLabelPaint(AXIS_TEXT);
                    domainAxis.setAxisLinePaint(GRID_LINE);
                    domainAxis.setTickMarkPaint(GRID_LINE);

                    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                    rangeAxis.setLabelFont(UIStyle.FONT_LABEL);
                    rangeAxis.setLabelPaint(AXIS_TEXT);
                    rangeAxis.setTickLabelFont(UIStyle.FONT_BODY);
                    rangeAxis.setTickLabelPaint(AXIS_TEXT);
                    rangeAxis.setAxisLinePaint(GRID_LINE);
                    rangeAxis.setTickMarkPaint(GRID_LINE);
                    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                    ChartPanel panel = new ChartPanel(chart);
                    view.initGraph(panel, "Contenido más visto");
                },
                err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void crearOrigenContenidosGraph() {
        BackgroundWork.run(
                () -> EstadisticasDB.catalogoPorOrigen(),
                stats -> {
                    HashMap<String, Double> tipos = (HashMap<String, Double>) stats;
                    DefaultPieDataset dataset = new DefaultPieDataset();
                    for (Map.Entry<String, Double> entry : tipos.entrySet()) {
                        dataset.setValue(entry.getKey(), entry.getValue());
                    }

                    JFreeChart chart = ChartFactory.createPieChart(
                            "Contenidos por origen", dataset, true, true, false);
                    applyCommonChartStyle(chart);

                    PiePlot piePlot = (PiePlot) chart.getPlot();
                    piePlot.setBackgroundPaint(PLOT_BG);
                    piePlot.setOutlinePaint(GRID_LINE);
                    piePlot.setOutlineStroke(new BasicStroke(1.0f));
                    piePlot.setShadowPaint(null);
                    piePlot.setLabelBackgroundPaint(UIStyle.BG_SUBTLE);
                    piePlot.setLabelOutlinePaint(GRID_LINE);
                    piePlot.setLabelShadowPaint(null);
                    piePlot.setLabelFont(UIStyle.FONT_BODY);
                    piePlot.setLabelPaint(AXIS_TEXT);
                    piePlot.setLabelGap(0.04);

                    int colorIdx = 0;
                    for (Object key : dataset.getKeys()) {
                        piePlot.setSectionPaint((Comparable<?>) key, PIE_COLORS[colorIdx % PIE_COLORS.length]);
                        piePlot.setSectionOutlinePaint((Comparable<?>) key, PLOT_BG);
                        piePlot.setSectionOutlineStroke((Comparable<?>) key, new BasicStroke(2.0f));
                        colorIdx++;
                    }

                    ChartPanel panel = new ChartPanel(chart);
                    panel.setPopupMenu(null);
                    view.initGraph(panel, "Origen de contenidos");
                },
                err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void crearDistribucionPuntuacionesGraph() {
        BackgroundWork.run(
                () -> EstadisticasDB.distribucionPuntuaciones(),
                stats -> {
                    HashMap<String, Double> tramos = (HashMap<String, Double>) stats;
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (Map.Entry<String, Double> entry : tramos.entrySet()) {
                        dataset.addValue(entry.getValue(), "Reseñas", entry.getKey());
                    }

                    JFreeChart chart = ChartFactory.createBarChart(
                            "Distribución de puntuaciones", "Puntuación", "Reseñas",
                            dataset, PlotOrientation.VERTICAL, false, true, false);
                    applyCommonChartStyle(chart);

                    CategoryPlot plot = chart.getCategoryPlot();
                    plot.setBackgroundPaint(PLOT_BG);
                    plot.setOutlinePaint(GRID_LINE);
                    plot.setDomainGridlinePaint(GRID_LINE);
                    plot.setRangeGridlinePaint(GRID_LINE);
                    plot.setRangeGridlineStroke(new BasicStroke(0.7f));
                    plot.setDomainGridlinesVisible(false);
                    plot.setOutlineVisible(false);

                    BarRenderer renderer = (BarRenderer) plot.getRenderer();
                    renderer.setBarPainter(new StandardBarPainter());
                    renderer.setShadowVisible(false);
                    renderer.setSeriesPaint(0, GOLD);
                    renderer.setSeriesOutlinePaint(0, GOLD_DARK);
                    renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0f));
                    renderer.setDrawBarOutline(true);
                    renderer.setMaximumBarWidth(0.08);
                    renderer.setItemMargin(0.06);

                    CategoryAxis domainAxis = plot.getDomainAxis();
                    domainAxis.setLabelFont(UIStyle.FONT_LABEL);
                    domainAxis.setLabelPaint(AXIS_TEXT);
                    domainAxis.setTickLabelFont(UIStyle.FONT_BODY);
                    domainAxis.setTickLabelPaint(AXIS_TEXT);
                    domainAxis.setAxisLinePaint(GRID_LINE);
                    domainAxis.setTickMarkPaint(GRID_LINE);

                    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                    rangeAxis.setLabelFont(UIStyle.FONT_LABEL);
                    rangeAxis.setLabelPaint(AXIS_TEXT);
                    rangeAxis.setTickLabelFont(UIStyle.FONT_BODY);
                    rangeAxis.setTickLabelPaint(AXIS_TEXT);
                    rangeAxis.setAxisLinePaint(GRID_LINE);
                    rangeAxis.setTickMarkPaint(GRID_LINE);
                    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                    ChartPanel panel = new ChartPanel(chart);
                    view.initGraph(panel, "Distribución de puntuaciones");
                },
                err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }
}
