/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

/**
 *
 * @author Aliens_Keanu
 */
public class QuantityReportData {

    private int countStandardActive;
    private int countKiddiesActive;
    private int countLowCarbActive;
    // total active cliens/orders
    private int activeClientCount;
    // total of each family size
    private int countFamilySize_1;
    private int countFamilySize_2;
    private int countFamilySize_3;
    private int countFamilySize_4;
    private int countFamilySize_5;
    private int countFamilySize_6;
    private int countFamilySizeMoreThanSix;
    // count family size per standard meal type
    private int countFamSize1_Standard;
    private int countFamSize2_Standard;
    private int countFamSize3_Standard;
    private int countFamSize4_Standard;
    private int countFamSize5_Standard;
    private int countFamSize6_Standard;
    private int countFamilySizeMoreThanSix_Standard;
    // count family size per low carb meal type
    private int countFamSize1_LC;
    private int countFamSize2_LC;
    private int countFamSize3_LC;
    private int countFamSize4_LC;
    private int countFamSize5_LC;
    private int countFamSize6_LC;
    private int countFamilySizeMoreThanSix_LC;
    // count family size per kiddies meal type
    private int countFamSize1_KD;
    private int countFamSize2_KD;
    private int countFamSize3_KD;
    private int countFamSize4_KD;
    private int countFamSize5_KD;
    private int countFamSize6_KD;
    private int countFamilySizeMoreThanSix_KD;
    // quantity for each family size
    private int QuantityFamSize1;
    private int QuantityFamSize2;
    private int QuantityFamSize3;
    private int QuantityFamSize4;
    private int QuantityFamSize5;
    private int QuantityFamSize6;
    private int QuantityFamSizeMoreThanSix;

    protected int getQuantityFamSize1() {
        return QuantityFamSize1;
    }

    protected int getQuantityFamSize2() {
        return QuantityFamSize2;
    }

    protected int getQuantityFamSize3() {
        return QuantityFamSize3;
    }

    protected int getQuantityFamSize4() {
        return QuantityFamSize4;
    }

    protected int getQuantityFamSize5() {
        return QuantityFamSize5;
    }

    protected int getQuantityFamSize6() {
        return QuantityFamSize6;
    }

    protected int getQuantityFamSizeMoreThanSix() {
        return QuantityFamSizeMoreThanSix;
    }

    protected int getCountStandardActive() {
        return countStandardActive;
    }

    protected int getCountKiddiesActive() {
        return countKiddiesActive;
    }

    protected int getCountLowCarbActive() {
        return countLowCarbActive;
    }

    protected int getActiveClientCount() {
        return activeClientCount;
    }

    protected int getCountFamilySize_1() {
        return countFamilySize_1;
    }

    protected int getCountFamilySize_2() {
        return countFamilySize_2;
    }

    protected int getCountFamilySize_3() {
        return countFamilySize_3;
    }

    protected int getCountFamilySize_4() {
        return countFamilySize_4;
    }

    protected int getCountFamilySize_5() {
        return countFamilySize_5;
    }

    protected int getCountFamilySize_6() {
        return countFamilySize_6;
    }

    protected int getCountFamilySizeMoreThanSix() {
        return countFamilySizeMoreThanSix;
    }

    protected int getCountFamSize1_Standard() {
        return countFamSize1_Standard;
    }

    protected int getCountFamSize2_Standard() {
        return countFamSize2_Standard;
    }

    protected int getCountFamSize3_Standard() {
        return countFamSize3_Standard;
    }

    protected int getCountFamSize4_Standard() {
        return countFamSize4_Standard;
    }

    protected int getCountFamSize5_Standard() {
        return countFamSize5_Standard;
    }

    protected int getCountFamSize6_Standard() {
        return countFamSize6_Standard;
    }

    protected int getCountFamilySizeMoreThanSix_Standard() {
        return countFamilySizeMoreThanSix_Standard;
    }

    protected int getCountFamSize1_LC() {
        return countFamSize1_LC;
    }

    protected int getCountFamSize2_LC() {
        return countFamSize2_LC;
    }

