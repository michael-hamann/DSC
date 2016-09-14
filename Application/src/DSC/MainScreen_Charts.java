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
    private static int countStandardActive = 0;
    private static int countKiddiesActive = 0;
    private static int countLowCarbActive = 0;

    private static int countStandardInActive = 0;
    private static int countKiddiesInActive = 0;
    private static int countLowCarbInActive = 0;

    private static int countFamilySize_1 = 0;
    private static int countFamilySize_2 = 0;
    private static int countFamilySize_3 = 0;
    private static int countFamilySize_4 = 0;
    private static int countFamilySize_5 = 0;
    private static int countFamilySize_6 = 0;
    private static int countFamilySizeMoreThanSix = 0;
    static final String STANDARD = "Standard";
    static final String LOW_CARB = "Low Carb";
    static final String KIDDIES = "Kiddies";
    
    public static void createBarGraph_ActiveAndInActiveMeals(JPanel pnlBarChartActive,
            JPanel pnlBarChartInActive, JPanel pnlPieChart, JLabel lblStandardTotal, JLabel lblLowCarbTotal, JLabel lblKiddiesTotal,
            JLabel lblKiddiesTotalInActive, JLabel lblStandardTotalInActive,
            JLabel lblLowCarbTotalInActive, JLabel lblSingleFamilySizeTotal, JLabel lblCoupleFamilySizeTotal, JLabel lblThreeFamilySizeTotal, JLabel lblFourFamilySizeTotal, JLabel lblFiveFamilySizeTotal,
            JLabel lblSixFamilySizeTotal, JLabel lblMoreThanSixFamilySizeTotal, boolean getData) {

        Firebase tableRef = DBClass.getInstance().child("Orders");// Go to specific Table

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (getData) {
                    for (DataSnapshot Data : ds.getChildren()) {//entire database

                        boolean activeCheck = (boolean) Data.child("Active").getValue();
                        long familySizeCheck;

                        for (DataSnapshot Data2 : Data.getChildren()) {

                            familySizeCheck = (long) Data.child("FamilySize").getValue();// gets family size from database

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

                                if (activeCheck == true && Data3.child("MealType").getValue().equals(STANDARD)) {
                                    countStandardActive++;
                                }
                                if (activeCheck == true && Data3.child("MealType").getValue().equals(LOW_CARB)) {
                                    countLowCarbActive++;
                                }
                                if (activeCheck == true && Data3.child("MealType").getValue().equals(KIDDIES)) {
                                    countKiddiesActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue().equals(STANDARD)) {
                                    countStandardInActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue().equals(LOW_CARB)) {
                                    countLowCarbInActive++;
                                }
                                if (activeCheck == false && Data3.child("MealType").getValue().equals(KIDDIES)) {
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
                dataset_Chart2.addValue(countLowCarbInActive, "", "Low Carb( " + countLowCarbInActive + " )");
                dataset_Chart2.addValue(countStandardInActive, "", "Standard( " + countStandardInActive + " )");
                dataset_Chart2.addValue(countKiddiesInActive, "", "Kiddies( " + countKiddiesInActive + " )");

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

                JFreeChart pieChart = ChartFactory.createPieChart("PieChart", datasetPieChart, true, false, false);

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
