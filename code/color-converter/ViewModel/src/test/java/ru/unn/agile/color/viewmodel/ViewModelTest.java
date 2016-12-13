package ru.unn.agile.color.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.unn.agile.color.model.ColorSpaces.*;
import static ru.unn.agile.color.viewmodel.ViewModel.Status.*;

public class ViewModelTest {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }
    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getFirstValueProperty().getValue());
        assertEquals("", viewModel.getSecondValueProperty().getValue());
        assertEquals("", viewModel.getThirdValueProperty().getValue());
        assertEquals(LAB, viewModel.getFromColorSpace().get());
        assertEquals(HSV, viewModel.getToColorSpace().get());
        assertEquals("", viewModel.getFirstValueResult());
        assertEquals("", viewModel.getSecondValueResult());
        assertEquals("", viewModel.getThirdValueResult());
        assertEquals(WAITING.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canMessageWaitingFormatWithEmptyColorSpaceValues() {
        viewModel.convert();
        assertEquals(WAITING.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canMessageReadyFormatWithFilledValues() {
        setInputData();
        assertEquals(READY.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canMessageSuccessFormatAfterConvert() {
        setInputData();
        viewModel.convert();
        assertEquals(SUCCESS.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canMessageBadFormat() {
        setInputData();
        viewModel.firstValueProperty().set("one");
        assertEquals(BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canMessageBadFormatFormatForNegativeValue() {
        setInputData();
        viewModel.firstValueProperty().set("-78");
        assertEquals(BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }
    @Test
    public void canSetRGBFromColorSpace() {
        viewModel.getFromColorSpace().set(RGB);
        assertEquals(RGB, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetRGBToColorSpace() {
        viewModel.getToColorSpace().set(RGB);
        assertEquals(RGB, viewModel.getToColorSpace().get());
    }
    @Test
    public void canSetHSVFromColorSpace() {
        viewModel.getFromColorSpace().set(HSV);
        assertEquals(HSV, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetHSVToColorSpace() {
        viewModel.getToColorSpace().set(HSV);
        assertEquals(HSV, viewModel.getToColorSpace().get());
    }
    @Test
    public void canSetLABFromColorSpace() {
        viewModel.getFromColorSpace().set(LAB);
        assertEquals(LAB, viewModel.getFromColorSpace().get());
    }
    @Test
    public void canSetLABToColorSpace() {
        viewModel.getToColorSpace().set(LAB);
        assertEquals(LAB, viewModel.getToColorSpace().get());
    }
    @Test
    public void checkDisabledButtonForEmptyValues() {
        assertTrue(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForCorrectValues() {
        setInputData();
        assertFalse(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForIncorrectValue() {
        setInputData();
        viewModel.firstValueProperty().set("Two");
        assertTrue(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonForNegativeValue() {
        viewModel.secondValueProperty().set("-98");
        assertTrue(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void checkDisabledButtonAfterConvert() {
        setInputData();
        viewModel.convert();
        assertFalse(viewModel.convertingDisabledProperty().get());
    }
    @Test
    public void convertFromLABToHSV() {
        viewModel.firstValueProperty().set("23");
        viewModel.secondValueProperty().set("24");
        viewModel.thirdValueProperty().set("25");
        viewModel.getFromColorSpace().set(LAB);
        viewModel.getToColorSpace().set(HSV);
        viewModel.convert();
        assertEquals("16.0", viewModel.firstValueResultProperty().get());
        assertEquals("80.851", viewModel.secondValueResultProperty().get());
        assertEquals("36.863", viewModel.thirdValueResultProperty().get());
    }
    @Test
    public void checkTheFirstColorSpaceInBox() {
        assertEquals(RGB, viewModel.getColorSpaces().get(0));
    }
    @Test
    public void checkTheSecondColorSpaceInBox() {
        assertEquals(LAB, viewModel.getColorSpaces().get(1));
    }
    @Test
    public void checkTheThirdColorSpaceInBox() {
        assertEquals(HSV, viewModel.getColorSpaces().get(2));
    }
    @Test
    public void checkStringResultingValue() {
        setInputData();
        viewModel.convert();
        assertEquals("210.0", viewModel.getFirstValueResult());
        assertEquals("8.0", viewModel.getSecondValueResult());
        assertEquals("9.804", viewModel.getThirdValueResult());
    }
    @Test
    public void checkStringMessage() {
        setInputData();
        viewModel.convert();
        assertEquals(SUCCESS.toString(), viewModel.getStatus());
    }
    @Test
    public void isButtonDisabledWithEmptyData() {
        assertTrue(viewModel.isConvertingDisabled());
    }

    public void setInputData() {
        viewModel.firstValueProperty().set("23");
        viewModel.secondValueProperty().set("24");
        viewModel.thirdValueProperty().set("25");
        viewModel.getFromColorSpace().set(RGB);
        viewModel.getToColorSpace().set(HSV);
    }
}
