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
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class StatsUserController {

    private  StatsDialog view;
    private  Model model;
    private static final Color CHART_BG   = UIStyle.BG_PRIMARY;
    private static final Color PLOT_BG    = UIStyle.BG_CARD;
    private static final Color GRID_LINE  = UIStyle.BORDER;
    private static final Color AXIS_TEXT  = UIStyle.TEXT;
    private static final Color TITLE_TEXT = UIStyle.TITLE;
    private static final Color GOLD       = UIStyle.ACCENT;
    private static final Color GOLD_DARK  = UIStyle.ACCENT_DARK;
    private static final Color GOLD_ALPHA = new Color(GOLD.getRed(), GOLD.getGreen(), GOLD.getBlue(), 100);
    private static final Color[] PIE_COLORS = {GOLD,UIStyle.SUCCESS,new Color(0x60A5FA),new Color(0xA78BFA),new Color(0xF87171), };

    public StatsUserController(StatsDialog view, Model model) {
        this.view = view;
        this.model = model;
        createdMonthsGraphic();
        crearMediaAspectoGraph();
        crearTiposGuardadosGraph();
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
            () -> EstadisticasDB.visitasPorMesUsuario(model.getUser().getIdUsuario()),
            stats -> {
                HashMap<String, Double> meses = (HashMap<String, Double>) stats;
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Double> entry : meses.entrySet()) {
                    dataset.addValue(entry.getValue(), "Visitas", entry.getKey());
                }

                JFreeChart chart = ChartFactory.createBarChart(
                        "Visitas por mes", "Mes", "Visitas",
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
                view.initGraph(panel, "Visitas por mes");
            },
            err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void crearMediaAspectoGraph() {
        BackgroundWork.run(
            () -> EstadisticasDB.mediaPorAspectoUsuario(model.getUser().getIdUsuario()),
            stats -> {
                HashMap<String, Double> aspectos = (HashMap<String, Double>) stats;
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                for (Map.Entry<String, Double> entry : aspectos.entrySet()) {
                    dataset.addValue(entry.getValue(), "Críticas", entry.getKey());
                }

                SpiderWebPlot plot = new SpiderWebPlot(dataset);
                plot.setWebFilled(true);
                plot.setStartAngle(90.0);
                plot.setInteriorGap(0.30);
                plot.setAxisLabelGap(0.12);
                plot.setHeadPercent(0.018);

                plot.setBackgroundPaint(PLOT_BG);
                plot.setOutlinePaint(GRID_LINE);
                plot.setOutlineStroke(new BasicStroke(1.0f));

                plot.setAxisLinePaint(GRID_LINE);
                plot.setAxisLineStroke(new BasicStroke(0.8f));

                plot.setLabelFont(UIStyle.FONT_LABEL);
                plot.setLabelPaint(AXIS_TEXT);

                plot.setSeriesPaint(0, GOLD_ALPHA);
                plot.setSeriesOutlinePaint(0, GOLD);
                plot.setSeriesOutlineStroke(0, new BasicStroke(2.5f));

                JFreeChart chart = new JFreeChart(
                        "Media de críticas por aspecto",
                        UIStyle.FONT_SUBTITLE, plot, true);
                applyCommonChartStyle(chart);

                ChartPanel panel = new ChartPanel(chart);
                view.initGraph(panel, "Media por aspecto");
            },
            err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }

    private void crearTiposGuardadosGraph() {
        BackgroundWork.run(
            () -> EstadisticasDB.contenidoEnListasPorTipo(model.getUser().getIdUsuario()),
            stats -> {
                HashMap<String, Double> tipos = (HashMap<String, Double>) stats;
                DefaultPieDataset dataset = new DefaultPieDataset();
                for (Map.Entry<String, Double> entry : tipos.entrySet()) {
                    dataset.setValue(entry.getKey(), entry.getValue());
                }

                JFreeChart chart = ChartFactory.createPieChart(
                        "Contenidos guardados por tipo", dataset, true, true, false);
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
                view.initGraph(panel, "Contenidos guardados");
            },
            err -> JOptionPane.showMessageDialog(view, "Error al iniciar el gráfico", "Error", JOptionPane.ERROR_MESSAGE)
        );
    }
}
