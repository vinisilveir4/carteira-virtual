package com.example.carteiraVirtual.util;

import java.util.regex.Pattern;

public class CPFUtil {
    // Método para formatar CPF
    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remover caracteres não numéricos
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // Método para validar CPF
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remover caracteres não numéricos

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // CPF deve ter 11 dígitos e não pode ser formado por dígitos repetidos
        }

        // Cálculo do dígito verificador
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = cpf.charAt(i) - '0';
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int remainder = sum % 11;
        int digit1 = (remainder < 2) ? 0 : (11 - remainder);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        remainder = sum % 11;
        int digit2 = (remainder < 2) ? 0 : (11 - remainder);

        return (digit1 == digits[9] && digit2 == digits[10]);
    }
}

