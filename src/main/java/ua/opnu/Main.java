package ua.opnu;

import java.util.*;
import java.util.function.*;

public class Main {

    // =========================
    // Завдання 1 — предикат простого числа
    // =========================
    public static Predicate<Integer> isPrime = n -> {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    };

    // ========================
    // Клас Student для завдань 2–4
    // ========================
    static class Student {
        private String name;
        private String group;
        private int[] marks;

        public Student(String name, String group, int[] marks) {
            this.name = name;
            this.group = group;
            this.marks = marks;
        }

        public String getName() { return name; }
        public String getGroup() { return group; }
        public int[] getMarks() { return marks; }
    }

    // =========================
    // Завдання 2 — фільтрація студентів
    // =========================
    public static List<Student> filter(Student[] students, Predicate<Student> condition) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (condition.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    // =========================
    // Завдання 3 — фільтрація за двома умовами
    // =========================
    public static List<Student> filterTwo(Student[] students,
                                          Predicate<Student> p1,
                                          Predicate<Student> p2) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (p1.test(s) && p2.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    // =========================
    // Завдання 4 — свій Consumer для Student
    // =========================
    @FunctionalInterface
    interface StudentConsumer {
        void accept(Student s);
    }

    // =========================
    // Завдання 5 — метод з Predicate + Consumer
    // =========================
    public static <T> void applyIf(T[] arr, Predicate<T> pred, Consumer<T> cons) {
        for (T el : arr) {
            if (pred.test(el)) {
                cons.accept(el);
            }
        }
    }

    // =========================
    // Завдання 6 — Function, що повертає 2^n
    // =========================
    public static Function<Integer, Integer> pow2 = n -> (int) Math.pow(2, n);

    // =========================
    // Завдання 7 — stringify()
    // =========================
    public static String[] stringify(int[] arr, Function<Integer, String> func) {
        String[] result = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = func.apply(arr[i]);
        }
        return result;
    }

    // Функція перетворення числа в слово
    public static Function<Integer, String> numberToWord = n -> {
        String[] words = {
                "нуль", "один", "два", "три", "чотири",
                "п'ять", "шість", "сім", "вісім", "дев'ять"
        };
        return words[n];
    };

    // =========================
    // MAIN — виконання всіх завдань
    // =========================
    public static void main(String[] args) {

        // --- Завдання 1 ---
        System.out.println("7 просте? " + isPrime.test(7));
        System.out.println("21 просте? " + isPrime.test(21));

        // --- Дані для студентів (2–4 завдання) ---
        Student[] students = {
                new Student("Іван Іванов", "ІП-21", new int[]{90, 80, 75}),
                new Student("Петро Петренко", "ІП-21", new int[]{40, 70, 55}),
                new Student("Оля Коваль", "ІП-22", new int[]{100, 95, 90})
        };

        // --- Завдання 2 ---
        Predicate<Student> hasDebts = s -> {
            for (int m : s.getMarks()) {
                if (m < 60) return true;
            }
            return false;
        };

        System.out.println("\nСтуденти із заборгованостями:");
        filter(students, hasDebts).forEach(s -> System.out.println(s.getName()));

        // --- Завдання 3 ---
        Predicate<Student> goodAvg = s -> {
            int sum = 0;
            for (int m : s.getMarks()) sum += m;
            return sum / s.getMarks().length > 80;
        };

        Predicate<Student> noDebts = s -> {
            for (int m : s.getMarks()) if (m < 60) return false;
            return true;
        };

        System.out.println("\nСтуденти без заборгованостей і з середнім > 80:");
        filterTwo(students, goodAvg, noDebts)
                .forEach(s -> System.out.println(s.getName()));

        // --- Завдання 4 ---
        StudentConsumer printName = s -> {
            String[] parts = s.getName().split(" ");
            System.out.println(parts[0] + " " + parts[1]);
        };

        System.out.println("\nforEach(StudentConsumer):");
        for (Student s : students) printName.accept(s);

        // --- Завдання 5 ---
        Integer[] nums = {1, 2, 3, 4, 5, 6};
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Consumer<Integer> printer = n -> System.out.println("num = " + n);

        System.out.println("\napplyIf (парні числа):");
        applyIf(nums, isEven, printer);

        // --- Завдання 6 ---
        System.out.println("\n2^n:");
        for (int i = 0; i < 10; i++) {
            System.out.println("2^" + i + " = " + pow2.apply(i));
        }

        // --- Завдання 7 ---
        int[] nums10 = {0,1,2,3,4,5,6,7,8,9};
        String[] words = stringify(nums10, numberToWord);

        System.out.println("\nНазви чисел:");
        for (String w : words) System.out.println(w);
    }
}