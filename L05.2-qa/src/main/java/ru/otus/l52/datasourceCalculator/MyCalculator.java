package ru.otus.l52.datasourceCalculator;

/**
 * @author sergey
 * created on 06.08.18.
 */
public class MyCalculator {

    private final DataProvider dataProvider;

    public MyCalculator(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public int accumulator(int ticksCounter) {
        int result = 0;
        for(int i = 0; i < ticksCounter; i++) {
            result += dataProvider.getDataInteger();
        }
        return result;
    }

    public double complexCalc(int ticksCounter) {
        double result = 0;
        for(int i = 0; i < ticksCounter; i++) {
            result += dataProvider.getDataDouble(dataProvider.getDataInteger());
        }
        return result;
    }
}
