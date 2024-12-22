package test;

import main.Converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for the Converter class
 *
 * Test 1: sanitise method
 *  - Truncating and Extending decimal places
 *  - No dollar amount
 *  - Negative numbers
 *
 * Test 2: convertNum method
 *  -
 *
 */
public class TestConverter {
    private Converter setUp(String number) {
        Converter converter = new Converter(number);
        converter.sanitiseNum();
        return converter;
    }

    @Test
    public void testSanitise() {
        // Number with no decimal
        Converter converter = new Converter("5");
        converter.sanitiseNum();
        assertEquals("5.00", converter.getProtoNumber());

        // Number with 1 decimal place
        converter = new Converter("5.0");
        converter.sanitiseNum();
        assertEquals("5.00", converter.getProtoNumber());

        // Number with 2 decimal places
        converter = new Converter("5.00");
        converter.sanitiseNum();
        assertEquals("5.00", converter.getProtoNumber());

        // Number with 3 decimal places
        converter = new Converter("5.001");
        converter.sanitiseNum();
        assertEquals("5.00", converter.getProtoNumber());

        // Number with no dollars
        converter = new Converter(".5");
        converter.sanitiseNum();
        assertEquals("0.50", converter.getProtoNumber());

        // Negative number
        converter = new Converter("-5.0");
        converter.sanitiseNum();
        assertEquals("5.00", converter.getProtoNumber());
        assertTrue(converter.getNegative());
    }

    @Test
    public void testConvertNum() {
        // Number == 0
        Converter converter = setUp("0");
        assertEquals(converter.convertNum(), "ZERO DOLLARS AND ZERO CENTS");

        // 0 < Number < 10
        converter = setUp("4.78");
        assertEquals(converter.convertNum(), "FOUR DOLLARS AND SEVENTY-EIGHT CENTS");

        // 9 < Number < 20
        converter = setUp("15.09");
        assertEquals(converter.convertNum(), "FIFTEEN DOLLARS AND NINE CENTS");

        // 19 < Number < 100
        converter = setUp("85.60");
        assertEquals(converter.convertNum(), "EIGHTY-FIVE DOLLARS AND SIXTY CENTS");

        // 99 < Number < 1000
        converter = setUp("234.45");
        assertEquals(converter.convertNum(), "TWO HUNDRED AND THIRTY-FOUR DOLLARS AND FORTY-FIVE CENTS");

        // 999 < Number < 10000
        converter = setUp("4567.89");
        assertEquals(converter.convertNum(), "FOUR THOUSAND FIVE HUNDRED AND SIXTY-SEVEN DOLLARS AND EIGHTY-NINE CENTS");

        // 9999 < Number < 100000
        converter = setUp("78901.34");
        assertEquals(converter.convertNum(), "SEVENTY-EIGHT THOUSAND NINE HUNDRED AND ONE DOLLARS AND THIRTY-FOUR CENTS");

        // 99999 < Number < 1000000
        converter = setUp("345678.90");
        assertEquals(converter.convertNum(), "THREE HUNDRED AND FORTY-FIVE THOUSAND SIX HUNDRED AND SEVENTY-EIGHT DOLLARS AND NINETY CENTS");

        // 999999 < Number < 10000000
        converter = setUp("1234567.12");
        assertEquals(converter.convertNum(), "ONE MILLION TWO HUNDRED AND THIRTY-FOUR THOUSAND FIVE HUNDRED AND SIXTY-SEVEN DOLLARS AND TWELVE CENTS");

        // 9999999 < Number < 100000000
        converter = setUp("98765432.10");
        assertEquals(converter.convertNum(), "NINETY-EIGHT MILLION SEVEN HUNDRED AND SIXTY-FIVE THOUSAND FOUR HUNDRED AND THIRTY-TWO DOLLARS AND TEN CENTS");

        // 99999999 < Number < 1000000000
        converter = setUp("123456789.01");
        assertEquals(converter.convertNum(), "ONE HUNDRED AND TWENTY-THREE MILLION FOUR HUNDRED AND FIFTY-SIX THOUSAND SEVEN HUNDRED AND EIGHTY-NINE DOLLARS AND ONE CENTS");

        // 999999999 < Number < 1000000000
        converter = setUp("987654321.99");
        assertEquals(converter.convertNum(), "NINE HUNDRED AND EIGHTY-SEVEN MILLION SIX HUNDRED AND FIFTY-FOUR THOUSAND THREE HUNDRED AND TWENTY-ONE DOLLARS AND NINETY-NINE CENTS");

        // Number < 0
        converter = setUp("-45.67");
        assertEquals(converter.convertNum(), "MINUS FORTY-FIVE DOLLARS AND SIXTY-SEVEN CENTS");
    }
}
