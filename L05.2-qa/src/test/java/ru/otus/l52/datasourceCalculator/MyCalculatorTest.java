package ru.otus.l52.datasourceCalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author sergey
 * created on 07.08.18.
 */
public class MyCalculatorTest {

    @Test
    public void accumulator() {
        DataProvider dataProvider = mock(DataProvider.class);
        when(dataProvider.getDataInteger()).thenReturn(1);


        int result = new MyCalculator(dataProvider).accumulator(5);

        assertEquals("accumulator result:", 5, result);
        verify(dataProvider, times(5)).getDataInteger();
    }


    @Test
    public void complexCalc() {
        DataProvider dataProvider = new DataProviderImpl();
        DataProvider dataProviderSpy = spy(dataProvider);
        doReturn(1.0).when(dataProviderSpy).getDataDouble(anyInt());
        double result = new MyCalculator(dataProviderSpy).complexCalc(5);


        assertEquals("complex calc result:", 5, result, 0.1);

        verify(dataProviderSpy, times(1)).getDataDouble(1);
        verify(dataProviderSpy, times(1)).getDataDouble(2);
    }
}