package com.mycompany.proyecto_recursividas;

import java.util.Scanner;

public class recursividad {

    public static int resolver(String expr) {
        if (expr == null) {
            throw new IllegalArgumentException("No puede ser null");
        }
        expr = expr.replace(" ", "");

        if (expr.isEmpty()) {
            throw new IllegalArgumentException("escribe algo");
        }
        if (expr.startsWith("(") && expr.endsWith(")") && parParentesisValido(expr)) {
            return resolver(expr.substring(1, expr.length() - 1));
        }

        int nivel = 0;
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == ')') nivel++;
            else if (c == '(') nivel--;
            else if (nivel == 0 && (c == '+' || c == '-')) {
                String izq = expr.substring(0, i);
                String der = expr.substring(i + 1);

                if (izq.isEmpty() || der.isEmpty()) {
                    throw new IllegalArgumentException("error cerca de '" + c + "'");
                }

                if (c == '+') {
                    return resolver(izq) + resolver(der);
                } else {
                    return resolver(izq) - resolver(der);
                }
            }
        }

        nivel = 0;
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == ')') nivel++;
            else if (c == '(') nivel--;
            else if (nivel == 0 && c == '*') {
                String izq = expr.substring(0, i);
                String der = expr.substring(i + 1);

                if (izq.isEmpty() || der.isEmpty()) {
                    throw new IllegalArgumentException("error cerca de '*'");
                }

                return resolver(izq) * resolver(der);
            }
        }

        try {
            return Integer.parseInt(expr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("algo esta mal en: '" + expr + "'");
        }
    }

    private static boolean parParentesisValido(String expr) {
        int nivel = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') nivel++;
            else if (c == ')') nivel--;

            if (nivel < 0) {
                throw new IllegalArgumentException("Paréntesis mal puestos.");
            }
            if (nivel == 0 && i < expr.length() - 1) {
                return false;
            }
        }
        if (nivel != 0) {
            throw new IllegalArgumentException("Te faltan o sobran parentesis.");
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Pon tu ecuacion: ");
        String texto = sc.nextLine();

        int res = resolver(texto);
        System.out.println("Resultado: " + res);

        sc.close();
    }
}