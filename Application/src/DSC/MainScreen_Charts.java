package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Aliens_Keanu
 */
public class MainScreen_Charts extends JPanel {

    /*
    *Static counters for Specific Items.
     */
    private static int countStandardActive;
    private static int countKiddiesActive;
    private static int countLowCarbActive;

    private static int countStandardInActive;
    private static int countKiddiesInActive;
    private static int countLowCarbInActive;

    private static int countFamilySize_1;
    private static int countFamilySize_2;
    private static int countFamilySize_3;
    private static int countFamilySize_4;
    private static int countFamilySize_5;
    private static int countFamilySize_6;
    private static int countFamilySizeMoreThanSix;
    private static final String STANDARD = "Standard";
    private static final String LOW_CARB = "Low Carb";
    private static final String KIDDIES = "Kiddies";

    public static void createGraphs(JPanel pnlBarChartActive,
            JPanel pnlBarChartInActive, JPanel pnlPieChart, JLabel lblStandardTotal, JLabel lblLowCarbTotal, JLabel lblKiddiesTotal,
            JLabel lblKiddiesTotalInActive, JLabel lblStandardTotalInActive,
            JLabel lblLowCarbTotalInActive, JLabel lblSingleFamilySizeTotal, JLabel lblCoupleFamilySizeTotal, JLabel lblThreeFamilySizeTotal, JLabel lblFourFamilySizeTotal, JLabel lblFiveFamilySizeTotal,
            JLabel lblSixFamilySizeTotal, JLabel lblMoreThanSixFamilySizeTotal, boolean getData) {

        countStandardActive = 0;
        countKiddiesActive = 0;
        countLowCarbActive = 0;

        countStandardInActive = 0;
        countKiddiesInActive = 0;
        countLowCarbInActive = 0;

        countFamilySize_1 = 0;
        countFamilySize_2 = 0;
        countFamilySize_3 = 0;
        countFamilySize_4 = 0;
        countFamilySize_5 = 0;
        countFamilySize_6 = 0;
        countFamilySizeMoreThanSix = 0;
        
        final String STANDARD = "Standard";
        final String LOW_CARB = "Low Carb";
        final String KIDDIES = "Kiddies";

        Firebase tableRef = DBClass.getInstance().child("Orders");// Go to specific Table

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (getData) {
                    for (DataSnapshot Data : ds.getChildren()) {//entire database

                        boolean activeCheck = Data.child("Active").getValue(boolean.class);
                        long familySizeCheck;

                        for (DataSnapshot Data2 : Data.getChildren()) {

                            familySizeCheck = Data.child("FamilySize").getValue(int.class);// gets family size from database

                            if (familySizeCheck == 1) {
                                countFamilySize_1++;
                            }
                            if (familySizeCheck == 2) {
                                countFamilySize_2++;
                            }
                            if (familySizeCheck == 3) {
                                countFamilySize_3++;
                            }
                            if (familySizeCheck == 4) {
                                countFamilySize_4++;
                            }
                            if (familySizeCheck == 5) {
                                countFamilySize_5++;
                            }
                            if (familySizeCheck == 6) {
                                countFamilySize_6++;
                            }

                            for (DataSnapshot Data3 : Data2.getChildren()) {

                                if (activeCheck == true && Data3.child("MealType").getValue(String.class).equals(STANDARD)) {
                                    countStandardActive++;
                                }
                                if (activeCheck == true && Data3.child("MealType").getValue(String.class).equals(LOW_CARB)) {
                                    countLowCarbActive++;
                                }
                                if (activeCheck == true && Data3.child("MealType").getValue(String.class).equals(KIDDIES)) {
                                    countKiddiesActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue(String.class).equals(STANDARD)) {
                                    countStandardInActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue(String.class).equals(LOW_CARB)) {
                                    countLowCarbInActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue(String.class).equals(KIDDIES)) {
                                    countKiddiesInActive++;
                                }

                                if (familySizeCheck > 6) {
                                    countFamilySizeMoreThanSix++;
                                }

                            }

                        }

                    }
                }

                /*
                *Creates BarGraph For all Active Meals.
                 */
                DefaultCategoryDataset dataset_Chart1 = new DefaultCategoryDataset();
                dataset_Chart1.addValue(countLowCarbActive, "", " Low Carb ( " + countLowCarbActive + " )");
                dataset_Chart1.addValue(countStandardActive, "", " Standard ( " + countStandardActive + " )");
                dataset_Chart1.addValue(countKiddiesActive, "", " Kiddies ( " + countKiddiesActive + " )");

                JFreeChart barChart = ChartFactory.createBarChart(
                        "Total Active Meals",
                        "Meal Types",
                        "Number Of Meals",
                        dataset_Chart1,
                        PlotOrientation.VERTICAL,
                        false, true, false);

                ChartPanel chartPanel_1 = new ChartPanel(barChart);
                chartPanel_1.setBackground(Color.DARK_GRAY);
                chartPanel_1.setBounds(0, 0, pnlBarChartActive.getWidth(), pnlBarChartActive.getHeight());
                pnlBarChartActive.add(chartPanel_1, BorderLayout.CENTER);
                chartPanel_1.setSize(new Dimension(pnlBarChartActive.getWidth(), pnlBarChartActive.getHeight()));
                chartPanel_1.repaint();

                /*
                *Creates BarGraph For all In-Active Meals.
                 */
                DefaultCategoryDataset dataset_Chart2 = new DefaultCategoryDataset();
                dataset_Chart2.addValue(countLowCarbInActive, "", " Low Carb( " + countLowCarbInActive + " )");
                dataset_Chart2.addValue(countStandardInActive, "", " Standard( " + countStandardInActive + " )");
                dataset_Chart2.addValue(countKiddiesInActive, "", " Kiddies( " + countKiddiesInActive + " )");

                JFreeChart barChart_2 = ChartFactory.createBarChart(
                        "Total In-Active Meals",
                        "Meal Types",
                        "Number Of Meals",
                        dataset_Chart2,
                        PlotOrientation.VERTICAL,
                        false, true, false);

                ChartPanel chartPanelCompare = new ChartPanel(barChart_2);
                chartPanelCompare.setBackground(Color.DARK_GRAY);
                chartPanelCompare.setBounds(0, 0, pnlBarChartInActive.getWidth(), pnlBarChartInActive.getHeight());
                pnlBarChartInActive.add(chartPanelCompare, BorderLayout.CENTER);
                chartPanelCompare.setSize(new Dimension(pnlBarChartInActive.getWidth(), pnlBarChartInActive.getHeight()));
                chartPanelCompare.repaint();

                /*
                *Creates PieChart For Family Size chosen per Order.
                 */
                DefaultPieDataset datasetPieChart = new DefaultPieDataset();
                datasetPieChart.setValue("FamilySize_1", countFamilySize_1);
                datasetPieChart.setValue("FamilySize_2", countFamilySize_2);
                datasetPieChart.setValue("FamilySize_3", countFamilySize_3);
                datasetPieChart.setValue("FamilySize_4", countFamilySize_4);
                datasetPieChart.setValue("FamilySize_5", countFamilySize_5);
                datasetPieChart.setValue("FamilySize_6", countFamilySize_6);
                datasetPieChart.setValue("FamilySizeMoreThan6", countFamilySizeMoreThanSix);

                JFreeChart pieChart = ChartFactory.createPieChart("Ordered Family Sizes", datasetPieChart, true, false, false);

                ChartPanel chartPanel_FamilySize = new ChartPanel(pieChart);
                chartPanel_FamilySize.setBackground(Color.DARK_GRAY);
                chartPanel_FamilySize.setBounds(0, 0, pnlPieChart.getWidth(), pnlPieChart.getHeight());
                pnlPieChart.add(chartPanel_FamilySize, BorderLayout.CENTER);
                chartPanel_FamilySize.setSize(new Dimension(pnlPieChart.getWidth(), pnlPieChart.getHeight()));
                chartPanel_FamilySize.repaint();

                /*
                *Insert values to Text Statistics.
                 */
                lblStandardTotal.setText(countStandardActive + "");
                lblLowCarbTotal.setText(countLowCarbActive + "");
                lblKiddiesTotal.setText(countKiddiesActive + "");

                lblKiddiesTotalInActive.setText(countKiddiesInActive + "");
                lblStandardTotalInActive.setText(countStandardInActive + "");
                lblLowCarbTotalInActive.setText(countLowCarbInActive + "");

                lblSingleFamilySizeTotal.setText(countFamilySize_1 + "");
                lblCoupleFamilySizeTotal.setText(countFamilySize_2 + "");
                lblThreeFamilySizeTotal.setText(countFamilySize_3 + "");
                lblFourFamilySizeTotal.setText(countFamilySize_4 + "");
                lblFiveFamilySizeTotal.setText(countFamilySize_5 + "");
                lblSixFamilySizeTotal.setText(countFamilySize_6 + "");
                lblMoreThanSixFamilySizeTotal.setText(countFamilySizeMoreThanSix + "");

                pnlBarChartActive.setBackground(Color.DARK_GRAY);
                pnlBarChartInActive.setBackground(Color.DARK_GRAY);
                pnlPieChart.setBackground(Color.DARK_GRAY);

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });

    }

}
