package com.lojadejogos.util;

import com.lojadejogos.util.interfaces.ValidCPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                d1 += digito * (10 - i);
                d2 += digito * (11 - i);
            }

            int resto1 = (d1 * 10) % 11;
            if (resto1 == 10) resto1 = 0;

            d2 += resto1 * 2;
            int resto2 = (d2 * 10) % 11;
            if (resto2 == 10) resto2 = 0;

            return resto1 == Character.getNumericValue(cpf.charAt(9)) &&
                   resto2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}
