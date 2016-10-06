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

    public int getQuantityFamSize1() {
        return QuantityFamSize1;
    }

    public int getQuantityFamSize2() {
        return QuantityFamSize2;
    }

    public int getQuantityFamSize3() {
        return QuantityFamSize3;
    }

    public int getQuantityFamSize4() {
        return QuantityFamSize4;
    }

    public int getQuantityFamSize5() {
        return QuantityFamSize5;
    }

    public int getQuantityFamSize6() {
        return QuantityFamSize6;
    }

    public int getQuantityFamSizeMoreThanSix() {
        return QuantityFamSizeMoreThanSix;
    }

    public int getCountStandardActive() {
        return countStandardActive;
    }

    public int getCountKiddiesActive() {
        return countKiddiesActive;
    }

    public int getCountLowCarbActive() {
        return countLowCarbActive;
    }

    public int getActiveClientCount() {
        return activeClientCount;
    }

    public int getCountFamilySize_1() {
        return countFamilySize_1;
    }

    public int getCountFamilySize_2() {
        return countFamilySize_2;
    }

    public int getCountFamilySize_3() {
        return countFamilySize_3;
    }

    public int getCountFamilySize_4() {
        return countFamilySize_4;
    }

    public int getCountFamilySize_5() {
        return countFamilySize_5;
    }

    public int getCountFamilySize_6() {
        return countFamilySize_6;
    }

    public int getCountFamilySizeMoreThanSix() {
        return countFamilySizeMoreThanSix;
    }

    public int getCountFamSize1_Standard() {
        return countFamSize1_Standard;
    }

    public int getCountFamSize2_Standard() {
        return countFamSize2_Standard;
    }

    public int getCountFamSize3_Standard() {
        return countFamSize3_Standard;
    }

    public int getCountFamSize4_Standard() {
        return countFamSize4_Standard;
    }

    public int getCountFamSize5_Standard() {
        return countFamSize5_Standard;
    }

    public int getCountFamSize6_Standard() {
        return countFamSize6_Standard;
    }

    public int getCountFamilySizeMoreThanSix_Standard() {
        return countFamilySizeMoreThanSix_Standard;
    }

    public int getCountFamSize1_LC() {
        return countFamSize1_LC;
    }

    public int getCountFamSize2_LC() {
        return countFamSize2_LC;
    }

    public int getCountFamSize3_LC() {
        return countFamSize3_LC;
    }

    public int getCountFamSize4_LC() {
        return countFamSize4_LC;
    }

    public int getCountFamSize5_LC() {
        return countFamSize5_LC;
    }

    public int getCountFamSize6_LC() {
        return countFamSize6_LC;
    }

    public int getCountFamilySizeMoreThanSix_LC() {
        return countFamilySizeMoreThanSix_LC;
    }

    public int getCountFamSize1_KD() {
        return countFamSize1_KD;
    }

    public int getCountFamSize2_KD() {
        return countFamSize2_KD;
    }

    public int getCountFamSize3_KD() {
        return countFamSize3_KD;
    }

    public int getCountFamSize4_KD() {
        return countFamSize4_KD;
    }

    public int getCountFamSize5_KD() {
        return countFamSize5_KD;
    }

    public int getCountFamSize6_KD() {
        return countFamSize6_KD;
    }

    public int getCountFamilySizeMoreThanSix_KD() {
        return countFamilySizeMoreThanSix_KD;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// increment methods
    public void incrementCountStandardActive(int quantity) {
        this.countStandardActive += quantity;
    }

    public void incrementCountKiddiesActive(int quantity) {
        this.countKiddiesActive += quantity;
    }

    public void incrementCountLowCarbActive(int quantity) {
        this.countLowCarbActive += quantity;
    }

    public void incrementActiveClientCount() {
        this.activeClientCount++;
    }

    public void incrementCountFamilySize_1() {
        this.countFamilySize_1++;
    }

    public void incrementCountFamilySize_2() {
        this.countFamilySize_2++;
    }

    public void incrementCountFamilySize_3() {
        this.countFamilySize_3++;
    }

    public void incrementCountFamilySize_4() {
        this.countFamilySize_4++;
    }

    public void incrementCountFamilySize_5() {
        this.countFamilySize_5++;
    }

    public void incrementCountFamilySize_6() {
        this.countFamilySize_6++;
    }

    public void incrementCountFamilySizeMoreThanSix() {
        this.countFamilySizeMoreThanSix++;
    }

    public void incrementCountFamSize1_Standard() {
        this.countFamSize1_Standard++;
    }

    public void incrementCountFamSize2_Standard() {
        this.countFamSize2_Standard++;
    }

    public void incrementCountFamSize3_Standard() {
        this.countFamSize3_Standard++;
    }

    public void incrementCountFamSize4_Standard() {
        this.countFamSize4_Standard++;
    }

    public void incrementCountFamSize5_Standard() {
        this.countFamSize5_Standard++;
    }

    public void incrementCountFamSize6_Standard() {
        this.countFamSize6_Standard++;
    }

    public void incrementCountFamilySizeMoreThanSix_Standard() {
        this.countFamilySizeMoreThanSix_Standard++;
    }

    public void incrementCountFamSize1_LC() {
        this.countFamSize1_LC++;
    }

    public void incrementCountFamSize2_LC() {
        this.countFamSize2_LC++;
    }

    public void incrementCountFamSize3_LC() {
        this.countFamSize3_LC++;
    }

    public void incrementCountFamSize4_LC() {
        this.countFamSize4_LC++;
    }

    public void incrementCountFamSize5_LC() {
        this.countFamSize5_LC++;
    }

    public void incrementCountFamSize6_LC() {
        this.countFamSize6_LC++;
    }

    public void incrementCountFamilySizeMoreThanSix_LC() {
        this.countFamilySizeMoreThanSix_LC++;
    }

    public void incrementCountFamSize1_KD() {
        this.countFamSize1_KD++;
    }

    public void incrementCountFamSize2_KD() {
        this.countFamSize2_KD++;
    }

    public void incrementCountFamSize3_KD() {
        this.countFamSize3_KD++;
    }

    public void incrementCountFamSize4_KD() {
        this.countFamSize4_KD++;
    }

    public void incrementCountFamSize5_KD() {
        this.countFamSize5_KD++;
    }

    public void incrementCountFamSize6_KD() {
        this.countFamSize6_KD++;
    }

    public void incrementCountFamilySizeMoreThanSix_KD() {
        this.countFamilySizeMoreThanSix_KD++;
    }

    /// Quantity Increment
    public void incrementQuantityFamSize1() {
        this.QuantityFamSize1++;
    }

    public void incrementQuantityFamSize2() {
        this.QuantityFamSize2++;
    }

    public void incrementQuantityFamSize3() {
        this.QuantityFamSize3++;
    }

    public void incrementQuantityFamSize4() {
        this.QuantityFamSize4++;
    }

    public void incrementQuantityFamSize5() {
        this.QuantityFamSize5++;
    }

    public void incrementQuantityFamSize6() {
        this.QuantityFamSize6++;
    }

    public void incrementQuantityFamSizeMoreThanSix() {
        this.QuantityFamSizeMoreThanSix++;
    }

}