    protected int getCountFamSize3_LC() {
        return countFamSize3_LC;
    }

    protected int getCountFamSize4_LC() {
        return countFamSize4_LC;
    }

    protected int getCountFamSize5_LC() {
        return countFamSize5_LC;
    }

    protected int getCountFamSize6_LC() {
        return countFamSize6_LC;
    }

    protected int getCountFamilySizeMoreThanSix_LC() {
        return countFamilySizeMoreThanSix_LC;
    }

    protected int getCountFamSize1_KD() {
        return countFamSize1_KD;
    }

    protected int getCountFamSize2_KD() {
        return countFamSize2_KD;
    }

    protected int getCountFamSize3_KD() {
        return countFamSize3_KD;
    }

    protected int getCountFamSize4_KD() {
        return countFamSize4_KD;
    }

    protected int getCountFamSize5_KD() {
        return countFamSize5_KD;
    }

    protected int getCountFamSize6_KD() {
        return countFamSize6_KD;
    }

    protected int getCountFamilySizeMoreThanSix_KD() {
        return countFamilySizeMoreThanSix_KD;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// increment methods
    protected void incrementCountStandardActive(int quantity) {
        this.countStandardActive += quantity;
    }

    protected void incrementCountKiddiesActive(int quantity) {
        this.countKiddiesActive += quantity;
    }

    protected void incrementCountLowCarbActive(int quantity) {
        this.countLowCarbActive += quantity;
    }

    protected void incrementActiveClientCount() {
        this.activeClientCount++;
    }

    protected void incrementCountFamilySize_1() {
        this.countFamilySize_1++;
    }

    protected void incrementCountFamilySize_2() {
        this.countFamilySize_2++;
    }

    protected void incrementCountFamilySize_3() {
        this.countFamilySize_3++;
    }

    protected void incrementCountFamilySize_4() {
        this.countFamilySize_4++;
    }

    protected void incrementCountFamilySize_5() {
        this.countFamilySize_5++;
    }

    protected void incrementCountFamilySize_6() {
        this.countFamilySize_6++;
    }

    protected void incrementCountFamilySizeMoreThanSix() {
        this.countFamilySizeMoreThanSix++;
    }

    protected void incrementCountFamSize1_Standard() {
        this.countFamSize1_Standard++;
    }

    protected void incrementCountFamSize2_Standard() {
        this.countFamSize2_Standard++;
    }

    protected void incrementCountFamSize3_Standard() {
        this.countFamSize3_Standard++;
    }

    protected void incrementCountFamSize4_Standard() {
        this.countFamSize4_Standard++;
    }

    protected void incrementCountFamSize5_Standard() {
        this.countFamSize5_Standard++;
    }

    protected void incrementCountFamSize6_Standard() {
        this.countFamSize6_Standard++;
    }

    protected void incrementCountFamilySizeMoreThanSix_Standard() {
        this.countFamilySizeMoreThanSix_Standard++;
    }

    protected void incrementCountFamSize1_LC() {
        this.countFamSize1_LC++;
    }

    protected void incrementCountFamSize2_LC() {
        this.countFamSize2_LC++;
    }

    protected void incrementCountFamSize3_LC() {
        this.countFamSize3_LC++;
    }

    protected void incrementCountFamSize4_LC() {
        this.countFamSize4_LC++;
    }

    protected void incrementCountFamSize5_LC() {
        this.countFamSize5_LC++;
    }

    protected void incrementCountFamSize6_LC() {
        this.countFamSize6_LC++;
    }

    protected void incrementCountFamilySizeMoreThanSix_LC() {
        this.countFamilySizeMoreThanSix_LC++;
    }

    protected void incrementCountFamSize1_KD() {
        this.countFamSize1_KD++;
    }

    protected void incrementCountFamSize2_KD() {
        this.countFamSize2_KD++;
    }

    protected void incrementCountFamSize3_KD() {
        this.countFamSize3_KD++;
    }

    protected void incrementCountFamSize4_KD() {
        this.countFamSize4_KD++;
    }

    protected void incrementCountFamSize5_KD() {
        this.countFamSize5_KD++;
    }

    protected void incrementCountFamSize6_KD() {
        this.countFamSize6_KD++;
    }

    protected void incrementCountFamilySizeMoreThanSix_KD() {
        this.countFamilySizeMoreThanSix_KD++;
    }

    /// Quantity Increment
    protected void incrementQuantityFamSize1() {
        this.QuantityFamSize1++;
    }

    protected void incrementQuantityFamSize2() {
        this.QuantityFamSize2++;
    }

    protected void incrementQuantityFamSize3() {
        this.QuantityFamSize3++;
    }

    protected void incrementQuantityFamSize4() {
        this.QuantityFamSize4++;
    }

    protected void incrementQuantityFamSize5() {
        this.QuantityFamSize5++;
    }

    protected void incrementQuantityFamSize6() {
        this.QuantityFamSize6++;
    }

    protected void incrementQuantityFamSizeMoreThanSix() {
        this.QuantityFamSizeMoreThanSix++;
    }

    // add totals
    protected int returnTotalClients() {

        int totalClients = getCountFamilySize_1() + getCountFamilySize_2() + getCountFamilySize_3() + getCountFamilySize_4()
                + getCountFamilySize_5() + getCountFamilySize_6() + getCountFamilySizeMoreThanSix();

        return totalClients;
    }

    protected int returnTotalIndividuals() {

        int totalIndividuals = getQuantityFamSize1() + getQuantityFamSize2() + getQuantityFamSize3()
                + getQuantityFamSize4() + getQuantityFamSize5() + getQuantityFamSize6() + getQuantityFamSizeMoreThanSix();

        return totalIndividuals;
    }

    protected int returnTotalStandardMeals() {

        int totalStandardMeals = getCountFamSize1_Standard() + getCountFamSize2_Standard() + getCountFamSize3_Standard() + getCountFamSize4_Standard()
                + getCountFamSize5_Standard() + getCountFamSize6_Standard() + getCountFamilySizeMoreThanSix_Standard();

        return totalStandardMeals;
    }

    protected int returnTotalLowCarbMeals() {

        int totalLowCarbMeals = getCountFamSize1_LC() + getCountFamSize2_LC() + getCountFamSize3_LC() + getCountFamSize4_LC()
                + getCountFamSize5_LC() + getCountFamSize6_LC() + getCountFamilySizeMoreThanSix_LC();

        return totalLowCarbMeals;
    }

    protected int returnTotalKiddiesMeals() {

        int totalKiddiesMeals = getCountFamSize1_KD() + getCountFamSize2_KD() + getCountFamSize3_KD() + getCountFamSize4_KD()
                + getCountFamSize5_KD() + getCountFamSize6_KD() + getCountFamilySizeMoreThanSix_KD();

        return totalKiddiesMeals;
    }

    protected int totalSingleMeals() {
        int totalSingle = getCountFamSize1_Standard() + getCountFamSize1_LC() + getCountFamSize1_KD();
        return totalSingle;
    }

    protected int totalCoupleMeals() {
        int totalCouple = getCountFamSize2_Standard() + getCountFamSize2_LC() + getCountFamSize2_KD();
        return totalCouple;
    }

    protected int totalThreeMeals() {
        int totalThree = getCountFamSize3_Standard() + getCountFamSize3_LC() + getCountFamSize3_KD();
        return totalThree;
    }

    protected int totalFourMeals() {
        int totalFour = getCountFamSize4_Standard() + getCountFamSize4_LC() + getCountFamSize4_KD();
        return totalFour;
    }

    protected int totalFiveMeals() {
        int totalFive = getCountFamSize5_Standard() + getCountFamSize5_LC() + getCountFamSize5_KD();
        return totalFive;
    }

    protected int totalSixMeals() {
        int totalSix = getCountFamSize6_Standard() + getCountFamSize6_LC() + getCountFamSize6_KD();
        return totalSix;
    }

    protected int totalExtraMeals() {
        int totalExtra = getCountFamilySizeMoreThanSix_Standard() + getCountFamilySizeMoreThanSix_LC() + getCountFamilySizeMoreThanSix_KD();
        return totalExtra;
    }

}
